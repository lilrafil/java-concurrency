package advance_locking;

import java.util.concurrent.locks.StampedLock;

public class StampedLockExample {
    private final StampedLock lock = new StampedLock();
    private int sharedData = 0;

    public void optimisticRead() {
        long stamp = lock.tryOptimisticRead();
        try {
            System.out.println("Reading shared data: " + sharedData);
            // Check if the lock was invalidated during the read operation
            if (!lock.validate(stamp)) {
                stamp = lock.readLock();
                // Perform read with full lock if optimistic read was invalid
                System.out.println("Optimistic read invalidated, retrying with read lock");
            }
        } finally {
            lock.unlockRead(stamp);
        }
    }

    public void write(int newData) {
        long stamp = lock.writeLock();
        try {
            sharedData = newData;
            System.out.println("Writing shared data: " + sharedData);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    public static void main(String[] args) {
        StampedLockExample example = new StampedLockExample();
        example.optimisticRead();
        example.write(42);
    }
}
