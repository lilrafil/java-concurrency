package advance_locking;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockExample {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private int sharedData = 0;

    // Method that holds the read lock twice (demonstrating reentrancy)
    public void readTwice() {
        lock.readLock().lock();  // First acquisition of the read lock
        try {
            System.out.println("First read operation: " + sharedData);

            // Thread can re-acquire the read lock without blocking
            lock.readLock().lock();  // Reacquiring the read lock
            try {
                System.out.println("Second read operation (reentrant): " + sharedData);
            } finally {
                lock.readLock().unlock(); // Releasing the second lock
            }

        } finally {
            lock.readLock().unlock(); // Releasing the first lock
        }
    }

    // Method to write to the shared data
    public void write(int newData) {
        lock.writeLock().lock();
        try {
            sharedData = newData;
            System.out.println("Writing shared data: " + sharedData);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantReadWriteLockExample example = new ReentrantReadWriteLockExample();

        // Thread 1: Read operation (reentrant)
        Thread thread1 = new Thread(example::readTwice);

        // Thread 2: Write operation
        Thread thread2 = new Thread(() -> example.write(42));

        thread1.start();
        thread2.start();
    }
}

