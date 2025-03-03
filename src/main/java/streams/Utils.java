package streams;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
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

    static <K, V> Collector<Map.Entry<K, V>, ?, Map<K, V>> toMap() {
        return toMap(HashMap::new, Characteristics.UNORDERED);
    }

    static <K, V> Collector<Map.Entry<K, V>, ?, Map<K, V>> toMap(Supplier<Map<K, V>> mapConstructor, Characteristics... c) {
        return Collector.of(
                mapConstructor,
                (map, entry) -> map.put(entry.getKey(), entry.getValue()),
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
