package real_world_use_cases;

import java.util.concurrent.CompletableFuture;

public class DatabaseQueries {
    public static void main(String[] args) {
        CompletableFuture<String> query1 = CompletableFuture.supplyAsync(() -> queryDatabase("SELECT * FROM Users"));
        CompletableFuture<String> query2 = CompletableFuture.supplyAsync(() -> queryDatabase("SELECT * FROM Orders"));

        // Wait for both queries to complete and then combine results
        query1.thenCombine(query2, (result1, result2) -> {
            return result1 + "\n" + result2;
        }).thenAccept(System.out::println); // Print combined result
    }

    private static String queryDatabase(String query) {
        // Simulate a database query
        return "Result of query: " + query;
    }
}
