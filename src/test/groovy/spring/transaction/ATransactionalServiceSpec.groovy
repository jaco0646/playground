package spring.transaction

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@SpringBootTest(webEnvironment = NONE)
class ATransactionalServiceSpec extends Specification {
    @Autowired
    ATransactionlService service

    @Autowired
    MyEntityRepository repo

    static final String msg1 = 'first message'
    static final String msg2 = 'second message'

    def cleanup() {
        repo.deleteAll()  // Because the suite is not @Transactional so doesn't auto rollback
    }

    def "test persistTwoEntities"() {
        when:
            service.persistTwoEntities(msg1, {msg2})
        then:
            def entities = repo.findAll()
            entities.size() == 2
            entities.sort{it.id}
            entities.first().id == 1
            entities.first().text == msg1
            entities.last().id == 2
            entities.last().text == msg2
    }

    def "test Unchecked exception rolls back"() {
        when:
            service.persistTwoEntities(msg1, {throw new RuntimeException('test unchecked exception')})
        then:
            thrown(RuntimeException)
            repo.count() == 0
    }

    def "test Checked exception does not roll back"() {
        when:
            service.persistTwoEntities(msg1, {throw new IOException('test checked exception')})
        then:
            thrown(IOException)
            repo.count() == 1
    }
}
