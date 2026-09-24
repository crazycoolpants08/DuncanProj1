/**
 * Specifies operations on a priority queue
 * @author Duncan <br>
 * <pre>
 * File: PQueue.java<br>
 * Course: csc 3102
 * Project: # 1
 * Instructor: Dr. Duncan
 *
 * DO NOT REMOVE THIS NOTICE (GNU GPL V2):
 * Contact Information: duncanw@lsu.edu
 * Copyright (c) 2026 William E. Duncan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/> 
 * </pre>
 */
package singlecorescheduler;

/** 
 * Describes the basic operations of a priority queue
 * @param <E>
 */
public interface PQueueAPI<E extends Comparable<E>>
{
   /**
    * Determines whether the priority queue is empty.
    * @return this method returns true if the priority is empty;
    * otherwise, it returns false if the priority queue contains at least one item.
    */
   boolean isEmpty();

   /**
    * Inserts an item into the priority queue.
    * @param item the value to be inserted.
    */
   void add(E item);

   /**
    * An exception is generated if this method is invoked
    * by an empty priority. The maximum/minimum value is removed
    * from the priority queue if the priority queue is not empty and its effective
    * size is reduced by 1.
    * @return the value with the highest priority.
     * @throws PQueueException when the priority queue is empty
    */
   E remove() throws PQueueException;

   /**
    * An exception is generated if this method is invoked
    * by an empty priority queue
    * @return the value with the highest priority
    * @throws PQueueException when the priority queue is empty
    */
   E peek() throws PQueueException;


   /**
    * Gives the size of this priority queue
    * @return the size of the priority queue; the effective size of the
    * priority queue.
    */
   int size();
}
