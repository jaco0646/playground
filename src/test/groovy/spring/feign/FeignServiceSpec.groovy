package spring.feign

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@SpringBootTest(webEnvironment = NONE)
class FeignServiceSpec extends Specification {
    @Autowired
    FeignService feignService

    def "Test Http Success"() {
        given:
            def status = feignService.success()
        expect:
            status.code() == 200
            status.description() == 'OK'
    }
}
