package playground

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import spock.lang.Specification

class JacksonSpec extends Specification {

    def jacksonNull() {
        given:
            ObjectMapper mapper = new ObjectMapper()
            ObjectNode eventNode = mapper.valueToTree([
                    person: [
                            name    : name,
                            location: location,
                    ]
            ])
            JsonNode nameNode = eventNode.get('person').get('name')
            JsonNode locationNode = eventNode.get('person').get('location')

        expect:
            nameNode.isTextual()
            nameNode.textValue() == 'Tom'
            nameNode.toString() == '"Tom"'

            locationNode.isNull()
            locationNode.textValue() == null
            locationNode.toString() == 'null'

        where:
            name  | location
            'Tom' | null
    }

}
