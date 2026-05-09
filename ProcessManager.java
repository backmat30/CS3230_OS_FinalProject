import java.util.ArrayList;

public class ProcessManager
{
    // list to store all processes
    private ArrayList<PCB> processes;

    // variable used to assign unique process IDs
    private int nextPid;

    public ProcessManager()
    {
        processes = new ArrayList<>();
        nextPid = 1;
    }


    public void createProcess(String name)
    {
        PCB process = new PCB(nextPid, name);

        processes.add(process);

        System.out.println("Process created:");
        System.out.println(process);

        nextPid++;
    }

    public void listProcesses()
    {
        if (processes.isEmpty())
        {
            System.out.println("No processes found.");
            return;
        }

        System.out.println("\n--- Process List ---");

        for (PCB process : processes)
        {
            System.out.println(process);
        }
    }

    public void schedule()
    {
        if (processes.isEmpty())
        {
            System.out.println("No processes to schedule.");
            return;
        }

        System.out.println("\n--- Running Scheduler ---");

        for (PCB process : processes)
        {
            // only schedule the READY processes
            if (process.getState().equals("READY"))
            {
                try
                {
                    // change state to RUNNING
                    process.setState("RUNNING");

                    System.out.println(
                        "Running Process -> PID: " +
                        process.getPid() +
                        " Name: " +
                        process.getName()
                    );

                    // simulate running time
                    Thread.sleep(5000);

                    // return process back to the READY state
                    process.setState("READY");

                    System.out.println(
                        "Process " +
                        process.getPid() +
                        " finished time slice."
                    );
                }
                catch (InterruptedException e)
                {
                    System.out.println("Scheduler interrupted.");
                }
            }
        }

        System.out.println("--- Scheduler Finished ---");
    }

    public ArrayList<PCB> getProcesses()
    {
        return processes;
    }
}