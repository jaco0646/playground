package spring.transaction

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@SpringBootTest(webEnvironment = NONE)
class TransactionalServiceSpec extends Specification {
    @Autowired
    TransactionalService service

    @Autowired
    MyEntityRepository repo

    static final String msg = 'first message'
    static final String errMsgChecked = 'test checked exception'
    static final String errMsgUnchecked = 'test unchecked exception'

    def cleanup() {
        repo.deleteAll()  // Because the suite is not @Transactional so doesn't auto rollback
    }

    def "test persistTwoEntities"() {
        when:
            service.persistTransactional({msg})
        then:
            verifyMessage(msg)
    }

    def "test Unchecked exception rolls back"() {
        when:
            service.persistTransactional({throw new RuntimeException(errMsgUnchecked)})
        then:
            thrown(RuntimeException)
            repo.count() == 0
    }

    def "test Checked exception does not roll back"() {
        when:
            service.persistTransactional({throw new IOException(errMsgChecked)})
        then:
            thrown(IOException)
            verifyMessage(errMsgChecked)
    }

    def "test Unchecked exception does not roll back"() {
        when:
            service.persistNonTransactional({throw new RuntimeException(errMsgUnchecked)})
        then:
            thrown(RuntimeException)
            verifyMessage(errMsgUnchecked)
    }

    void verifyMessage(String msg) {
        verifyAll(repo.findAll()) {
            size() == 1
            first().text == msg
        }
    }
}
