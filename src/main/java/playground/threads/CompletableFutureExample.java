package playground.threads;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class CompletableFutureExample {
    public static void main(String... args) throws InterruptedException {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(CompletableFutureExample::hello);
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(CompletableFutureExample::world);

        System.out.println("main");
        Thread.sleep(2000);
        System.out.println("done");

        System.out.println(
            Stream.of(future1, future2)
                .map(CompletableFuture::join)
                .collect(joining())
        );
    }

    static String hello() {
        sleep(500);
        System.out.println("Hello, ");
        return "Hello, ";
    }

    static String world() {
        sleep(1500);
        System.out.println("World.");
        return "World.";
    }

    static void sleep(int millis) {
        try { Thread.sleep(millis); }
        catch (InterruptedException ignore) {}
    }
}

// main
// Hello,
// World.
// done
// Hello, World.