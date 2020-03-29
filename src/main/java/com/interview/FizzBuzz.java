package com.interview;

import java.util.stream.IntStream;

public class FizzBuzz {
    public static void main(String... args) {
        IntStream.rangeClosed(1, 100)
                .mapToObj(it -> it % 15 == 0 ? "FizzBuzz" : it % 3 == 0 ? "Fizz" : it % 5 == 0 ? "Buzz" : it)
                .forEach(System.out::println);
    }
}
