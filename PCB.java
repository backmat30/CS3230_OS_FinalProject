public class PCB {
    private int pid;
    private String name;
    private String state;
    private boolean active;

    public PCB(int pid, String name) {
        this.pid = pid;
        this.name = name;
        this.state = "READY"; // Starts in READY state
        this.active = true;
    }

    // Getters
    public int getPid() { return pid; }
    public String getName() { return name; }
    public String getState() { return state; }
    public boolean isActive() { return active; }

    // Setters
    public void setState(String state) { this.state = state; }
    public void setActive(boolean active) { this.active = active; }

    // This makes the process look nice when you type 'ps'
    @Override
    public String toString() {
        return "PID " + pid + " [" + name + "] - " + state;
    }
}