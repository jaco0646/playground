package spring.feign

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.TestPropertySource
import wiremock.AbstractWireMockSpec

import static com.github.tomakehurst.wiremock.client.WireMock.*

@TestPropertySource(properties = 'http.status.url=${wiremock.server.http.url}')
class HttpStatusClientSpec extends AbstractWireMockSpec {

    @Autowired
    HttpStatusClient httpStatusClient

    def wireMockTest() {
        setup:
            WIREMOCK.stubFor(get('/200')
                    .withHeader('Accept', containing('json'))
                    .willReturn(ok()
                            .withHeader('Content-Type', 'application/json')
                            .withBody('{"code":200,"description":"foo"}')));
        when:
            def result = httpStatusClient.twoHundred()
        then:
            result.code() == 200

            WIREMOCK.verify(getRequestedFor(urlPathEqualTo('/200'))
                    .withHeader('Accept', equalTo('application/json')));
    }
}
