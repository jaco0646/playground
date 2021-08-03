package wiremock

import com.github.tomakehurst.wiremock.WireMockServer
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@SpringBootTest(webEnvironment = NONE)
@ContextConfiguration(initializers = WireMockPropertySetter.class)
abstract class AbstractWireMockSpec extends Specification {
    //TODO static instance won't support parallel tests
    static final WireMockServer WIREMOCK = new WireMockServer(wireMockConfig().dynamicPort())

    def setupSpec() {
        WIREMOCK.start()
    }

    def cleanup() {
        WIREMOCK.resetAll()
    }

    def cleanupSpec() {
        WIREMOCK.stop()
    }

    static class WireMockPropertySetter implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        void initialize(ConfigurableApplicationContext cxt) {
            TestPropertyValues.of([
                    'wiremock.server.http.port' : WIREMOCK.port().toString(),
                    'wiremock.server.http.url' : WIREMOCK.baseUrl()
            ]).applyTo(cxt.getEnvironment())
        }
    }

}
