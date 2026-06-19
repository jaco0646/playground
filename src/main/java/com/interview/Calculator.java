package com.interview;

import java.util.IntSummaryStatistics;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Collections.reverseOrder;
import static java.util.stream.Collectors.*;

public class Calculator {

    /**
     * range: the difference between min and max values
     */
    public int range(int... input) {
        IntSummaryStatistics summaryStatistics = IntStream.of(input).summaryStatistics();
        return summaryStatistics.getMax() - summaryStatistics.getMin();
    }

    /**
     *  mean: the average of the numbers
     */
    public double mean(Integer... input) {
        return Stream.of(input)
                .filter(Objects::nonNull)
                .mapToInt(i -> i)
                .summaryStatistics()
                .getAverage();
    }

    /**
     * median: the middle number in a sorted list
     * ...if there are two middle values, return the average of the two
     */
    public double median(int... input) {
        int inputCount = input.length;
        boolean isEven = inputCount %2 == 0;
        int middleIndex = isEven ? inputCount/2 : inputCount/2 + 1;
        int outputCount = isEven ? 2 : 1;
        return IntStream.of(input)
                .sorted()
                .skip(middleIndex - 1)
                .limit(outputCount)
                .sum() / (double) outputCount;
    }

    /**
     *  mode: the most frequently occurring number
     */
    public int[] mode(int... input) {
        AtomicInteger max = new AtomicInteger(0);
        return IntStream.of(input).boxed()
                .collect(toMap(i -> i, i -> 1, Integer::sum))
                .entrySet().stream()
                .sorted(reverseOrder(Entry.comparingByValue()))
                .takeWhile(entry -> entry.getValue() >= max.getAndSet(entry.getValue()))
                .mapToInt(Entry::getKey)
                .sorted()
                .toArray();
    }
}
