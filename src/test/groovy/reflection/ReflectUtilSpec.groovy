package reflection

import spock.lang.Specification
import spock.lang.Unroll

import java.util.stream.Collectors

@Unroll
class ReflectUtilSpec extends Specification {
    def "GetAllInterfaces"() {
        given:
            def output = ReflectUtil.getParents(input).collect(Collectors.toList())
        expect:
            output.size() == expected.size()
            output == expected
        where:
            input         | expected
            LinkedHashMap | [AbstractMap, Cloneable, HashMap, Map, Object, Serializable]
            ArrayList     | [AbstractCollection, AbstractList, Cloneable, Collection, Iterable, List, Object, RandomAccess, Serializable]
    }
}
