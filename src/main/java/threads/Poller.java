package threads;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.*;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class Poller {
    private final ScheduledExecutorService ses;
    private final int initialDelay;
    private final int interval;
    private final int timeout;
    private final TimeUnit timeUnit;

    public <T> CompletableFuture<T> poll(Callable<T> job, Predicate<T> isComplete) {
        return poll(job, isComplete, this::defaultTimeoutHandler);
    }

    public <T> CompletableFuture<T> poll(Callable<T> job, Predicate<T> isComplete, Callable<T> timeoutHandler) {
        CompletableFuture<T> future = new CompletableFuture<>();
        Runnable runnableJob = runFomCallable(job, isComplete, future);
        Runnable runnableTimeout = () -> timeout(future, timeoutHandler);
        ScheduledFuture<?> scheduledPolling = ses.scheduleWithFixedDelay(runnableJob, initialDelay, interval, timeUnit);
        ScheduledFuture<?> scheduledTimeout = ses.schedule(runnableTimeout, timeout + initialDelay, timeUnit);
        return future.whenComplete((result, e) -> {
            scheduledPolling.cancel(true);
            scheduledTimeout.cancel(true);
        });
    }

    private <T> Runnable runFomCallable(Callable<T> job, Predicate<T> isComplete, CompletableFuture<T> future) {
        return () -> {
            try {
                T result = job.call();
                if (isComplete.test(result)) {
                    future.complete(result);
                }
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        };
    }

    private <T> void timeout(CompletableFuture<T> future, Callable<T> timeoutHandler) {
        try {
            future.complete(timeoutHandler.call());
        } catch (Exception e) {
            future.completeExceptionally(e);
        }
    }

    private <T> T defaultTimeoutHandler() throws TimeoutException {
        throw new TimeoutException("Polling timed out after " + timeout + " " + timeUnit);
    }
}
