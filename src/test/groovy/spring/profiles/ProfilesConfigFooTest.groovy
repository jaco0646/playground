package spring.profiles

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.core.env.Environment
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@ActiveProfiles('foo')
@SpringBootTest(webEnvironment = NONE)
class ProfilesConfigFooTest extends Specification {

    @Autowired
    ApplicationContext ctx

    def testFooProfile() {
        given:
            Environment env = ctx.environment
        expect:
            env.getActiveProfiles().length == 1
            env.getActiveProfiles()[0] == 'foo'
        and:
            !ctx.containsBean('foobarBean')
            !ctx.containsBean('foobazBean')
    }
}
