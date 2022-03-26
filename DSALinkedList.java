import java.util.*;
import java.io.*;

// FILE: DSALinkedList
// AUTHOR: Elijah Combes
// PURPOSE: A class that can create a linked list and contain methods that are used
//          to perform operations on that linked list
// REQUIRES: makes use of the DSAListNode class          
// REFERENCES: Previoulsly submitted for practical 3.
// Last Mod: 15/10/2019
public class DSALinkedList implements Iterable, Serializable
{
    private DSAListNode head;
    private DSAListNode tail;
    
    // NAME: DSALinkedList
    // PURPOSE: default constructor for the linked list
    // IMPORTS: none
    // EXPORTS: a DSALinkedList object
    public DSALinkedList( )
    {
        head = null;
        tail = null;
    }

    // NAME: insertFirst
    // PURPOSE: to insert a node at the start of the list
    // IMPORTS: value, an Object to store
    // EXPORT: none
    public void insertFirst( Object value )
    {
        DSAListNode newNode = new DSAListNode( value );
        if( isEmpty( ) ) 
        {
            head = newNode;
            tail = head;
        }
        else
        {
            newNode.setNext( head );
            head.setPrev( newNode );
            head = newNode;
        }    
    }

    // NAME: insertLast
    // PURPOSE: to insert a node at the end of the linked list
    // IMPORTS: value, an object to store
    // EXPORTS: none
    public void insertLast( Object value )
    {
        DSAListNode newNode = new DSAListNode( value );
        if( isEmpty( ) )
        {
            head = newNode;
            tail = head;
        }
        else
        {
            tail.setNext( newNode );
            newNode.setPrev( tail );
            tail = tail.getNext( ); 
        }
    }
            
    // GETTERS
    public DSAListNode getFirst( )
    {
        return head;
    }

    public boolean isEmpty( )
    {
        return ( head == null );
    }

    // NAME: peekFirst
    // PURPOSE: to show what value is stored at the front of the list
    // IMPORTS: none
    // EXPORTS nodeValue, the object stored by the front list node
    public Object peekFirst( )
    {
        Object nodeValue = null;
        if( isEmpty( ) )
        {
            throw new IllegalStateException( "List is empty" );
        }
        else
        {
            nodeValue = head.getValue( );
        }
        return nodeValue;   
    }

    // NAME: peekLast
    // PURPOSE: to show what value is stored at the end of the list
    // IMPORTS: none
    // EXPORTS: nodeValue, the object stored by the tail list node
    public Object peekLast( )
    {
        Object nodeValue = null;

        if( isEmpty( ) )
        {
            throw new IllegalStateException( "List is empty" );
        } 
        else
        {
            nodeValue = tail.getValue( );
        }
        return nodeValue;
    }        
             
    // NAME: removeFirst
    // PURPOSE: to remove the 1st node in the list
    // IMPORTS: none
    // EXPORTS: nodeValue, the value stored by the fist list node
    public Object removeFirst( )
    {
        Object nodeValue = null;
        if( isEmpty( ) )
        {
            throw new IllegalStateException( "List is empty" );
        }
        else
        {
            nodeValue = head.getValue( );
            head = head.getNext( );
        }
        return nodeValue;
    }

    // NAME: removeLast
    // PURPOSE: to remove the tail list node in the list
    // IMPORTS: none
    // EXPORTS: nodeValue, the data stored by the tail list node
    public Object removeLast( )
    {
        DSAListNode prevNode, curNode = null;
        Object nodeValue = null;
        if( isEmpty( ) )
        {
            throw new IllegalStateException( "List is empty" );
        }
        else if( head.getNext( ) == null )
        {
            nodeValue = head.getValue( );
            head = null;
        }
        else
        {
            nodeValue = tail.getValue( );
            tail = tail.getPrev( );
            tail.setNext( null );
        }
        return nodeValue;
    }

    // NAME: remove
    // PURPOSE: to remove the node in the list that matches the imported key
    // removes node from a graph**
    // IMPORTS label, the key, theList the linked list to remove the node from
    // EXPORTS: none
    public void remove( String label, DSALinkedList theList )
    {
        DSAListNode curNode, prevNode = null;
        DSAListNode c = null;
        DSAGraphNode g,t = null;
        boolean finished = false;
        boolean tester;
        if( isEmpty( ) )
        {
            throw new IllegalStateException( "List is Empty" );
        }
        else if( head.getNext( ) == null )
        {
            head = null;
        }
        else
        {
            curNode = head;
            while( curNode.getNext( ) != null || !finished )
            {
                g = ( DSAGraphNode )curNode.getValue( );                
                if( curNode.getPrev( ) != null )
                {
                    if( g.getLabel( ).equals( label ) );
                    {
                        prevNode = curNode.getPrev( );
                        prevNode.setNext( curNode.getNext( ) );
                        curNode.getNext( ).setPrev( prevNode );
                        System.out.println( label + " has been removed successfully. " );
                        finished = true;
                    }
                }
                else if( g.getLabel( ).equals( label ) ) //if first node is to be removed
                {
                    removeFirst( );
                    finished = true;
                }
                curNode = curNode.getNext( );
                if( curNode.getNext( ) == null )
                {
                    finished = true;
                }
            }
        }    
    }
                    
