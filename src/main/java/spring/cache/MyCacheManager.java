package spring.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/** This class is unnecessary as Spring provides a default cache manager.
 * It just proves that an override will get automatically wired up. */
@Component
public class MyCacheManager implements CacheManager {

    @Autowired
    Cache myCache;

    public MyCacheManager() {
        System.out.println("instantiating MyCacheManager");
    }

    @Override
    public Cache getCache(String name) {
        System.out.println("Get Cache: " + name);
        return myCache;
    }

    @Override
    public Collection<String> getCacheNames() {
        System.out.println("Get All Cache Names");
        return List.of(myCache.getName());
    }
}
