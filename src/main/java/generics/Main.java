package generics;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String... args) {

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
