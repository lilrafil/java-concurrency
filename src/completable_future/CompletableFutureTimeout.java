package completable_future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureTimeout {
    public static void main(String[] args) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);  // Simulating long computation
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Task Completed";
        });

        future.orTimeout(3, TimeUnit.SECONDS)
                .exceptionally(ex -> "Timed out");

        future.thenAccept(result -> System.out.println(result));  // Prints: Timed out
    }
}
