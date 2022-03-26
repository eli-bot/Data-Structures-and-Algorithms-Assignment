import java.util.*;

public class UnitTestDSAGraph
{
    private static DSAGraph graph;

    public static void main( String args[] )
    { 
        graph = new DSAGraph( );            
        testGraph( ); 
        //testNode( );
    }

    public static void testNode( )
    {
        graph.displayVertices();
        DSAGraphNode node = graph.getVertex( "3" );
        System.out.println( "\nExpected: 3, Actual: " + node.getLabel( ) );
        node = graph.getVertex( "1" );
        System.out.println( "Expected: 1, Actual: " + node.getLabel( ) );
       
        System.out.println( "testing removing vertex with label 2 " ); 
        graph.removeVertex( "2" );    
        DSALinkedList vertices = graph.getVertices( );
        vertices.iterateOverLinks( vertices );
        graph.displayVertices( );
    }


    //tests adding methods in DSAGraph
    
    public static void testGraph( )
    {
        DSAGraphNode node1, node2;
        boolean tester = false;
        DSALinkedList vertices = graph.getVertices( );
        //insert 4 nodes, with people as their values
        Person p1 = new Person( "11" );
        Person p2 = new Person( "22" );
        Person p3 = new Person( "33" );
        Person p4 = new Person( "44" );
        
        graph.addVertex( "1", p1 );
        graph.addVertex( "2", p2 );
        graph.addVertex( "3", p3 );
        graph.addVertex( "4", p4 );
        try
        {
            System.out.println( "\nTesting addVertex " );
            node1 = graph.getVertex( "1" );
            if( node1.getValue( ) != p1  )
            {   
                throw new IllegalArgumentException( "FAILED: 1" );
            }
            node2 = graph.getVertex( "2" );
            if( node2.getValue( ) != p2 )
            {
                throw new IllegalArgumentException( "FAILED: 2" );
            }
            if(node2.getLabel() != "2" )
            {
                throw new IllegalArgumentException("FAIL");
            }
            node2 = graph.getVertex( "3" );
            if( node2.getValue( ) != p3 )
            {
                throw new IllegalArgumentException( "FAILED: 3" );
            }
            if( node2.getLabel() != "3" )
            {
                throw new IllegalArgumentException( "FAILED: 4");
            }
            else
            {
                System.out.println("PASSED");
            }
        }
        catch( Exception e )
        {
            System.out.println( "FAILED: 3" );
        }
        
        //test addEdge
        graph.addEdge( "1", "2", true); //ie add edge from 1 to 2 directed
        graph.addEdge( "1", "4", false);
        graph.addEdge( "4", "2", true );
        System.out.println( "\nTesting addEdge " );
        DSAEdge edge = (DSAEdge)( graph.getEdges().peekFirst());
        System.out.println( "EXPECTED: 12, ACTUAL: "+ edge.getLabel( ) );
        graph.displayEdges( );    
        
        System.out.println( "\nTesting getEdge: " );
        System.out.println( "EXPECTED: 42 directed, ACTUAL: " +graph.getEdge( "42" ).toString( ) );
        System.out.println( "\nTesting search for invalid Edge: " ); 
        graph.getEdge( "234" );         
        graph.displayVertices();
      /*  try
        {*/
            //tests if the first node is following the second
            System.out.println( "\nTesting isAdjacent: " );
            if( graph.isAdjacent( "1", "2" ) == false )
                throw new IllegalArgumentException( "FAILED: 1" );
            if( graph.isAdjacent( "1", "4" ) == false )
                throw new IllegalArgumentException( "FAILED: 2" );
            if( graph.isAdjacent( "4", "2" ) == false )
                throw new IllegalArgumentException( "FAILED: 3" );
            if( graph.isAdjacent( "3", "4" ) == true )
                throw new IllegalArgumentException( "FAILED: 4" );
        /*}
        catch( Exception e ) { System.out.println( "FAILED: 5" ); }    
        System.out.println( "PASSED." );
        */
        System.out.println( "\nTesting getDegree: " );
        System.out.println( "EXPECTED: 2, ACTUAL: "+ graph.degree( 
                          (DSAGraphNode)vertices.peekFirst( ) ) );        
            
        node1 = graph.getVertex("2");
        node2 = graph.getVertex("3");
        boolean tester1 = (node1.getLabel().equals(node2.getLabel()));
        System.out.println(" BOOLEAN TEST: "+tester1);

        graph.displayVertices( );
        System.out.println( "\nRemoving vertex 3, Result: " );
        graph.removeVertex( "2" );
        graph.displayVertices(); 
        
    }

    // tesst graph with person objects
}

