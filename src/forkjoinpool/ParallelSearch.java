package forkjoinpool;

import java.util.concurrent.*;
import java.util.*;

public class ParallelSearch {

    public static class SearchTask extends RecursiveTask<Boolean> {
        private String[] data;
        private String target;
        private int start, end;

        public SearchTask(String[] data, String target, int start, int end) {
            this.data = data;
            this.target = target;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Boolean compute() {
            for (int i = start; i < end; i++) {
                if (data[i].contains(target)) {
                    return true; // Found the target
                }
            }
            return false;
        }
    }

    public static void main(String[] args) {
        String[] logData = new String[1000000];
        Random rand = new Random();
        for (int i = 0; i < logData.length; i++) {
            logData[i] = "log entry " + rand.nextInt(1000);  // Simulate log entries
        }

        String targetKeyword = "log entry 500";  // Search target

        ForkJoinPool pool = new ForkJoinPool();
        SearchTask task = new SearchTask(logData, targetKeyword, 0, logData.length);
        Boolean found = pool.invoke(task);

        System.out.println("Target keyword found: " + found);
    }
}
