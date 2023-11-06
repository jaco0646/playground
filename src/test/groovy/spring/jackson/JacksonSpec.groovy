package spring.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@SpringBootTest(webEnvironment = NONE)
class JacksonSpec extends Specification {
    @Autowired
    ObjectMapper jsonMapper

    def 'Test Jackson Serialization of Booleans'() {
        given:
            def booleans = new Booleans(
                    true,
                    true,
                    true,
                    true,
                    true
            )
            def json = jsonMapper.writeValueAsString(booleans)
        expect:
            booleans.isPrimitive()
            json.contains(/"primitive":true/)

            booleans.getIsWrapper()
            json.contains(/"isWrapper":true/)

            booleans.isFluent()
            json.contains(/"fluent":true/)

            booleans.getIsJsonProperty()
            json.contains(/"isJsonProperty":true/)

            booleans.isFluentProperty()
            json.contains(/"isFluentProperty":true/)
            json.contains(/"fluentProperty":true/)
    }

    def 'Test Jackson Serialization of Booleans with JsonAutoDetect'() {
        given:
            def booleans = new Booleans2(true, true, true)
            def json = jsonMapper.writeValueAsString(booleans)
        expect:
            booleans.isPrimitive()
            booleans.getIsWrapper()
            booleans.isFluent()
            json == /{"isPrimitive":true,"isWrapper":true,"isFluent":true}/
    }
}
