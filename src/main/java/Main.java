import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String... args) {

        System.out.println(
                newStream().filter(s -> s.startsWith("ba")).collect(Collectors.joining())
        );

    }

    public static Stream<String> newStream() {
        return Stream.of("foo", "bar", "baz");
    }

}