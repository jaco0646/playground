package spring.cache;

import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MyCache implements Cache {
    private final Map<Object, Object> cache = new ConcurrentHashMap<>();

    public MyCache() {
        System.out.println("instantiating MyCache");
    }

    @Override
    public String getName() {
        System.out.println("Get Cache Name");
        return "myCacheName";
    }

    @Override
    public Object getNativeCache() {
        System.out.println("Get Native Cache");
        return cache;
    }

    @Override
    public ValueWrapper get(Object key) {
        System.out.println("Get from Cache: " + key);
        throw new RuntimeException("Foo Runtime Exception");
//        return cache.containsKey(key) ? () -> cache.get(key) : null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key, Class<T> type) {
        System.out.println("Get Type from Cache: " + key);
        return (T) cache.get(key);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        System.out.println("Get Callable from Cache: " + key);
        try {
            return cache.containsKey(key) ? (T) cache.get(key) : valueLoader.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void put(Object key, Object value) {
        System.out.println("Put in Cache: " + key);
        cache.put(key, value);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        System.out.println("Put if absent in Cache: " + key);
        Object oldValue = cache.putIfAbsent(key, value);
        return () -> oldValue;
    }

    @Override
    public void evict(Object key) {
        System.out.println("Evict from Cache: " + key);
        cache.remove(key);
    }

    @Override
    public void clear() {
        System.out.println("Clear Cache");
        cache.clear();
    }
}
