package streams

import spock.lang.Specification

import java.util.function.Consumer
import java.util.function.Predicate
import java.util.function.UnaryOperator

import static streams.Utils.ifElse

class UtilsSpec extends Specification {
    static final List<String> elements = ['foo', 'bar', 'baz', 'qux']
    static final Predicate<String> startsWithBA = it -> it.startsWith('ba')

    def "test ifElse Consumer"() {
        given:
            def matches = []
            def mismatches = []
            Consumer<String> addMatch = matches::add
            Consumer<String> addMismatch = mismatches::add
            elements.forEach(ifElse(startsWithBA, addMatch, addMismatch))
        expect:
            matches == ['bar', 'baz']
            mismatches == ['foo', 'qux']
    }

    def "test ifElse Mapper"() {
        given:
            UnaryOperator<String> toUpperCase = String::toUpperCase
            UnaryOperator<String> reverse = it -> new StringBuilder(it).reverse().toString()
            List<String> modifiedElements = elements.stream()
                    .map(ifElse(startsWithBA, toUpperCase, reverse))
                    .toList()
        expect:
            modifiedElements == ['oof', 'BAR', 'BAZ', 'xuq']
    }
}
