import java.util.*;
import java.io.*;

// FILE: DSAListNode.java
// AUTHOR: Elijah Combes
// PURPOSE: provides a class for node objects to be used by DSALinkedList
// REFERENCES: previously submitted for practical 3.
// Last Mod: 15/10/2019
public class DSAListNode implements Serializable
{
    private Object value;
    private DSAListNode next;
    private DSAListNode prev;

    // NAME: DSAListNode
    // PURPOSE: constructor for DSAListNode objects
    // IMPORTS: inValue, an object for the list node to store
    // EXPORTS: DSAListNode object
    public DSAListNode(Object inValue)
    {
        value = inValue;
        next = null;
        prev = null;
    }

    // NAME: getValue
    // PURPOSE: accessor for the value stored by the list node
    // IMPORTS: none
    // EXPORTS: value, Object
    public Object getValue()
    {
        return value;
    }

    // NAME: setValue
    // PURPOSE: mutator for the value stored by the list node
    // IMPORTS: inValue new new value for the node to store
    // EXPORTS: none
    public void setValue(Object inValue)
    {
        value = inValue;
    }
    
    // NAME: getNext
    // PURPOSE: accessor for the next node in the list
    // IMPORTS: none
    // EXPORTS: next, DSAListNode
    public DSAListNode getNext()
    {
        return next;
    }

    // NAME: getPrev
    // PURPOSE: accessor for previous node in the list
    // IMPORTS: none
    // EXPORTS: prev, the previous list node
    public DSAListNode getPrev()
    {
        return prev;
    }
    
    // NAME: setNext
    // PURPOSE: Mutator for the adjacent next node in the list
    // IMPORTS: newNext, new next list node 
    // EXPORTS: none
    public void setNext(DSAListNode newNext) 
    {
        next = newNext;
    }

    // NAME: setPrev
    // PURPOSE: mutator for changing the previous node in the list
    // IMPORTS: newPrev, DSAListNode
    // EXPORTS: none
    public void setPrev(DSAListNode newPrev)
    {
        prev = newPrev;
    }

    //for test purposes, only works when value is a String
    public void display()
    {
        String tester = (String)value;
        System.out.println("ACTUAL: "+value+"\n");
    }
}
