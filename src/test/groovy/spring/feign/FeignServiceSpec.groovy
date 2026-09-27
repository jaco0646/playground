package spring.feign

import http.StaticResponseServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@SpringBootTest(webEnvironment = NONE)
class FeignServiceSpec extends Specification {
    @Autowired
    FeignService feignService

    def "Test Http Success"() {
        given:
            StaticResponseServer localhost = StaticResponseServer.builder()
                    .host("http://localhost/httpstatus/200")
                    .port(80)
                    .status(HttpStatus.OK)
                    .build();
        when:
            def status = feignService.success()
        then:
            status.code() == 200
            status.description() == 'OK'
        cleanup:
            localhost.close()
    }
}
