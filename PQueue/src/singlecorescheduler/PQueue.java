package singlecorescheduler;
import java.util.*;

/**
 * This class describes a priority min-queue that uses an array-list-based min binary heap 
 * that implements the PQueueAPI interface. The array list holds objects that implement 
 * the parameterized Comparable interface.
 * @author Duncan, Heidi Faustermann
 * <pre>
 * Date: 9/29/2026
 * course: csc 3102
 * Programming Project: 1
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
 * <pre>
 */
 
 
 /**
  * A min-priority queue implementation that uses an 
  * array list as its data container
  * @param <E> the priority queue element type.   
  */
public class PQueue<E extends Comparable<E>> implements PQueueAPI<E>
{    
   /**
    * A complete tree stored in an array list representing the 
    * binary heap
    */
   private ArrayList<E> tree;
   /**
    * A comparator lambda function that compares two elements of this
    * heap when rebuilding it; cmp.compare(x,y) gives 1. negative when x less than y
    * 2. positive when x greater than y 3. 0 when x equal y
    */   
   private Comparator<? super E> cmp;

   /**
    * Constructs an empty PQueue using the compareTo method of its data type as the 
	* comparator
    */
   public PQueue()
   {
	   //Creates empty ArrayList to store the heap
	   tree = new ArrayList<E>();
	   //Uses compareTo to determine the order of the elements
	   cmp = (x,y) -> x.compareTo(y);
   }
   
   /**
    * copy constructor
    */
   public PQueue(PQueue pQ)
   {
	   //Creates a new ArrayList containing the elements from the priority queue
	   tree = new ArrayList<E>(pQ.tree);
	   //Copies the comparator from original priority queue
	   cmp = pQ.cmp;
   }
   
   /**
    * A parameterized constructor that uses an externally defined comparator    
    * @param fn - a trichotomous integer value comparator function   
    */
   public PQueue(Comparator<? super E> fn)
   {
	   //Creates empty ArrayList to store heap
	   tree = new ArrayList<E>();
	   //Stores comparator passed into the constructor
	   cmp = fn;
	   
   }

   public boolean isEmpty()
   {
      return tree.isEmpty();
   }

   public void add(E obj)
   {
	   //Add new element to end of heap
	   tree.add(obj);
	   //Move new element up until heap is restored
	   heapifyUp(tree.size()-1);
   }

   public E remove() throws PQueueException
   {
	   if (tree.isEmpty()) 
	   {
		   //Throws exception because there is nothing to remove
		   throw new PQueueException();
	   }  
	   //Saves root element (highest priority)
	   E result = tree.get(0);
	   //Remove last element from heap
	   E last = tree.remove(tree.size() - 1);
	   
	   //If elements are still left in the heap, move last element to the root
	   if(!tree.isEmpty()) 
	   {
		   tree.set(0, last);
		   //Move new root down until heap is restored
		   heapifyDown(0);
	   }
	   
	   return result;
	   
      //implement this method
	 
   }
 
   public E peek() throws PQueueException
   {
	   if (tree.isEmpty()) 
	   {
		   //Throws exception because there is no element
		   throw new PQueueException();
	   }
	   //Returns the root without removing it
	   return tree.get(0);
   }

   public int size()
   {
	  //Returns number of elements stored in the ArrayList
      return tree.size();
   }
   
   /**
    * Swaps a parent and child elements of this heap at the specified indices
    * @param place an index of the child element on this heap
    * @param parent an index of the parent element on this heap
    */
   private void swap(int place, int parent)
   {
	   //Temporarily store the child element
	   E temp = tree.get(place);
	   //Move parent into child's position
	   tree.set(place, tree.get(parent));\
	   //Move child to parent's position
	   tree.set(parent, temp);
   }

    /**
     * Rebuild this priority queue from the 
     * specified index using a trickle up procedure
     * @param index the index at which to begin the rebuild
     */
    private void heapifyUp(int index)
    {
    	while (index > 0) 
    	{
    		//Calculates index of parent
    		int parent = (index - 1) / 2;
    		
    		//Checks if element has higher priority than parent
    		if (cmp.compare(tree.get(index), tree.get(parent)) < 0) 
    		{
    			//If current element has higher priority, swap with parent
    			swap(index, parent);
    			index = parent;
    		} else {
    			break;
    		}
    	}
    }
        //implement this method
    /**
     * Rebuild this priority queue from the 
     * specified index using a trickle down procedure
     * @param index the index at which to begin the rebuild
     */   
    public void heapifyDown(int index)
    {
    	while (true) 
    	{
    		int left = 2 * index + 1;
    		int right = 2 * index + 2;
    		
    		if(left >= tree.size()) 
    		{
    			break;
    		}
    		
    		int smallerChild = left;
    		//Placeholder that assumes the left is smaller
    		
    		if (right < tree.size() && cmp.compare(tree.get(right), tree.get(left)) <0) 
    		{
    			smallerChild = right;
    			//Switches to the right if it is actually smaller
    		}
    		
    		//checks if smallerChild is smaller than parent
    		if (cmp.compare(tree.get(smallerChild), tree.get(index)) < 0) 
    		{
    			//If smallerChild is smaller, they swap
    			swap(index, smallerChild);
    			index = smallerChild;
    		} else {
    			break;
    		}
    	}
      
    }
}