import java.util.*;
import java.io.*;

// FILE: DSAQueue.java
// AUTHOR: Elijah Combes
// PURPOSE: provides a class for creating a queue and defining its methods
// REFERENCES: previously submitted for practical 2
// Last Mod: 15/10/2019
public abstract class DSAQueue implements Serializable
{
    protected final int DEFAULT_CAPACITY = 100;
    
    protected Object[] queue;
    protected int count;
    
    // NAME: DSAQueue
    // PURPOSE: default constructor for queue
    // IMPORTS: none
    // EXPORTS: DSAQueue object
    public DSAQueue()
    {
        queue = new Object[DEFAULT_CAPACITY];
        count = 0;
    }
        
    // NAME: DSAQueue
    // PURPOSE: alternate constructor for creating a DSAQueue
    // IMPORTS: maxCapacity, defines queue total size
    // EXPORTS: DSAQueue object
    public DSAQueue(int maxCapacity)
    {
        queue = new Object[maxCapacity];
        count = 0;
    }

    // NAME: getCount
    // PURPOSE: returns the number of items in the queue
    // IMPORTS: none
    // EXPORTS: count, an integer
    public int getCount()
    {
        return count;
    }

    // NAME: isEmpty
    // PURPOSE: to tell user whether the queue is empty or not
    // IMPORTS: none
    // EXPORTS: boolean, true if empty, false if not
    public boolean isEmpty()
    {
        return (count == 0);
    }
    
    // NAME: isFull
    // PURPOSE: to tell the user whether the queue is full or not
    // IMPORTS: none
    // EXPORTS: boolean 
    public boolean isFull()
    {
        return (count == queue.length);
    }

//MUTATORS
          
    public abstract void enqueue(Object value);
    
    public abstract Object dequeue();
    
    public abstract Object peek();

    // NAME: display
    // PURPOSE: to display the queues values and the index they are stored at
    // IMPORTS: none
    // EXPORTS: none
    public void display()
    {
        for(int i = 0;i<getCount();i++)
        {
            System.out.println("index: "+i+", "+queue[i].toString());
        }
    }
}
