import java.util.*;

//test harness for DSAShufflingQueue
// FILE: UnitTEstDSACircularQueue
// PURPOSE: test a circular queue
// previously submitted for practical 2
public class UnitTestDSACircularQueue
{
    public static void main(String[] args)
    {
        String[] array = {"12","13","14","15"};
        testCircular(array);
    }
    
    public static void testCircular(String[] array)
    {
        DSACircularQueue tester = new DSACircularQueue(4);
        System.out.println("CIRCULAR QUEUE TEST 1: enqueue 4 elements ");
 
        tester.enqueue(array[0]);
        tester.enqueue(array[1]);
        tester.enqueue(array[2]);
        tester.enqueue(array[3]);
        System.out.println("\nEXPECTED:\n12\n13\n14\n15\nACTUAL: ");
        
        for(int i = 0; i < tester.getCount();i++)
        {
            System.out.println((tester.queue[i]).toString());
        }
            
        System.out.println("peek()\nE: 12 , A: "+((String)tester.peek()));
        
        System.out.println("dequeue 1 element\nE: 12, A: "+((String)tester.dequeue()));
        System.out.println("peek()\nE: 13, A: "+((String)tester.peek()));
        System.out.println("count()\nE: 3, A: "+ tester.getCount());
        tester.enqueue(array[0]);
        System.out.println("dequeue after 1 more enqueue\nE: 13, A: "+((String)tester.dequeue()));
        System.out.println("\n");
    }
    
}
    /*public static void testStack(YeetObject[] array)
    {
        DSAStack tester = new DSAStack(10);
        tester.push('(');//array[0]);
        tester.push('+');//array[1]);
        tester.push('-');//array[2]);
        tester.push('*');//array[3]);
        System.out.println("\nSTACK TEST:\nCOUNT E: 4, A: "+tester.getCount());
           
        tester.display();
        System.out.println("E: *, A: "+(tester.top()));
        System.out.println("E: *, A: "+(tester.pop()));
        System.out.println("E: -, A: "+(tester.pop()));
            tester.pop();
            tester.pop();
        System.out.println("E: 0, A: "+ tester.getCount());
    } */
