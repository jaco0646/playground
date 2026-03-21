package spring.profiles

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.core.env.Environment
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@ActiveProfiles('local')
@SpringBootTest(webEnvironment = NONE)
class ProfilesConfigLocalTest extends Specification {

    @Autowired
    ApplicationContext ctx

    def 'Property files support interpolation.'() {
        given:
            Environment env = ctx.environment
        expect:
            env.getProperty('env') == ('LOCAL')
            env.getProperty('message').contains(' LOCAL ')
    }
}
