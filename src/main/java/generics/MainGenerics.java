package generics;

import java.util.ArrayList;
import java.util.List;

public class MainGenerics {
    interface MyInterface { default void method(Object o) { throw new UnsupportedOperationException(); }}
    interface MyInterfaceOverload extends MyInterface { void method(String s); }

    interface Generic<T extends CharSequence> { default void method(T o) { throw new UnsupportedOperationException(); }}
    interface GenericOverload extends Generic<String> { void method(String s); }

    public static void main(String... args) {
        // The raw call succeeds because in the absence of a generic type, Java prefers to call a non-generic method.
        Generic raw = (GenericOverload) System.out::println;
        raw.method("Hello World");

        MyInterface mio = (MyInterfaceOverload) System.out::println;
        mio.method("Hello World");
    }



    void function() {
        genericTCompiles(new ArrayList<Integer>());
        genericQCompiles(new ArrayList<Integer>());

        nestedGenericTCompiles(new ArrayList<List<Integer>>());
//        nestedGenericQDoesntCompile(new ArrayList<List<Integer>>());
    }

    <T extends Number> void genericTCompiles(List<T> list) {}
    void genericQCompiles(List<? extends Number> list) {}

    <T extends Number> void nestedGenericTCompiles(List<List<T>> list) {}
    void nestedGenericQDoesntCompile(List<List<? extends Number>> list) {}
}
