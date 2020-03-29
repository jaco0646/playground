package playground.threads;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class CompletableFutureException {
    public static void main(String... args) {
        awaitAllIgnoreExceptions();
    }

    static void awaitAllIgnoreExceptions() {
        Supplier<String> s1 = () -> { throw new RuntimeException(); };
        Supplier<String> s2 = () -> "Hello, ";
        Supplier<String> s3 = () -> "World";
        System.out.println(
            Stream.of(s1, s2, s3)
                .map(CompletableFuture::supplyAsync)
                .map(future -> future.exceptionally(t -> ""))
                .map(CompletableFuture::join)
                .collect(joining())
        );
    }

    static void awaitAnyIgnoreExceptions() {
        // https://stackoverflow.com/questions/33913193/completablefuture-waiting-for-first-one-normally-return
    }
}
