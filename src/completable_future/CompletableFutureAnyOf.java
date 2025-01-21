package completable_future;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureAnyOf {
    public static void main(String[] args) {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            return "Task 1";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            return "Task 2";
        });

        CompletableFuture<Object> anyFuture = CompletableFuture.anyOf(future1, future2);
        anyFuture.thenAccept(result -> System.out.println("First completed: " + result));
    }
}
