package streams;

import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/** A DSL for arbitrary branching logic within the {@link java.util.stream.Stream#map(Function)} method
 * and the {@link java.util.stream.Stream#forEach(Consumer)} method. */
@RequiredArgsConstructor(staticName = "if_")
public class Selector<T> {
    private final Predicate<T> predicate;

    public ElseConsumer<T> thenDo(Consumer<T> onMatch) {
        return (Consumer<T> onMismatch) ->
                (T arg) -> (predicate.test(arg) ? onMatch : onMismatch).accept(arg);
    }

    public <R> ElseMapper<T, R> thenMap(Function<T, R> onMatch) {
        return (Function<T, R> onMismatch) ->
                (T arg) -> (predicate.test(arg) ? onMatch : onMismatch).apply(arg);
    }

    @FunctionalInterface
    public interface ElseConsumer<T> {
        Consumer<T> elseDo(Consumer<T> onMismatch);
    }

    @FunctionalInterface
    public interface ElseMapper<T, R> {
        Function<T, R> elseMap(Function<T, R> onMismatch);
    }
}
