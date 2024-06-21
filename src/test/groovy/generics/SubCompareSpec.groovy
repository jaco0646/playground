package generics

import spock.lang.Specification

import java.time.LocalDate

import static generics.SubCompare.*

class SubCompareSpec extends Specification {

    def 'Compare lists of different subtypes'() {
        given:
            LocalDate today = LocalDate.now()
            LocalDate yesterday = today.minusDays(1)
            LocalDate tomorrow = today.plusDays(1)
            MyInterface a = new Impl1(42L, today, tomorrow, 1)
            MyInterface b = new Impl1(43L, yesterday, today, 2)
            MyInterface c = new Impl2(null, today, tomorrow, 1, List.of("c"))
            MyInterface d = new Impl2(null, yesterday, today, 1, List.of("d"))
            MyInterface e = new Impl2(null, tomorrow, today, 1, List.of("e"))
        expect:
            equals([], [], MyInterface::start)
            equals([a], [c], MyInterface::start, MyInterface::end, MyInterface::weight)
            !equals([a], [c], MyInterface::start, MyInterface::end, MyInterface::id)
            !equals([a], [b], MyInterface::start, MyInterface::end)
            !equals([a], [e], MyInterface::start, MyInterface::end)
            equals([a, b], [c, d], MyInterface::start, MyInterface::end)
            !equals([a, b], [d, e], MyInterface::end)
            !equals([a, a, b], [a, b, b], MyInterface::start, MyInterface::end)
    }

    interface MyInterface {
        Long id();
        LocalDate start();
        LocalDate end();
        Integer weight();
    }

    static record Impl1(Long id, LocalDate start, LocalDate end, Integer weight) implements MyInterface { }
    static record Impl2(Long id, LocalDate start, LocalDate end, Integer weight, List<String> baz) implements MyInterface { }

}