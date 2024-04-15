package spring.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.mrbean.MrBeanModule
import org.spockframework.spring.EnableSharedInjection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Shared
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@SpringBootTest(webEnvironment = NONE)
@EnableSharedInjection
class MrBeanSpec extends Specification {
    @Shared
    @Autowired
    ObjectMapper jsonMapper

    void setupSpec() {
        jsonMapper.registerModule(new MrBeanModule())
    }

    def 'Can Mr Bean deserialize an interface'() {
        given:
            String json = '''
                {
                    "foo": "one",
                    "bar": "two",
                    "baz": 3
                }
            '''
        when:
            def object = jsonMapper.readValue(json, MrBeanProjection)
        then:
            object instanceof MrBeanProjection
            object.foo == 'one'
            object.bar == 'two'
            object.baz == 3
    }
}