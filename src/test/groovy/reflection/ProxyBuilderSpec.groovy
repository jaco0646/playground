package reflection

import spock.lang.Specification

import java.util.function.Function

class ProxyBuilderSpec extends Specification {

    def "testMapInterface"() {
        given:
            def overrides = [ (Map.class.getMethod("clear")) : { System.out.println("Hello World") } as Function ]
            ProxyBuilder<Map<String, String>> builder = new ProxyBuilder<>(new HashMap<>())
            Map<String, String> proxy = builder.newProxy(builder.newHandler(overrides))
        expect:
            proxy.put("foo", "bar") == null
        when:
            proxy.clear()
        then:
            proxy.size() == 1
    }

    def "testMapAbstractClass"() {
        given:
            ProxyBuilder<Map<String, String>> builder = new ProxyBuilder<>(new HashMap<>())
        when:
            AbstractMap<String, String> proxy = builder.newProxy(builder.newHandler([:]))
        then:
            ClassCastException cce = thrown()
            cce.message.contains('com.sun.proxy.$Proxy')
            cce.message.contains('java.util.AbstractMap')
    }
}
