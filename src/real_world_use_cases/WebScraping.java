package real_world_use_cases;

import java.util.concurrent.CompletableFuture;

public class WebScraping {
    public static void main(String[] args) {
        CompletableFuture<String> site1 = CompletableFuture.supplyAsync(() -> scrapeWebsite("http://site1.com"));
        CompletableFuture<String> site2 = CompletableFuture.supplyAsync(() -> scrapeWebsite("http://site2.com"));
        CompletableFuture<String> site3 = CompletableFuture.supplyAsync(() -> scrapeWebsite("http://site3.com"));

        // Combine all results once they are completed
        CompletableFuture.allOf(site1, site2, site3).thenRun(() -> {
            System.out.println("All websites scraped successfully!");
        });

        // You can also combine and process the results
        CompletableFuture<Void> allResults = site1.thenCombine(site2, (result1, result2) -> {
            return result1 + " + " + result2; // Combine results
        }).thenCombine(site3, (combinedResults, result3) -> {
            return combinedResults + " + " + result3; // Add third result
        }).thenAccept(System.out::println); // Output combined result
    }

    private static String scrapeWebsite(String url) {
        // Simulate web scraping logic
        return "Data from " + url;
    }
}

