package spring.valid

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
class ValidationControllerSpec extends Specification {
    @Autowired
    MockMvc mockMvc

    private static final String V1_REQUEST =
        '''
        {   "foo": "foo",
            "time": "2031-09-11",
            "v1": [{
                "foo": "bar",
                "i": 43,
                "time": "2031-09-12",
                "v1": []
            }]
        }
        '''

    def "test createV1"() {
        expect:
            mockMvc.perform(post('/valid')
                    .contentType(APPLICATION_JSON)
                    .content(V1_REQUEST)
            ).andExpect(status().isCreated())
    }
}
