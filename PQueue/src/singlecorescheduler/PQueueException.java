//package singlecorescheduler;
/**
 * Reports priority queue exception
 * @author Duncan <br>
 * <pre>
 * File: PQueueException.java<br>
 * Course: csc 3102.001
 * Project: # 1
 * Instructor: Dr. Duncan
 * </pre>
 */
package singlecorescheduler;

/**
 * This class reports PQueue exceptions.
 */
public class PQueueException extends Exception implements PQueueExceptionAPI
{
    /**
     * Creates a new instance of <code>PQueueException</code> without detail
     * message.
     */
    public PQueueException() { }

    /**
     * Constructs an instance of <code>PQueueException</code> with the specified
     * detail message.
     * @param msg the detail message.
     */
    public PQueueException(String msg) 
    {
        super(msg);
    }
    
    public String toString()
    {
        return toString();
    }
}