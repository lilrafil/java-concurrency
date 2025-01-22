package forkjoinpool;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;
import java.util.List;

public class ParallelWordCount {

    static class WordCountTask extends RecursiveTask<Integer> {
        private List<String> lines;
        private int start, end;

        public WordCountTask(List<String> lines, int start, int end) {
            this.lines = lines;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end - start <= 10) {
                return countWords(lines.subList(start, end));
            } else {
                int mid = (start + end) / 2;
                WordCountTask leftTask = new WordCountTask(lines, start, mid);
                WordCountTask rightTask = new WordCountTask(lines, mid, end);

                leftTask.fork();
                rightTask.fork();

                return leftTask.join() + rightTask.join();
            }
        }

        private int countWords(List<String> lines) {
            int count = 0;
            for (String line : lines) {
                count += line.split("\\s+").length;
            }
            return count;
        }
    }

    public static void main(String[] args) {
        List<String> lines = List.of("This is a test", "ForkJoinPool example", "Parallel word count");
        ForkJoinPool pool = new ForkJoinPool();

        WordCountTask task = new WordCountTask(lines, 0, lines.size());
        int totalCount = pool.invoke(task);

        System.out.println("Total word count: " + totalCount);
    }
}
