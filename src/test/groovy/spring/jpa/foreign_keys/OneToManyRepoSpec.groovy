package spring.jpa.foreign_keys

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import spock.lang.Specification

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class OneToManyRepoSpec extends Specification {
    @Autowired
    OneToManyRepository parentRepo

    @Autowired
    ManyToOneRepository childRepo

    def "New parent and child are both assigned IDs and dates"() {
        given:
            def parent = new OneToManyEntity()
            parent.setChildren([new ManyToOneEntity()])
        when:
            def persisted = parentRepo.save(parent)
        then:
            def persistedChild = persisted.children.first()
            persisted.id > 0
            persistedChild.id > persisted.id
            persisted.createdDate
            persisted.createdDate == persisted.lastModifiedDate
            persistedChild.createdDate
            persistedChild.createdDate == persistedChild.lastModifiedDate
    }

    def "Appended child is assigned IDs and dates"() {
        given:
            def parent = new OneToManyEntity()
            parent.setChildren([new ManyToOneEntity()])
            def persisted = parentRepo.save(parent)
            persisted.children.add(new ManyToOneEntity())
        when:
            def persisted2 = parentRepo.save(persisted)
        then:
            persisted2.children.size() == 2
            def firstChild = persisted2.children.first()
            def secondChild = persisted2.children.last()
            secondChild.id > firstChild.id
            secondChild.createdDate
    }

    def 'Can retrieve many-to-one through indirect lazy reference using method but not field'() {
        given:
            def child = childRepo.findById(2).orElseThrow()
        expect:
            child.parentIdMethod == 1
        when:
            child.parentIdField1
        then:
            thrown(NullPointerException)
        when:
            child.parentIdField2
        then:
            thrown(NullPointerException)
    }

    def 'Can retrieve one-to-many through indirect lazy reference using field'() {
        given:
            def parent = parentRepo.findById(1).orElseThrow()
        expect:
            parent.firstChildID == 2
    }
}
