package collectors;

import jakarta.annotation.Nonnull;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.*;

public class EntryMap<K, V> implements UnmodifiableMap<K, V> {
    private Collection<Entry<K, V>> entries;

    public EntryMap(Collection<Entry<K, V>> entries) {
        this.entries = requireNonNull(entries);
    }

    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public boolean isEmpty() {
        return entries.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return entries.stream().anyMatch(entry -> Objects.equals(entry.getKey(), key));
    }

    @Override
    public boolean containsValue(Object value) {
        return entries.stream().anyMatch(entry -> Objects.equals(entry.getValue(), value));
    }

    @Override
    public V get(Object key) {
        return entries.stream()
                .filter(entry -> Objects.equals(entry.getKey(), key))
                .findFirst()
                .map(Entry::getValue)
                .orElse(null);
    }

    @Override
    public @Nonnull Set<K> keySet() {
        return entries.stream().map(Entry::getKey).collect(toUnmodifiableSet());
    }

    @Override
    public @Nonnull Collection<V> values() {
        return entries.stream().map(Entry::getValue).toList();
    }

    @Override
    public @Nonnull Set<Entry<K, V>> entrySet() {
        return Set.copyOf(entries);
    }
}
