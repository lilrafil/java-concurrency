package forkjoinpool;

import java.util.concurrent.*;
import java.util.*;

public class ParallelMergeSort {

    // MergeSort using ForkJoinPool
    public static class MergeSortTask extends RecursiveTask<int[]> {
        private int[] array;

        public MergeSortTask(int[] array) {
            this.array = array;
        }

        @Override
        protected int[] compute() {
            if (array.length <= 1) {
                return array;
            }

            int mid = array.length / 2;
            MergeSortTask leftTask = new MergeSortTask(Arrays.copyOfRange(array, 0, mid));
            MergeSortTask rightTask = new MergeSortTask(Arrays.copyOfRange(array, mid, array.length));

            leftTask.fork();
            rightTask.fork();

            int[] leftResult = leftTask.join();
            int[] rightResult = rightTask.join();

            return merge(leftResult, rightResult);
        }

        // Merging two sorted arrays
        private int[] merge(int[] left, int[] right) {
            int[] result = new int[left.length + right.length];
            int i = 0, j = 0, k = 0;

            while (i < left.length && j < right.length) {
                if (left[i] < right[j]) {
                    result[k++] = left[i++];
                } else {
                    result[k++] = right[j++];
                }
            }

            while (i < left.length) {
                result[k++] = left[i++];
            }
            while (j < right.length) {
                result[k++] = right[j++];
            }

            return result;
        }
    }

    public static void main(String[] args) {
        int[] largeDataset = new int[1000000];
        Random rand = new Random();
        for (int i = 0; i < largeDataset.length; i++) {
            largeDataset[i] = rand.nextInt(10000);  // Generate random data
        }

        ForkJoinPool pool = new ForkJoinPool();
        MergeSortTask task = new MergeSortTask(largeDataset);
        int[] sortedArray = pool.invoke(task);

        System.out.println("Sorted Array: " + Arrays.toString(sortedArray));
    }
}
