package records;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toUnmodifiableList;

public record MyRecord(List<Integer> ints) {

    public static void main(String... args) {
        MyRecord r1 = new MyRecord(1, 2, 3, 4);
        MyRecord r2 = new MyRecord(1, 2, 3, 4);

        System.out.println(r1 == r2);
        System.out.println(r1.equals(r2));
        System.out.println(r1.ints.equals( List.of(1, 2, 3, 4) ));
    }

    public MyRecord(int... ints) {
        this( IntStream.of(ints).boxed().collect(toUnmodifiableList()) );
    }
}
