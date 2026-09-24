package singlecorescheduler;

/**
 * A application to simulate a non-preemptive scheduler for a single-core CPU
 * using a heap-based implementation of a priority queue
 * @author William Duncan, YOUR NAME
 * @see PQueue.java, PCB.java
 * <pre>
 * DATE: LAST DATE MODIFIED
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

import sun.nio.cs.Surrogate.Generator;

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
        
        
		//Complete the implementation of this method
    }
    
}










