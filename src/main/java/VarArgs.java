import java.util.Objects;
import java.util.stream.Stream;

public class VarArgs {

    public static void main(String... args) {
        Object[] objects = { new Object(), new Object() };
        Integer[] integers = { 1, 2 };
        int[] ints = { 1, 2 };

        printCount(objects);    // size == 2
//        printCount(integers);   // size == 2
//        printCount(ints);       // size == 1
    }

    static void printCount(Object... objects) {
        System.out.println( objects.length );
    }

}
