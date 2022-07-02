package streams;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.*;

public class Utils {

    static <T> void splitForEach(Collection<T> elements, Predicate<T> criteria, Consumer<T> onMatch, Consumer<T> onMismatch) {
        elements.forEach(it -> {
            if (criteria.test(it)) {
                onMatch.accept(it);
            } else {
                onMismatch.accept(it);
            }
        });
    }

    static <T, R> List<R> mapForEach(Collection<T> elements, Predicate<T> criteria, Function<T, R> onMatch, Function<T, R> onMismatch) {
        return elements.stream()
                .map(it -> criteria.test(it) ? onMatch.apply(it) : onMismatch.apply(it))
                .collect(toList());
    }
}
