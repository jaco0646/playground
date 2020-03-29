package reflection;

import java.util.stream.Stream;

import static java.util.Comparator.comparing;

public class ReflectUtil {
    private ReflectUtil() {
        throw new UnsupportedOperationException();
    }

    public static Stream<Class<?>> getParents(Class<?> c) {
        return Stream.concat(
                    streamOfParents(c),
                    streamOfParents(c).flatMap(ReflectUtil::getParents))
                .sorted(comparing(Class::getSimpleName))
                .distinct();
    }

    private static Stream<Class<?>> streamOfParents(Class<?> c) {
        return Stream.concat( Stream.ofNullable(c.getSuperclass()), Stream.of(c.getInterfaces()) );
    }
}
