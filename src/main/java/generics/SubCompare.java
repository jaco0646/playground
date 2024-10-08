package generics;

import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static java.util.Arrays.stream;

public class SubCompare {

    /** Compare collections of related objects using the hash codes from any subset of the objects' method return values.
     * The comparison requires values whose hash codes indicate both equality and inequality!
     * <p>
     * Note the contract of {@link Object#hashCode()} does <i>not</i> require an indication of inequality,
     * so not all values are suitable for comparison using this method. */
    @SafeVarargs
    public static <T> boolean equals(@NonNull Collection<? extends T> x,
                                     @NonNull Collection<? extends T> y, Function<T, ?>... comparables) {
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
