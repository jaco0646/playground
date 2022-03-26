package playground.threads;

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
        CompletableFuture<T> future = new CompletableFuture<>();
        Runnable runnable = runFomCallable(job, isComplete, future);
        ScheduledFuture<?> polling = ses.scheduleWithFixedDelay(runnable, initialDelay, interval, timeUnit);
        ses.schedule(() -> timeout(polling, future), timeout + initialDelay, timeUnit);
        future.exceptionally(e -> exception(polling));
        return future.thenApply(result -> success(polling, result));
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

    private <T> T success(ScheduledFuture<?> polling, T result) {
        polling.cancel(true);
        return result;
    }

    private <T> T exception(ScheduledFuture<?> polling) {
        polling.cancel(true);
        return null;
    }

    private void timeout(ScheduledFuture<?> polling, CompletableFuture<?> future) {
        polling.cancel(true);
        future.completeExceptionally(new TimeoutException("Polling timed out after " + timeout + " " + timeUnit));
    }
}
