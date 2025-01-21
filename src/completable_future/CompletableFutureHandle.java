package completable_future;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureHandle {
    public static void main(String[] args) {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            return 10 / 0;  // Will cause ArithmeticException
        });

        future.handle((result, ex) -> {
            if (ex != null) {
                System.out.println("Handled exception: " + ex.getMessage());
                return -1;  // Fallback value
            }
            return result;
        }).thenAccept(result -> System.out.println("Result: " + result));  // Prints: Result: -1
    }
}