    // NAME: getCount
    // PURPOSE: accessor for the number of values in the list
    // IMPORTS: none
    // EXPORTS: count
    public int getCount( )
    {
        DSAListNode curNode = head;
        int count = 1;
        if( isEmpty( ) )
        {
            count = 0;
        }
        else
        {
            while( curNode.getNext( ) != null )
            {
                count++;
                curNode = curNode.getNext( );
            }
        }
        return count;
    }
        
    // NAME: iterator
    // PURPOSE: creates an iterator for users to iterate through the list with
    // IMPORTS: none
    // EXPORTS: Iterator
    public Iterator iterator( )
    {
        return new DSALinkedListIterator( this );
    }

    //iterates over a list of links and prints each graphNodes label
    public void iterateOverLinks( DSALinkedList theList )
    {
        DSAGraphNode c;
        for( Object o: theList )
        {
            c = ( DSAGraphNode )o;
            System.out.println( c.getLabel( ) );
   
        }
    }

    //displays imported list
    public void iterateOverList( DSALinkedList theList )
    {
        DSAListNode c;
        DSAGraphNode g;
        for( Object o: theList )
        {
            c = ( DSAListNode )o;
            g = ( DSAGraphNode )c.getValue( );
            //g = (DSAGraphNode)c.getValue();
            System.out.println( ": \nNode label: "+g.getLabel( ) );//+", NODE value: "+g.getValue());    
        }
    }

    public DSAGraphNode iterateFind( String label, DSALinkedList theList )
    {
        DSAListNode c;
        DSAGraphNode g;
        DSAGraphNode w = null;    
        if( theList.isEmpty( ) ) //if vertices list is empty, create new node and add to list
        {
            g = new DSAGraphNode( label, " " );
            theList.insertFirst( g );
            w = g;
        }
        else
        {
            for( Object o: theList )
            {
                g = ( DSAGraphNode )o;
                if( g.getLabel( ).equals( label ) )
                {
                    w = g;
                }
            }
            if( w == null )
            {
                System.out.println("Unable to find vertex");
            }
        }
        return w;
    }
    
    //given a list of vertices and a String label, finds the vertex whose
    //label matches the imported string and returns that vertex
    public DSAGraphNode iterFind( String label, DSALinkedList theList )
    {
        DSAListNode c;
        DSAGraphNode g;
        DSAGraphNode w = null;

        for( Object o: theList )
        {
            c = ( DSAListNode )o;
            g = ( DSAGraphNode )c.getValue( );
            if( g.getLabel( ).equals( label ) )
            {
                w = g;
            }
        }
        return w;             
    }
                   
    // NAME: DSALinkedListIterator
    // PURPOSE: class to contain iterator functions for iterating through a 
    //          DSALinkedList
    // REQUIRES: DSAListNode class and Iterator interface          
    private class DSALinkedListIterator implements Iterator
    {
        private DSAListNode iterNext;
        
        // NAME: DSALinkedListIterator
        // PURPOSE: constructor for DSALinkedListIterator
        // IMPORTS: theList, a DSALinkedList
        // EXPORTS: DSALinkedListIterator
        public DSALinkedListIterator( DSALinkedList theList )
        {
            iterNext = theList.head;
        }

        //Iterator interface implementation
        // NAME: hasNext
        // PURPOSE: determine if the list has more items
        // IMPORTS: none
        // EXPORTS: boolean
        public boolean hasNext( )
        {
            return ( iterNext != null );
        }

        // NAME: next
        // PURPOSE: get the next value in the list
        // IMPORTS: none
        // EXPORTS: value, the object store by the next list node
        public Object next( )
        {
            Object value;
            if( iterNext == null )
            {
                value = null;
            }
            else
            {
                value = iterNext.getValue( );
                iterNext = iterNext.getNext( );
            }
            return value;
        }
        
        //optional method
        // NAME: remove
        // PURPOSE: remove a node and its value from the list
        // IMPORTS: none
        // EXPORTS: none
        public void remove( )
        {
            DSAListNode prevNode, nextNode = null;
            //head node
            if( iterNext.getPrev( ) == null )
            {
                head = iterNext.getNext( );
            }
            else
            {
                prevNode = iterNext.getPrev( );
                nextNode = iterNext.getNext( );
                prevNode.setNext( nextNode );
                nextNode.setPrev( prevNode );
            }
        }
    }
}
