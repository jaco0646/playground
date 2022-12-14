package spring.envers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE
import static org.springframework.data.history.RevisionMetadata.RevisionType

/**
 * Not using @DataJpaTest to avoid running in a Transaction.
 * Entity and Revisions should be persisted before the test ends.
 */
@SpringBootTest(webEnvironment = NONE)
class PersonAuditingSpec extends Specification {
    @Autowired
    PersonRepository personRepo

    def 'Person Entity is Audited with Envers'() {
        given:
            def person = personRepo.save(new Person(firstName: 'John', lastName: 'Doe'))
        expect:
            personRepo.findRevisions(person.id).content.isEmpty()  // Auditing insert events is disabled. See: EnversConfig.
        when:
            person.firstName = 'Jane'
            personRepo.save(person)
        then:
            personRepo.findRevisions(person.id).content.size() == 1
            personRepo.findRevisions(person.id).content.first().metadata.revisionType == RevisionType.UPDATE
        cleanup:
            personRepo.delete(person)
    }
}
