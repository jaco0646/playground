package streams;

import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/** A DSL for arbitrary branching logic within the {@link java.util.stream.Stream#map(Function)} method
 * and the {@link java.util.stream.Stream#forEach(Consumer)} method. */
public class Selector {
    private Selector() { }

    public static <T> Then<T> branchingIf(Predicate<T> test) {
        return new Then<>(test);
    }

    @RequiredArgsConstructor
    public static class Then<T> {
        private final Predicate<T> test;

        public <R> ElseMapper<T, R> thenMap(Function<T, R> onMatch) {
            return new ElseMapper<>(test, onMatch);
        }

        public ElseConsumer<T> thenDo(Consumer<T> onMatch) {
            return new ElseConsumer<>(test, onMatch);
        }
    }

    @RequiredArgsConstructor
    public static class ElseMapper<T, R> {
        private final Predicate<T> test;
        private final Function<T, R> onMatch;

        public Function<T, R> elseMap(Function<T, R> onMismatch) {
            return arg -> test.test(arg) ? onMatch.apply(arg) : onMismatch.apply(arg);
        }
    }

    @RequiredArgsConstructor
    public static class ElseConsumer<T> {
        private final Predicate<T> test;
        private final Consumer<T> onMatch;

        public Consumer<T> elseDo(Consumer<T> onMismatch) {
            return arg -> {
                if (test.test(arg)) {
                    onMatch.accept(arg);
                } else {
                    onMismatch.accept(arg);
                }
            };
        }
    }
}
