package streams;

import org.springframework.dao.DuplicateKeyException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;

/**
 * related: <a href="https://stackoverflow.com/questions/43864005">Is it advisable to reuse a Collector?</a>
 */
public class Utils {

    static <K, K2, V> Function<Map.Entry<K, V>, Map.Entry<K2, V>> newKey(Function<K, K2> keyMapper) {
        return entry -> Map.entry(keyMapper.apply(entry.getKey()), entry.getValue());
    }

    static <K, V, V2> Function<Map.Entry<K, V>, Map.Entry<K, V2>> newValue(Function<V, V2> valueMapper) {
        return entry -> Map.entry(entry.getKey(), valueMapper.apply(entry.getValue()));
    }

    static <K, V> Collector<Map.Entry<K, V>, ?, Map<K, V>> entriesToMap() {
        return entriesToMap(HashMap::new, Characteristics.UNORDERED);
    }

    static <K, V> Collector<Map.Entry<K, V>, ?, Map<K, V>> entriesToMap(Supplier<Map<K, V>> mapConstructor,
                                                                        Characteristics... c) {
        return Collector.of(
                mapConstructor,
                (map, entry) -> map.merge(entry.getKey(), entry.getValue(), (_,_) -> dke(entry)),
                (map1, map2) -> { map1.putAll(map2); return map1; },
                c
        );
    }

    private static <V> V dke(Map.Entry<?,?> entry) {
        throw new DuplicateKeyException(String.valueOf(entry.getKey()));
    }

    static <K, V> Collector<Map.Entry<K, V>, ?, Map<K, V>> entriesToMap(Supplier<Map<K, V>> mapConstructor,
                                                                        BinaryOperator<V> mergeFunction,
                                                                        Characteristics... c) {
        return Collector.of(
                mapConstructor,
                (map, entry) -> map.merge(entry.getKey(), entry.getValue(), mergeFunction),
                (map1, map2) -> { map1.putAll(map2); return map1; },
                c
        );
    }

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
