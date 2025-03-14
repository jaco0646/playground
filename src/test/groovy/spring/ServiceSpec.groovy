package spring

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.cache.interceptor.SimpleKey
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@SpringBootTest(webEnvironment = NONE)
class ServiceSpec extends Specification {

    @Autowired
    CacheManager cacheManager

    @Autowired
    Service service

    def cleanup() {
        cacheManager.cacheNames
                .collect(cacheManager::getCache)
                .each(Cache::invalidate)
    }

    def cachingTest() {
        given:
            def cache = cacheManager.getCache("myCacheName")
        expect:
            cache.get(SimpleKey.EMPTY, String) == null
            service.foo() == 'foo'
            cache.get(SimpleKey.EMPTY, String) == 'foo'
    }

    def 'Test default method caching'() {
        given:
            def cache = cacheManager.getCache("myCacheName")
        expect:
            cache.get(SimpleKey.EMPTY, String) == null
        when:
            service.repopulateCache(service)
        then:
            cache.get(SimpleKey.EMPTY, String) == '"Hello from application properties!"'
    }
}
