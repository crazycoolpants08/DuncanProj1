package singlecorescheduler;

/**
 * A application to simulate a non-preemptive scheduler for a single-core CPU
 * using a heap-based implementation of a priority queue
 * @author William Duncan, Heidi Faustermann
 * @see PQueue.java, PCB.java
 * <pre>
 * DATE: 9/29/2026
 * File:SingleCoreScheduler.java
 * Course: csc 3102
 * Programming Project # 1
 * Instructor: Dr. Duncan
 * Usage: SingleCoreScheduler <number of cylces> <-R or -r> <probability of a  process being created per cycle>  or,
 *        SingleCoreScheduler <number of cylces> <-F or -f> <file name of file containing processes>,
 *        The simulator runs in either random (-R or -r) or file (-F or -f) mode 
 * </pre>
 */


import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
//import sun.nio.cs.Surrogate.Generator;

public class SingleCoreScheduler
{
    /**
     * Single-core processor with non-preemptive scheduling simulator    
     * @param args an array of strings containing command line arguments
     * args[0] - number of cyles to run the simulation
     * args[1] - the mode: -r or -R for random mode and -f or -F for file mode
     * args[2] - if the mode is random, this entry contains the probability that
     * a process is created per cycle and if the simulator is running in
     *           file mode, this entry contains the name of the file containing the 
     *           the simulated jobs. In file mode, each line of the input file is  
     *           in this format: 
     * <process ID> <priority value> <cycle of process creation> <time required to execute> 
     */
    public static void main(String []args) throws PQueueException, IOException
    {
        if (args.length != 3)
        {
            System.out.println("Usage: SingleCoreScheduler <number of cylces> <-R or -r> <probability of a  process being created per cycle>  or ");
            System.out.println("       SingleCoreScheduler <number of cylces> <-F or -f> <file name of file containing processes>");
            System.out.println("The simulator runs in either random (-R or -r) or file (-F or -f) mode.");
            System.exit(1);
        }
        
        Random generator = new Random(System.currentTimeMillis());
        
        //Gets number of CPU cycles
        int cycles = Integer.parseInt(args[0]);
        //Amount of time the current process has remaining
        int remaining = 0;
        //Stores process currently running
        PCB currentProcess = null;
        
        //Amount of processes created
        int created = 0;
        //Amount of processes completed
        int completed = 0;
        //Total turnaround time
        int totalTurnaround = 0;
        //Total wait time of all completed processes
        long totalWait = 0;

        //Determines which PCB has priority, smaller number means higher priority
        Comparator<PCB> pcbComparator = (a, b) -> 
        {
        	if (a.getPriority() != b.getPriority()) 
        	{
        		return Integer.compare(a.getPriority(), b.getPriority());
        	}
        	
        	//If they have equal priorities, the one created first goes first
        	return Integer.compare(a.getArrival(), b.getArrival());
        };
        
        PQueue<PCB> readyQueue = new PQueue<>(pcbComparator);
        
        //random mode
        if (args[1].equals("-r") || args[1].equals("-R"))
        {
        	//Gets the probability that a new process is created each cycle
            double probability = Double.parseDouble(args[2]);
            int nextPid = 1;
            
            //Runs simulations for the given number of cycles
            for (int i = 1; i <= cycles; i++)
            {
            	System.out.println("*** Cycle #: " + i);
            	
            	//Checks if no process is waiting & no running process
            	if (readyQueue.isEmpty() && currentProcess == null) 
            	{
            		//If true, the idle statement is printed.
            		System.out.println("The CPU is idle.");
            	} 
            	else 
            	{
            		/*If there is no current process and another process on the priority list
            		 * then the process next in priority is removed from the queue.
            		 * */
            		if (currentProcess == null) 
            		{
            			currentProcess = readyQueue.remove();
            			currentProcess.execute();
            			
            			//Record when process begins executing
            			currentProcess.setStart(i);
            			//Calculates wait time
            			currentProcess.setWait(i - currentProcess.getArrival());
            			//Set process's remaining time
            			remaining = currentProcess.getBurst();
            		}
            		//If there is still time remaining, it continues executing
                	if (remaining > 0) 
                	{
                		System.out.println("Process #" + currentProcess.getPid() + " is executing.");
                		remaining--;
                	} 
                	else 
                	{
                		//Process finished executing, finished burst time
                		System.out.println("Process #" + currentProcess.getPid() + " has just terminated.");
                		
                		//Add process wait time to total
                		totalWait += currentProcess.getWait();
                		//Turnaround time = burst time + waiting time
                		totalTurnaround += currentProcess.getBurst() + currentProcess.getWait();
                		completed++;
                		
                		//Makes CPU available for another process
                		currentProcess = null;
                		//Reset remaining time
                		remaining = 0;
                	}
            	}
            	
            	//Generates a random number between 0 and 1
                double q = generator.nextDouble();
                
                //Creates process if random number is less than or equal to the probability
                if (q <= probability)
                {
                	//Generates priority from -20 to 19
                    int priority = generator.nextInt(40) - 20;
                    //Generates burst time from 1 to 100
                    int burst = generator.nextInt(100) + 1;
                    
                    PCB process = new PCB(nextPid, priority, 0, i, burst);

                    readyQueue.add(process);
                    created++;
                    nextPid++;

                    System.out.println("Adding job with pid #" + process.getPid() + " and priority " + process.getPriority() + " and burst " + process.getBurst() + ".");
                }
                else
                {
                    System.out.println("No new job this cycle.");
                }
            }
        }
        //File mode
        else if (args[1].equals("-f") || args[1].equals("-F"))
        {
            //Opens the file
            Scanner input = new Scanner(new FileReader(args[2]));
            
            //Stores processes read from the input file
            ArrayList<PCB> processes = new ArrayList<>();
            
            //Reads each process from file
            while (input.hasNext()) 
            {
            	int pid = input.nextInt();
            	int priority = input.nextInt();
            	int arrival = input.nextInt();
            	int burst = input.nextInt();
            	
            	//Creates new PCB using values from the file
            	PCB process = new PCB(pid, priority, 0, arrival, burst);
            	
            	processes.add(process);
            }
            
            input.close();
            
            //Runs simulation for the given number of cycles
            for (int i = 1; i <= cycles; i++) {
            	
            	System.out.println("*** Cycle #: " + i);
            	
            	//If the queue is empty and no process is running, the CPU is idle
            	if (readyQueue.isEmpty() && currentProcess == null ) {
            		System.out.println("The CPU is idle.");
            	} 
            	else 
            	{
            		//If CPU is available, highest priority process is selected
            		if (currentProcess == null) 
            		{
            			currentProcess = readyQueue.remove();
            			currentProcess.execute();
            			
            			//Records when process begins execution
            			currentProcess.setStart(i);
            			//Calculates wait time
            			currentProcess.setWait(i - currentProcess.getArrival());
            			//Sets remaining time
            			remaining = currentProcess.getBurst();
            		}
            		
            		//Continues executing current process if there is time remaining
                	if (remaining > 0) {
                		System.out.println("Process #" + currentProcess.getPid() + " is executing.");

                		remaining--;
                	} 
                	else 
                	{
                		//Process is completed
                		System.out.println("Process #" + currentProcess.getPid() + " has just terminated.");
                		totalWait += currentProcess.getWait();
                		totalTurnaround += currentProcess.getBurst() + currentProcess.getWait();
                		completed++;
                		
                		currentProcess = null;
                		remaining = 0;
                	}
            	}
            	//Tracks if a process was created this cycle
            	boolean newProcess = false;
            	
            	//Checks every process from the input file
            	for (PCB process : processes) 
            	{
            		//If process arrival cycle matches current cycle, add it to the ready queue
            		if (process.getArrival() == i) 
            		{
            			readyQueue.add(process);
            			created++;
            			
            			System.out.println("Adding job with pid #" + process.getPid() + " and priority " + process.getPriority() 
            			+ " and burst " + process.getBurst() + ".");
            			
            			//New process was created this cycle
            			newProcess = true;
            		}
            	}
            	//Prints message if no new process was created this cycle
            	if (!newProcess) 
            	{
            		System.out.println("No new job this cycle.");
            	}
            }
            
        }
        else
        {
            // invalid
            System.exit(1);
        }
        
        //Prints the final statistics
        System.out.println();
        System.out.println("The number of processes created: " + created);
        System.out.println("The average number of processes created per cycle is " + ((double) created / cycles) + ". ");
        if (completed > 0) 
        {
        	System.out.println("The average turnaround time per process is " + ((double) totalTurnaround / completed) + " cycles.");
        	System.out.println("The average wait time per process is " + ((double) totalWait / completed) + ".");
        }
        else 
        {
        	System.out.println("No processes completed.");
        }
    }
    
}

