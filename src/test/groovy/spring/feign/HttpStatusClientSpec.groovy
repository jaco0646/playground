package spring.feign

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.test.context.TestPropertySource
import wiremock.AbstractWireMockSpec

import static com.github.tomakehurst.wiremock.client.WireMock.*
import static org.springframework.http.HttpHeaders.ACCEPT
import static org.springframework.http.HttpHeaders.CONTENT_TYPE
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE

@TestPropertySource(properties = 'http.status.url=${wiremock.server.http.url}')
class HttpStatusClientSpec extends AbstractWireMockSpec {

    @Autowired
    HttpStatusClient httpStatusClient

    @Autowired
    HttpStatusClient2 responseEntityClient

    def wireMockTest() {
        setup:
            stub200()
        when:
            def result = httpStatusClient.twoHundred()
        then:
            result.code() == 200

            WIREMOCK.verify(getRequestedFor(urlPathEqualTo('/200'))
                    .withHeader(ACCEPT, equalTo(APPLICATION_JSON_VALUE)))
    }

    def responseEntityTest() {
        setup:
            stub200()
        when:
            def response = responseEntityClient.twoHundred()
        then:
            response.statusCode == HttpStatus.OK
            response.body instanceof HttpStatusClient2.HttpStatus
            response.body.code == HttpStatus.OK.value()
            response.body.description == HttpStatus.OK.reasonPhrase
            response.headers[CONTENT_TYPE].first() == APPLICATION_JSON_VALUE
    }

    void stub200() {
        WIREMOCK.stubFor(get('/200')
                .withHeader(ACCEPT, equalTo(APPLICATION_JSON_VALUE))
                .willReturn(ok()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBody('{"code":200,"description":"OK"}')))
    }
}
