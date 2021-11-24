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
    OneToManyRepository repo

    def "New parent and child are both assigned IDs and dates"() {
        given:
            def parent = new OneToManyEntity()
            parent.setChildren([new ManyToOneEntity()])
        when:
            def persisted = repo.save(parent)
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
            def persisted = repo.save(parent)
            persisted.children.add(new ManyToOneEntity())
        when:
            def persisted2 = repo.save(persisted)
        then:
            persisted2.children.size() == 2
            def firstChild = persisted2.children.first()
            def secondChild = persisted2.children.last()
            secondChild.id > firstChild.id
            secondChild.createdDate
    }
}
