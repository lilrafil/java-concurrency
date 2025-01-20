package advance_locking;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static int sharedData = 0;

    public static void main(String[] args) {
        // Create and start 10 reader threads
        for (int i = 0; i < 10; i++) {
            new Thread(new Reader()).start();
        }

        // Create and start 1 writer thread
        new Thread(new Writer()).start();
    }

    static class Reader implements Runnable {
        @Override
        public void run() {
            lock.readLock().lock();
            try {
                System.out.println("Reading shared data: " + sharedData);
            } finally {
                lock.readLock().unlock();
            }
        }
    }

    static class Writer implements Runnable {
        @Override
        public void run() {
            lock.writeLock().lock();
            try {
                sharedData++;
                System.out.println("Writing shared data: " + sharedData);
            } finally {
                lock.writeLock().unlock();
            }
        }
    }
}
