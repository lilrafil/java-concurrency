package custom_synchronizers;

import java.util.concurrent.locks.*;

public class CustomBarrierAQS extends AbstractQueuedSynchronizer {
    private final int totalThreads;
    private int waitingThreads = 0;

    public CustomBarrierAQS(int totalThreads) {
        this.totalThreads = totalThreads;
    }

    // Determines if the barrier is open
    @Override
    protected boolean isHeldExclusively() {
        return waitingThreads == totalThreads;
    }

    // Releases threads once the barrier is reached
    @Override
    protected boolean tryAcquire(int arg) {
        if (waitingThreads < totalThreads) {
            waitingThreads++;
            if (waitingThreads == totalThreads) {
                releaseShared(1);  // Wake up all threads
            }
            return false;
        }
        return true;  // Barrier is open, allow threads to proceed
    }

    // Allows threads to wait at the barrier
    @Override
    protected boolean tryRelease(int arg) {
        return false;  // No action needed, since we're using shared release mechanism
    }

    // Wait until all threads reach the barrier
    public void await() throws InterruptedException {
        if (!tryAcquire(1)) {
            doWait();
        }
    }

    // Perform the waiting operation
    private void doWait() throws InterruptedException {
        LockSupport.park(this);
    }

    // Method to release all threads once the barrier is reached
    public void release() {
        releaseShared(1);
    }

    public static void main(String[] args) {
        final int THREAD_COUNT = 5;
        CustomBarrierAQS barrier = new CustomBarrierAQS(THREAD_COUNT);

        // Create and start 5 threads
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " reached barrier.");
                    barrier.await();  // Threads wait until all have arrived at the barrier
                    System.out.println(Thread.currentThread().getName() + " passed barrier.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
