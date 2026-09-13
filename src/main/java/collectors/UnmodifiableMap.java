package collectors;

import jakarta.annotation.Nonnull;

import java.util.Map;

public interface UnmodifiableMap<K, V> extends Map<K, V> {
    @Override
    default V put(K key, V value) {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + " is unmodifiable: put is not allowed.");
    }

    @Override
    default V remove(Object key) {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + " is unmodifiable: remove is not allowed.");
    }

    @Override
    default void putAll(@Nonnull Map m) {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + " is unmodifiable: putAll is not allowed.");
    }

    @Override
    default void clear() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + " is unmodifiable: clear is not allowed.");
    }
}
