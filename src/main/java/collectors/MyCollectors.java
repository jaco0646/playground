package collectors;

import java.util.stream.Collector;
import java.util.stream.Stream;
import static java.util.stream.Collectors.*;

public class MyCollectors {

    public static void main(String... args) {
        line();
        average();
        andThen();
        count();
        filter();
        flatMap();
        group();
    }

    static Stream<String> oneTwoThreeFour() {
        return Stream.of("1", "2", "3", "4");
    }

    static Stream<String> fooBarBazQux() {
        return Stream.of("foo", "bar", "baz", "qux");
    }

    static void line() {
        System.out.println("----");
    }

    static void average() {
        System.out.println("averagingDouble:\t" + oneTwoThreeFour().collect(averagingDouble(Double::valueOf)));
        System.out.println("averagingInt:\t\t"  + oneTwoThreeFour().collect(averagingInt(Integer::valueOf)));
        System.out.println("averagingLong:\t\t" + oneTwoThreeFour().collect(averagingLong(Long::valueOf)));
        line();
    }

    static void andThen() {
        Collector<CharSequence, ?, String> joining = joining(" ", "", " ");
        System.out.println("collectingAndThen: " + fooBarBazQux().collect(collectingAndThen(joining, s -> s.repeat(2))));
        line();
    }

    static void count() {
        System.out.println("counting: " + oneTwoThreeFour().count());
        line();
    }

    static void filter() {
        System.out.println("filtering: " + fooBarBazQux().collect(filtering(s -> s.startsWith("b"), toList())));
        line();
    }

    static void flatMap() {
        System.out.println("flatMapping: " + fooBarBazQux().collect(flatMapping(String::lines, joining(" "))));
        line();
    }

    static void group() {
        System.out.println("groupingBy: " + fooBarBazQux().collect(groupingBy(s -> s.charAt(0))));
        System.out.println("groupingBy: " + fooBarBazQux().collect(groupingBy(s -> s.charAt(0), joining(""))));
        line();
    }

    static void map() {

    }

}
