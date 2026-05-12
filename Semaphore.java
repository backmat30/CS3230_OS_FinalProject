public class Semaphore {
    private int count;

    //initializer for the semaphore
    public Semaphore(int initialCount) {
        this.count = initialCount;
    }

    //Basically just aquire method for the semaphore
    public synchronized void waitSem() throws InterruptedException {
        while (count <= 0) {
            // Thread sleeps here until count becomes positive
            wait();
        }
        count--;
    }

    //basically just the release method for the semaphore
    public synchronized void signal() {
        count++;
        // notify() wakes up one waiting thread
        notify();
    }
}