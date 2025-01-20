package custom_synchronizers;

public class CustomLockWithCondition {
    private boolean isLocked = false;

    public synchronized void lock() throws InterruptedException {
        while (isLocked) {
            wait(); // Wait until the lock is available
        }
        isLocked = true;
    }

    public synchronized void unlock() {
        isLocked = false;
        notify();  // Notify one waiting thread that the lock is available
    }
}
