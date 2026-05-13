public class PCB {
    private int pid;
    private String name;
    private State state;
    private boolean active;

    public enum State {
        READY,
        RUNNING,
        BLOCKED
    }

    public PCB(int pid, String name) {
        this.pid = pid;
        this.name = name;
        this.state = State.READY; // Starts in READY state
        this.active = true;
    }

    // Getters
    public int getPid() { return pid; }
    public String getName() { return name; }
    public State getState() { return state; }
    public boolean isActive() { return active; }

    // Setters
    public void setState(State state) { this.state = state; }
    public void setActive(boolean active) { this.active = active; }

    // This makes the process look nice when you type 'ps'
    @Override
    public String toString() {
        return "PID " + pid + " [" + name + "] - " + state;
    }
}