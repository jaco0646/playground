package generics

import spock.lang.Specification

import static generics.SubCompare.*

class SubCompareSpec extends Specification {

    def 'Compare lists of different subtypes'() {
        given:
            MyInterface a = new Impl1(42L, "foo 1", 1)
            MyInterface b = new Impl1(43L, "foo 2", 2)
            MyInterface c = new Impl2(null, "foo 1", 1, List.of("c"))
            MyInterface d = new Impl2(null, "foo 2", 2, List.of("d"))
        expect:
            equals([], [], MyInterface::foo)
            equals([a], [c], MyInterface::foo, MyInterface::bar)
            !equals([a], [c], MyInterface::foo, MyInterface::bar, MyInterface::id)
            !equals([a], [b], MyInterface::foo, MyInterface::bar)
            equals([a, b], [c, d], MyInterface::foo, MyInterface::bar)
            !equals([a, b], [c, c], MyInterface::foo)
    }

    interface MyInterface {
        Long id();
        String foo();
        int bar();
    }

    static record Impl1(Long id, String foo, int bar) implements MyInterface { }
    static record Impl2(Long id, String foo, int bar, List<String> baz) implements MyInterface { }

}