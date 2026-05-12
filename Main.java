import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Initialize managers
        ProcessManager pm = new ProcessManager();
        MemoryManager mm = new MemoryManager();

        // Initialize semaphores
        Semaphore memSemaphore = new Semaphore(1); 

        //make scanner to read input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to MiniOS!");

        //determine if the mini os is still running or not
        boolean running = true;

        while (running) {
            System.out.print("MiniOS> "); //formatting for command enter

            //format the input (remove uncessecary items from end, format to lowercase and store individual commands into "parts" // array)
            String input = scanner.nextLine().trim();
            String[] parts = input.split(" ");
            String command = parts[0].toLowerCase();

            //Run commands using a switch
            try {
                switch (command) {
                    case "create": //"create" command, make a process using process manager
                        if (parts.length > 1) {
                            pm.createProcess(parts[1]);
                        } else {
                            System.out.println("Usage: create [name]"); //print usage if no more arguments are entered 
                        }
                        break;

                    case "ps": // list all processes using process manager
                        pm.listProcesses();
                        break;

                    case "schedule": // run scheduled process using process manager
                        pm.schedule();
                        break;

                    case "alloc": //"alloc" command, using memory manager
                        if (parts.length > 2) {
                            int pid = Integer.parseInt(parts[1]);
                            int size = Integer.parseInt(parts[2]);
                            
                            // Use semaphore for mutual exclusion
                            memSemaphore.waitSem(); 
                            mm.allocate(pid, size);
                            memSemaphore.signal();
                        } else {
                            System.out.println("Usage: alloc [pid] [size]"); //print usage if not enough args entered (need 2 args for command)
                        }
                        break;

                    case "free": // "free" command using memory manager
                        if (parts.length > 1) {
                            //convert the string to an integer
                            int pid = Integer.parseInt(parts[1]);
                            
                            //use semaphore for mutual exclusion
                            memSemaphore.waitSem();
                            mm.free(pid);
                            memSemaphore.signal();
                        } else {
                            System.out.println("Usage: free [pid]"); //print usage if no more args entered
                        }
                        break;

                    case "mem": // display memory layout using memory manager
                        mm.printMemory();
                        break;

                    case "exit": // terminate shell (set running to false)
                        running = false;
                        System.out.println("Exiting MiniOS...");
                        break;

                    default:
                        System.out.println("Unknown command: " + command);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                // Ensure semaphore is released if an error occurs during alloc/free, [just in case]
            }
        }
        //close the scanner after shell exit
        scanner.close(); 
    }
}