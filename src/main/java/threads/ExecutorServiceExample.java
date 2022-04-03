package threads;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ExecutorServiceExample {

    public static void main(String... args) throws InterruptedException {
        Callable<String> c1 = () -> { System.out.println("Hello "); return "Hello "; };
        Callable<String> c2 = () -> { System.out.println("World!"); return "World!"; };
        List<Callable<String>> callables = List.of(c1, c2);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        System.out.println("Begin invokeAll...");
        List<Future<String>> allFutures = executor.invokeAll(callables);
        System.out.println("End invokeAll.\n");

        System.out.println("Begin submit...");
        List<Future<String>> submittedFutures = callables.stream().map(executor::submit).collect(Collectors.toList());
        System.out.println("End submit.");

        executor.shutdown();
    }

}