package threads;

import java.util.concurrent.Callable;

    @FunctionalInterface
    public interface CallableRunnable<R> extends Callable<R>, Runnable {
        static <R> CallableRunnable<R> fromCallable(Callable<R> callable) {
            return callable::call;
        }

        static CallableRunnable<Void> fromRunnable(Runnable runnable) {
            return () -> { runnable.run(); return null; };
        }

        @Override
        default void run() {
            try {
                call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        // Tests ----------------------------------------------------------------------------------
        static void main(String... args) throws Exception {
            Callable<String> callable = () -> "Hello Callable!";
            Runnable runnable = () -> System.out.println("Hello Runnable!");

            Callable<Void> adaptedRunnable = CallableRunnable.fromRunnable(runnable);
            Runnable adaptedCallable = CallableRunnable.fromCallable(callable);

            System.out.println(adaptedRunnable.call());  // null
            adaptedCallable.run();

            Callable<String> thrower = () -> { throw new Exception("Goodbye Callable."); };
            Runnable adaptedThrower = CallableRunnable.fromCallable(thrower);
            try {
                adaptedThrower.run();
            } catch (RuntimeException e) {
                System.out.println(e.getCause().getMessage());
            }

            String msg = "Hello CallableRunnable!";
            CallableRunnable<String> callableRunnable = () -> { System.out.println(msg); return msg; };
            System.out.println(testMethodRequiresCallable(callableRunnable));
            testMethodRequiresRunnable(callableRunnable);
        }

        static <R> R testMethodRequiresCallable(Callable<R> callable) throws Exception {
            return callable.call();
        }

        static void testMethodRequiresRunnable(Runnable runnable) {
            runnable.run();
        }
    }
