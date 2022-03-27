package playground.threads;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class Poller {
    private final ScheduledExecutorService ses;
    private final int initialDelay;
    private final int interval;
    private final int timeout;
    private final TimeUnit timeUnit;

    public <T> CompletableFuture<T> poll(Callable<T> job, Predicate<T> isComplete) {
        return poll(job, isComplete, null);
    }

    public <T> CompletableFuture<T> poll(Callable<T> job, Predicate<T> isComplete, Supplier<T> timeoutHandler) {
        CompletableFuture<T> future = new CompletableFuture<>();
        Runnable runnable = runFomCallable(job, isComplete, future);
        ScheduledFuture<?> polling = ses.scheduleWithFixedDelay(runnable, initialDelay, interval, timeUnit);
        ses.schedule(() -> timeout(future, timeoutHandler), timeout + initialDelay, timeUnit);
        return future.whenComplete((result, e) -> polling.cancel(true));
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

    private <T> void timeout(CompletableFuture<T> future, Supplier<T> timeoutHandler) {
        if (timeoutHandler == null) {
            future.completeExceptionally(new TimeoutException("Polling timed out after " + timeout + " " + timeUnit));
        } else {
            try {
                future.complete(timeoutHandler.get());
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        }
    }
}
