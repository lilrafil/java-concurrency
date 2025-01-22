package forkjoinpool;

import java.awt.image.BufferedImage;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class ParallelImageProcessing {

    static class BlurTask extends RecursiveTask<BufferedImage> {
        private BufferedImage image;
        private int xStart, xEnd;

        public BlurTask(BufferedImage image, int xStart, int xEnd) {
            this.image = image;
            this.xStart = xStart;
            this.xEnd = xEnd;
        }

        @Override
        protected BufferedImage compute() {
            if (xEnd - xStart < 100) { // If task is small enough, process directly
                return applyBlur(image, xStart, xEnd);
            } else {
                int mid = (xStart + xEnd) / 2;
                BlurTask leftTask = new BlurTask(image, xStart, mid);
                BlurTask rightTask = new BlurTask(image, mid, xEnd);

                leftTask.fork();
                rightTask.fork();

                BufferedImage leftResult = leftTask.join();
                BufferedImage rightResult = rightTask.join();
                return mergeImages(leftResult, rightResult);
            }
        }

        private BufferedImage applyBlur(BufferedImage image, int xStart, int xEnd) {
            // Simplified blur operation for demonstration
            BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
            for (int x = xStart; x < xEnd; x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    result.setRGB(x, y, image.getRGB(x, y));  // Simulating blur
                }
            }
            return result;
        }

        private BufferedImage mergeImages(BufferedImage left, BufferedImage right) {
            // Simulate merging two blurred parts of the image
            BufferedImage merged = new BufferedImage(left.getWidth() + right.getWidth(), left.getHeight(), left.getType());
            for (int x = 0; x < left.getWidth(); x++) {
                for (int y = 0; y < left.getHeight(); y++) {
                    merged.setRGB(x, y, left.getRGB(x, y));
                }
            }
            for (int x = 0; x < right.getWidth(); x++) {
                for (int y = 0; y < right.getHeight(); y++) {
                    merged.setRGB(x + left.getWidth(), y, right.getRGB(x, y));
                }
            }
            return merged;
        }
    }

    public static void main(String[] args) {
        BufferedImage image = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);
        ForkJoinPool pool = new ForkJoinPool();

        BlurTask task = new BlurTask(image, 0, image.getWidth());
        BufferedImage result = pool.invoke(task);

        System.out.println("Blurred image processed.");
    }
}
