package lombok

import spock.lang.Specification
import tools.jackson.databind.PropertyNamingStrategy
import tools.jackson.databind.cfg.MapperConfig
import tools.jackson.databind.introspect.AnnotatedMethod
import tools.jackson.databind.json.JsonMapper

class BooleanPOJOTest extends Specification {
    def testBooleans() {
        given:
            JsonMapper json = JsonMapper.builder()
                    .propertyNamingStrategy(new BooleanNamingStrategy())
                    .build()
            BooleanPOJO bp = new BooleanPOJO(true, true)
            String serialized = json.writeValueAsString(bp)
        expect:
            bp.isObject()
            bp.isPrimitive()
            serialized == '{"isObject":true,"isPrimitive":true}'
            json.readValue(serialized, BooleanPOJO) == bp
    }

    /** Retain any existing prefix (e.g. 'is') on booleans. */
    static class BooleanNamingStrategy extends PropertyNamingStrategy {
        @Override
        String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
            return nameForMethod(method, defaultName)
        }

        @Override
        String nameForSetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
            return nameForMethod(method, defaultName)
        }

        private static String nameForMethod(AnnotatedMethod method, String defaultName) {
            Class<?> returnType = method.getRawReturnType()
            boolean isBoolean = (returnType == boolean.class || returnType == Boolean.class)
            return isBoolean ? method.getName() : defaultName
        }
    }
}
