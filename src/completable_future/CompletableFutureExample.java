package completable_future;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureExample {
    public static void main(String[] args) {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            return "Hello from CompletableFuture!";
        });

        future1.thenAccept(result -> System.out.println(result)); // Prints: Hello from CompletableFuture!

        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
             System.out.println("Running asynchronously!");
        });

        future2.thenRun(() -> System.out.println("Task finished!"));

    }
}
