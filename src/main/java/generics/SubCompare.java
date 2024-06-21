package generics;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static java.util.Arrays.stream;

public class SubCompare {

    @SafeVarargs
    public static <T> boolean equals(Collection<T> x, Collection<T> y, Function<T, ?>... comparables) {
        if (x.size() != y.size()) {
            return false;
        }
        List<Integer> sortedX = x.stream().map(it -> hash(it, comparables)).sorted().toList();
        List<Integer> sortedY = y.stream().map(it -> hash(it, comparables)).sorted().toList();
        return sortedX.equals(sortedY);
    }

    @SafeVarargs
    private static <T> int hash(T element, Function<T, ?>... comparables) {
        return Objects.hash(stream(comparables).map(f -> f.apply(element)).toArray());
    }

}
