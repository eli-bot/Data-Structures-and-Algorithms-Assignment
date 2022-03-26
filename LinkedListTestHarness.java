import java.util.*;
import java.io.*;

// FILE: UnitTestLinkedList.java
// AUTHOR: Elijah Combes
// PURPOSE: linked list data structure
//
public class LinkedListTestHarness
{
    public static void main(String[] args)
    {
        DSALinkedList list = new DSALinkedList();
        DSAListNode node1 = new DSAListNode("node 1");
        DSAListNode node2 = new DSAListNode("node 2");
        DSAListNode node3 = new DSAListNode("node 3");
        DSAListNode node4 = new DSAListNode("node 4");
        
        list.insertFirst(node1);
        list.insertLast(node2);
        list.insertLast(node3);
        list.insertLast(node4);
        list.iterateOverList(list);
        save(list,"savedList");       

        Scanner sc = new Scanner(System.in);
        int choice = 0;
        while(choice != 5)
        {
            try
            {
                System.out.println("Please select an option: \n1. Test Linked List\n2. Read serialized file\n3. Display list");
                System.out.println("4. Write Serialized file\n5. Exit");
                choice = sc.nextInt();
            switch(choice)
            {   
                case 1:
                    testLinkedList();
                    break;
                case 2:
                    list = load("savedList");
                    list.iterateOverList(list);
                    break;
                case 3: 
                    list.iterateOverList(list);
                    break;
                case 4:
                    save(list,"savedList");
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            }
            catch(InputMismatchException a)
            {
                System.out.println("invalid choice");
                choice = 5;
            }
        }
    }
        
    public static void testLinkedList()
    {        
        DSALinkedList myList = new DSALinkedList();
        
        DSAListNode node1 = new DSAListNode("Node #1");
        DSAListNode node2 = new DSAListNode("Node #2");
        DSAListNode node3 = new DSAListNode("Node #3");

        //create list where node1 = head
        myList.insertFirst(node1);
        myList.insertLast(node2);
        myList.insertLast(node3);

        myList.iterateOverList(myList);

        DSAListNode testNode = (DSAListNode)myList.peekFirst();
        System.out.println("peekFirst() test\n EXPECTED: NODE #1 ");
        testNode.display();   
     
        System.out.println("peekLast() test\n EXPECTED: node #3");  
        testNode = (DSAListNode)myList.peekLast();
        testNode.display();

        System.out.println("removeFirst() TEST: \n EXPECTED: 2, 3");
        myList.removeFirst();
        myList.iterateOverList(myList);        
        
        System.out.println("removeLast()  TEST: \n EXPECTED: 2");
        myList.removeLast();
        myList.iterateOverList(myList);    
   }     

    public static void save(DSALinkedList objToSave, String fileName)
    {
        FileOutputStream fileStrm;
        ObjectOutputStream objStrm;
        
        try
        {
            fileStrm = new FileOutputStream(fileName);
            objStrm = new ObjectOutputStream(fileStrm);
            objStrm.writeObject(objToSave);

            objStrm.close();
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("Unable to save Object to file");
        }
    }
        
    public static DSALinkedList load(String fileName)
    {
        FileInputStream fileStrm;
        ObjectInputStream objStrm;
        DSALinkedList inObj = null;
    
        try
        {
            fileStrm = new FileInputStream(fileName);
            objStrm = new ObjectInputStream(fileStrm);
            inObj = (DSALinkedList)objStrm.readObject();
            objStrm.close();
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("Class DSALinkedList not found");// + 
                              // e.getmessage);
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("Unable to load object from file");
        }
        return inObj;
    }    
}
