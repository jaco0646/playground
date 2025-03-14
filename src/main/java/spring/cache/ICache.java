package spring.cache;

/** Calling the abstract methods within a `default` method will _not_ trigger the underlying (proxied) cache mechanism.
 * However, we can pass the cache into itself, in order to trigger caching.
 */
public interface ICache {
    default void repopulateCache(ICache iCache) {
        iCache.foo();
    }

    default String foo() {
        throw new UnsupportedOperationException(this.getClass().getSimpleName()
            + " does not support foo(). It should override repopulateCache() to call something else.");
    }
}
