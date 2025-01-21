package real_world_use_cases;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

public class ParallelDataProcessing {
    public static void main(String[] args) {
        int[] data = IntStream.range(1, 100).toArray();  // Sample data

        CompletableFuture<Void> processing = CompletableFuture.allOf(
                IntStream.range(0, data.length).mapToObj(i -> CompletableFuture.runAsync(() -> {
                    processData(data[i]);  // Process each data item asynchronously
                })).toArray(CompletableFuture[]::new)
        );

        processing.join();  // Wait for all tasks to complete
        System.out.println("All data processed.");
    }

    private static void processData(int value) {
        System.out.println("Processing data: " + value);
    }
}

