package spring.cache;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
@ConditionalOnProperty("foo")
public class CacheConfig extends CachingConfigurerSupport {

    public CacheConfig() {
        System.out.println("instantiating CacheConfig");
    }

    @Override
    public CacheErrorHandler errorHandler() {
        System.out.println("instantiating CacheLogger");
        return new CacheLogger();
    }
}
