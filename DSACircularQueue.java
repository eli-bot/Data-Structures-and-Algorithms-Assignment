// FILE: DSACircularQueue.java
// AUTHOR: Elijah Combes
// PURPOSE: Provides a class for implementing a circular queue
// COMMENTS: Standard circular queue algorithm
// REFERENCES: previously submitted for practical 2
// REQUIRES: Super class DSAQueue
// Last Mod: 15/10/2019

import java.util.*;

public class DSACircularQueue extends DSAQueue
{
    private int head;
    private int tail;
   
    // NAME: DSACircularQueue
    // PURPOSE: Default constructor for a DSACircularQueue
    // IMPORTS: none
    // EXPORTS: DSACircularQueue object
    // ASSERTIONS: 
    public DSACircularQueue( )
    {
        super( );
        head = -1;
        tail = -1;
    }
        
    // NAME: DSACircularQueue
    // PURPOSE: Alternate constructor for a DSACircular, allowing user
    //          to create a queue of the size they desire
    // IMPORTS: maxCapacity and integer determining the size of the queue
    // EXPORTS: DSACircularQueue object
    // ASSERTIONS: maxCapacity is a valid integer         
    public DSACircularQueue( int maxCapacity )
    {
        super( maxCapacity );
        head = -1;
        tail = -1;
    }
          
    // NAME: enqueue
    // PURPOSE: add new object to the queue
    // IMPORTS: inObj an object to be added to the queue
    // EXPORTS: none
    public void enqueue( Object inObj )
    {
        if( isFull( ) )
        {
            throw new IllegalStateException( "queue is full" );
        }
        else
        {
            tail = ( tail + 1 ) % queue.length;
            queue[tail] = inObj;
            count++;
        }
        if( head == -1 )
        {
            head++;
        }
    } 

    // NAME: dequeue
    // PURPOSE: removes front object from the queue
    // IMPORTS: none
    // EXPORTS: Object, the object that is removed
    // ASSERTIONS: 
    public Object dequeue( )
    {
        Object frontVal = null;
        if( isEmpty( ) )
        {
            throw new IllegalStateException( "queue is empty" );
        }
        else
        {
            frontVal = queue[head];
            queue[head] = null;
            head = ( head + 1 ) % queue.length;        
            count--;
        }
        return frontVal;
    }

    // NAME: peek
    // PURPOSE: show the front item in the queue
    // IMPORTS: none
    // EXPORTS: Object, front item in the queue
    // ASSERTIONS: 
    public Object peek( )
    {
        return queue[head];
    }

    // NAME: display
    // PURPOSE: display all items in the queue to the user and what index 
    //          they are at
    // IMPORTS: none
    // EXPORTS: none         
    public void display( )
    {
        for( int i = head;i < count; i++ )
        {
            System.out.println( "index: "+i+", "+( ( DSAGraphNode )queue[i] ).display( ) );
        }   
    }
}
    
