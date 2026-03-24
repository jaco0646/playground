package spring.profiles

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE

@ActiveProfiles('list')
@SpringBootTest(webEnvironment = NONE)
class YmlListTest extends Specification {

    // Spring will split a comma-separated string into a list,
    //  but it will not parse a yml list directly.
    @Value('${my.list}')
    List<String> configList

    def 'Read List from YML config'() {
        expect:
            configList == ['foo', 'bar', 'baz']
    }

}