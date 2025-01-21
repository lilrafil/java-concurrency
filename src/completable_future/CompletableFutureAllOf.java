package completable_future;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureAllOf {
    public static void main(String[] args) {
        CompletableFuture<Void> future1 = CompletableFuture.supplyAsync(() -> {
            return "Task 1";
        }).thenAccept(result -> System.out.println(result));

        CompletableFuture<Void> future2 = CompletableFuture.supplyAsync(() -> {
            return "Task 2";
        }).thenAccept(result -> System.out.println(result));

        CompletableFuture.allOf(future1, future2).join();  // Waits for both tasks to complete
    }
}

