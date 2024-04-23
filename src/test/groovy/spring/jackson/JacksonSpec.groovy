package spring.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

import static com.fasterxml.jackson.annotation.JsonFormat.Value.forPattern
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@SpringBootTest(webEnvironment = NONE)
class JacksonSpec extends Specification {
    @Autowired
    ObjectMapper jsonMapper

    def 'Test deserialization of capitalized json'() {
        setup:
            jsonMapper.configOverride(LocalDateTime).setFormat(forPattern('yyyy-MM-dd HH:mm:ss'))
            def json = / {"Foo":"foo","Bar":"2024-04-22","Baz":"2024-04-22 07:00:00"} /
            def pojo = new Pojo('foo', LocalDate.of(2024, 4, 22), LocalDateTime.of(2024, 4, 22, 7, 0, 0))
        expect:
            jsonMapper.readValue(json, Pojo) == pojo
    }

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
