package spring.jpa.custom_naming

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@SpringBootTest(webEnvironment = NONE)
class IdentifiersTest extends Specification {
    @Autowired
    IdentifiersRepository repo

    def testIDs() {
        given:
            def entity = new Identifiers(1, 2, 3)
        when:
            repo.save(entity)
        then:
            repo.findById(entity.id).orElseThrow() == entity
    }

    def testNames() {
        expect:
            repo.getColumnNames() == ['big_id', 'camel_id', 'id']
    }
}
