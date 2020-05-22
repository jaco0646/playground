package collectors;

import java.util.function.Consumer;
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
        forEach();
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

    static void forEach() {
        fooBarBazQux().forEach( ((Consumer<String>) it -> System.out.print("forEach: "))
                .andThen(System.out::print)
                .andThen(it -> System.out.print(new StringBuilder(it).reverse()))
                .andThen(it -> System.out.println(it.toUpperCase()))
        );
        line();
    }

    static void group() {
        System.out.println("groupingBy: " + fooBarBazQux().collect(groupingBy(s -> s.charAt(0))));
        System.out.println("groupingBy: " + fooBarBazQux().collect(groupingBy(s -> s.charAt(0), joining(""))));
        System.out.println("groupingBy: " +
                Stream.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten")
                    .collect(groupingBy(String::length, groupingBy(it -> it.charAt(0))))
        );
        line();
    }

    static void map() {

    }

}
