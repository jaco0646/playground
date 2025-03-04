package streams

import spock.lang.Specification

import java.util.function.Consumer
import java.util.function.Predicate
import java.util.function.UnaryOperator

import static streams.Selector.if_
import static streams.Utils.*

class UtilsSpec extends Specification {
    static final List<String> elements = ['foo', 'bar', 'baz', 'qux']
    static final Predicate<String> startsWithBA = it -> it.toLowerCase().startsWith('ba')

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

    def "test IF THEN ELSE consumer"() {
        given:
            def matches = []
            def mismatches = []
        when:
            elements.forEach(if_(startsWithBA).thenDo(matches::add).elseDo(mismatches::add))
        then:
            matches == ['bar', 'baz']
            mismatches == ['foo', 'qux']
    }

    def "test IF THEN ELSE mapper"() {
        expect:
            ['foo', 'BAR', 'baz', 'QUX'].stream()
                .map(if_(startsWithBA).thenMap(String::toUpperCase).elseMap(String::toLowerCase))
                .toList() == ['foo', 'BAR', 'BAZ', 'qux']
    }

    def "test EntrySet Collector"() {
        expect:
            [foo : ' bar  ', baz : '  qux ']
                    .entrySet()
                    .stream()
                    .map(newKey(String::toUpperCase))
                    .map(newValue(String::trim))
                    .collect(toMap()) == [FOO: 'bar', BAZ: 'qux']
    }
}
