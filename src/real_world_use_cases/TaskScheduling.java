package real_world_use_cases;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TaskScheduling {
    public static void main(String[] args) {
        CompletableFuture<Void> task = CompletableFuture.runAsync(() -> {
            System.out.println("Task started");
        }, CompletableFuture.delayedExecutor(3, TimeUnit.SECONDS, Executors.newCachedThreadPool()));

        task.thenRun(() -> System.out.println("Task completed after delay"));
    }
}

