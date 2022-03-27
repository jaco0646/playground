package playground.threads

import spock.lang.Specification

import java.lang.reflect.UndeclaredThrowableException
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import java.util.concurrent.TimeoutException

import static java.util.concurrent.TimeUnit.MILLISECONDS

class PollerSpec extends Specification {
    static final Poller poller = new Poller(Executors.newSingleThreadScheduledExecutor(), 500, 100, 500, MILLISECONDS)
    def results = (1..30).toList()

    def 'test happy path'() {
        given:
            def future = poller.poll(() -> countUp(), result1 -> result1 == 3)
        when:
            def result = future.get()
        then:
            result == 3
            results.size() == 27
    }

    def 'test timeout'() {
        given:
            def future = poller.poll(() -> countDown(), result2 -> result2 == 5)
        when:
            future.get()
        then:
            def e = thrown(ExecutionException)
            e.cause instanceof TimeoutException
            results.size() == 25
    }

    def 'test Exception'() {
        given:
            def future = poller.poll(() -> throwException(), result2 -> result2 == 1)
        when:
            future.get()
        then:
            def e = thrown(ExecutionException)
            e.cause instanceof Exception
            e.cause.message == 'foo'
            sleep(1000)
            results.size() == 29
    }

    def 'test Timeout default'() {
        given:
            def future = poller.poll(() -> countDown(), result2 -> result2 == 5, () -> 42)
        when:
            def result = future.get()
        then:
            result == 42
            results.size() == 25
    }

    def 'test Timeout handler Exception'() {
        given:
            def future = poller.poll(() -> countDown(), result2 -> result2 == 5, () -> {throw new Exception('foo')})
        when:
            future.get()
        then:
            def e = thrown(ExecutionException)
            e.cause instanceof UndeclaredThrowableException
            e.cause.cause instanceof Exception
            e.cause.cause.message == 'foo'
            sleep(1000)
            results.size() == 25
    }

    int countUp() {
        int result = results.removeAt(0)
        println "working... $result"
        return result
    }

    int countDown() {
        int result = results.removeLast()
        println "working... $result"
        return result
    }

    String throwException() {
        int result = results.removeAt(0)
        println "working... $result"
        throw new Exception('foo')
    }

}
