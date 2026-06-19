package playground

import com.interview.Calculator
import spock.lang.Specification

/**
 * Implement the mean, median, mode and range methods of the Calculator class in Java 8 Functional
 * style with the minimum code necessary to pass the tests.
 */
class FunctionalTest extends Specification {
    private final Calculator calculator = new Calculator()

    void range() {
        expect:
            calculator.range(3, 17, 15, 11, 9) == 14
    }

    void mean() {
        expect:
            calculator.mean(13, 19, null, 14, 16, 5, 8) == 12.5d
    }

    void median() {
        expect:
            calculator.median(7, 11, 6, 2, 5) == 6d
            calculator.median(13, 18, 14, 16, 5, 8) == 13.5d
    }

    void mode() {
        expect:
            calculator.mode(5, 2, 3, 6, 4, 1, 3) == new int[]{3}
            calculator.mode(4, 5, 3, 1, 3, 2, 5, 6) == new int[]{3, 5}
            calculator.mode(4, 5, 5, 3, 1, 3, 2, 5, 6) == new int[]{5}
            calculator.mode(1, 3, 2, 4, 5) == new int[]{1, 2, 3, 4, 5}
    }
}
