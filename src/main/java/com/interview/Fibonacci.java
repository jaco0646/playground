package com.interview;

public class Fibonacci {
    public static void main(String... args) {
        fibonacci(0, 1, 100);
    }

    static void fibonacci(int n1, int n2, int nk) {
        if (n2 > nk) return;
        int n3 = n2 + n1;
        System.out.println(n3);
        fibonacci(n2,n3,nk);
    }
}
