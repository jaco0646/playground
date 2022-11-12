package streams;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Utils {

    static <T> Consumer<T> ifElse(Predicate<T> criteria, Consumer<T> onMatch, Consumer<T> onMismatch) {
        return it -> (criteria.test(it) ? onMatch : onMismatch).accept(it);
    }

    static <T, R> Function<T, R> ifElse(Predicate<T> criteria, Function<T, R> onMatch, Function<T, R> onMismatch) {
        return it -> (criteria.test(it) ? onMatch : onMismatch).apply(it);
    }

    /**
     * Thread-safe implementation of an enter-only-once gate.
     * @see <a href="https://stackoverflow.com/questions/74404697/">What to call an object that acts like an enter-only-once gate?</a>
     */
    static class Once<T> implements Supplier<T> {
        private final T second;
        private final AtomicReference<T> gate;

        Once(T first, T second) {
            this.second = second;
            this.gate = new AtomicReference<>(first);
        }

        @Override
        public T get() {
            return gate.getAndSet(second);
        }
    }
}
