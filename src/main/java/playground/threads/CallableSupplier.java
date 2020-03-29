package playground.threads;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

@FunctionalInterface
public interface CallableSupplier<T> extends Callable<T>, Supplier<T> {
    @Override
    default T get() {
        try {
            return call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
