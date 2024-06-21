package generics;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.*;

public class SubCompare {

    @SafeVarargs
    public static <T> boolean equals(Collection<T> x, Collection<T> y, Function<T, ?>... comparables) {
        if (x.size() != y.size()) {
            return false;
        }
        Set<Integer> setX = x.stream().map(it -> hash(it, comparables)).collect(toSet());
        Set<Integer> setY = y.stream().map(it -> hash(it, comparables)).collect(toSet());
        return setX.equals(setY);
    }

    @SafeVarargs
    private static <T> int hash(T element, Function<T, ?>... comparables) {
        return Objects.hash(stream(comparables).map(f -> f.apply(element)).toArray());
    }

}
