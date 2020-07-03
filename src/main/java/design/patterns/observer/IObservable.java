package design.patterns.observer;

import org.apache.commons.collections4.map.ReferenceIdentityMap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.synchronizedMap;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.map.AbstractReferenceMap.ReferenceStrength.HARD;
import static org.apache.commons.collections4.map.AbstractReferenceMap.ReferenceStrength.WEAK;

public interface IObservable {
    class Cache {
        private static final Map<IObservable, Set<Observer>> CACHE =
                synchronizedMap(new ReferenceIdentityMap<>(WEAK, HARD, true));  //Guava has a thread-safe version.
    }

    default boolean register(Observer... observers) {
        return getObservers().addAll(Arrays.asList(observers));
    }

    default boolean unregister(Observer observer) {
        return getObservers().remove(observer);
    }

    default boolean unregisterAll() {
        return Cache.CACHE.remove(this) != null;
    }

    /** Notify all Observers in one (asynchronous) thread.
     * A notification failure will short-circuit this operation and subsequent Observers will not be notified. */
    default CompletableFuture<Void> notifyAll(ChangeEvent event) {
        return CompletableFuture.runAsync(() -> getObservers().forEach(it -> it.notify(event)));
    }

    /** Notify each Observer in its own thread.
     * A notification failure will affect only one Observer. */
    default CompletableFuture<Void> notifyEach(ChangeEvent event) {
        return getObservers().stream()
                .map(observer -> CompletableFuture.runAsync(() -> observer.notify(event)))
                .collect(collectingAndThen(toList(), this::awaitAll));
    }

    private CompletableFuture<Void> awaitAll(Collection<CompletableFuture<?>> futures) {
        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }

    private Set<Observer> getObservers() {
        return Cache.CACHE.computeIfAbsent(this, k -> ConcurrentHashMap.newKeySet());
    }
}
