package spring.profiles

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.core.env.Environment
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@ActiveProfiles(['foo', 'baz'])
@SpringBootTest(webEnvironment = NONE)
class ProfilesConfigFoobazTest extends Specification {

    @Autowired
    ApplicationContext ctx

    def testFooBazProfiles() {
        given:
            Environment env = ctx.environment
        expect:
            env.getActiveProfiles().length == 2
            env.getActiveProfiles()[0] == 'foo'
            env.getActiveProfiles()[1] == 'baz'
        and:
            !ctx.containsBean('foobarBean')
            ctx.containsBean('foobazBean')
    }
}
