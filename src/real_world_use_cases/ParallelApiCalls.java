package real_world_use_cases;

import java.util.concurrent.CompletableFuture;

public class ParallelApiCalls {
    public static void main(String[] args) {
        CompletableFuture<String> apiCall1 = CompletableFuture.supplyAsync(() -> callApi("https://api.example.com/data1"));
        CompletableFuture<String> apiCall2 = CompletableFuture.supplyAsync(() -> callApi("https://api.example.com/data2"));

        // Combine results after both API calls are completed
        apiCall1.thenCombine(apiCall2, (response1, response2) -> {
            return response1 + " + " + response2;
        }).thenAccept(System.out::println);  // Output combined results
    }

    private static String callApi(String url) {
        // Simulate an API call
        return "Data from " + url;
    }
}
