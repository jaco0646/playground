package spring.jpa

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.springframework.boot.jdbc.EmbeddedDatabaseConnection.H2
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@SpringBootTest(webEnvironment = NONE)
class PlaygroundRepositorySpec extends Specification {

    @Autowired
    DataSourceProperties datasource

    @Autowired
    PlaygroundRepository playgroundRepo

    def 'Assert the SQL DataSource is H2'() {
        expect:
            datasource.embeddedDatabaseConnection == H2
    }

    def 'Assert the in-memory database is readable and writable'() {
        expect:
            playgroundRepo.count() == 0
        when:
            playgroundRepo.saveAndFlush(new KeyValue('foo', 'bar'))
        then:
            playgroundRepo.existsById('foo')
    }
}
