package reflection

import spock.lang.Specification

class ProxyBuilderSpec extends Specification {

    def "testMapInterface"() {
        given:
            HashMap<String, String> delegate = new HashMap<>()
            Map<String, String> proxyMap = new ProxyBuilder<>(delegate)
                    .withOverride("clear", it -> System.out.println("Hello World"))
                    .build()
        expect:
            proxyMap.put("foo", "bar") == null
        when:
            proxyMap.clear()
        then:
            proxyMap.size() == 1
    }

    def "testMapAbstractClass"() {
        given:
            HashMap<String, String> delegate = new HashMap<>()
        when:
            AbstractMap<String, String> proxyMap = new ProxyBuilder<>(delegate).build()
        then:
            ClassCastException cce = thrown()
            cce.message.contains('com.sun.proxy.$Proxy')
            cce.message.contains('java.util.AbstractMap')
    }
}
