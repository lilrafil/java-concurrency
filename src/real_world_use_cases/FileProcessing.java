package real_world_use_cases;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.CompletableFuture;

public class FileProcessing {
    public static void main(String[] args) {
        CompletableFuture<Void> task1 = CompletableFuture.runAsync(() -> {
            try {
                Files.readAllLines(Paths.get("file1.txt"));
                System.out.println("File 1 processed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        CompletableFuture<Void> task2 = CompletableFuture.runAsync(() -> {
            try {
                Files.readAllLines(Paths.get("file2.txt"));
                System.out.println("File 2 processed");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        CompletableFuture.allOf(task1, task2).thenRun(() -> {
            System.out.println("Both files processed successfully!");
        });
    }
}
