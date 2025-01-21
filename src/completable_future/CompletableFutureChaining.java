package completable_future;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureChaining {
    public static void main(String[] args) {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 5);

        future.thenApply(result -> result * 2)  // Multiply by 2
                .thenApply(result -> result + 3)  // Add 3
                .thenAccept(result -> System.out.println(result));  // Final result: 13
    }
}

