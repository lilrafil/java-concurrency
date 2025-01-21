package completable_future;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureExceptionHandling {
    public static void main(String[] args) {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            if (true) {
                throw new RuntimeException("Something went wrong");
            }
            return 42;
        });

        future.exceptionally(ex -> {
            System.out.println("Handled exception: " + ex.getMessage());
            return 0;  // Fallback value
        }).thenAccept(result -> System.out.println("Result: " + result));  // Prints: Result: 0
    }
}
