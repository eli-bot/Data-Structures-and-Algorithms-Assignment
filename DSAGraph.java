import java.util.*;
import java.io.*;

// FILE: DSAGraph.java
// AUTHOR: Elijah Combes
// PURPOSE: Container class for storing a network and methods to to insert, search, delete
//          elements in this graph
// REFERENCES: previously submitted in practical 5 and modified since
// REQUIRES: DSAGraphNode, DSALinkedList and DSACircularQueue
// Last Mod: 28th October 2019         
public class DSAGraph implements Serializable
{
    private DSALinkedList vertices;
    private DSALinkedList edges;
    
    // NAME: DSAGraph
    // PURPOSE: constructor for graph object
    //
    public DSAGraph( )
    {
        vertices = new DSALinkedList( );
        
        edges = new DSALinkedList( );
    }

    // NAME: addVertex
    // PURPOSE: adds a new vertex to the graph
    // IMPORTS: label(String), value(Object)
    // EXPORTS: none
    public void addVertex( String label , Object value )
    {
        DSAGraphNode newVertex = new DSAGraphNode( label, value );
    
        vertices.insertLast( newVertex );
    } 

    // NAME: addEdge
    // PURPOSE: to add an edge between two vertices in the graph
    // IMPORTS: label1, label2 and directed
    // EXPORTS: NONE
    public void addEdge( String label1, String label2 , boolean directed )
    {
        DSAGraphNode node1 = vertices.iterateFind( label1, vertices );
        DSAGraphNode node2 = vertices.iterateFind( label2, vertices );
        DSAEdge newEdge;
        Person p;

        //adds new node with label if the node doesnt exist in the list
        if( node1 == null )
        {
            node1 = new DSAGraphNode( label1, " " );
            vertices.insertLast( node1 );
        }
        if( node2 == null )
        {
            node2 = new DSAGraphNode( label2, " " );
            vertices.insertLast( node2 );

        }   
     //   node1.addEdge( node2 );// add node2 to node1 links
        newEdge = getEdge( label2 + label1 );
        //if label2 already follows label1 
        if( newEdge != null )    
        {
            newEdge.setDirected( false );
            p = ( Person )node1.getValue( );
            p.addFollower( node2 );
            p = ( Person )node2.getValue( );
            p.follow( node1 );
            node2.addEdge( node1 );
            //ba
        }
        else 
        {
            newEdge = new DSAEdge( label1 + label2, node1, node2, directed );      
            edges.insertLast( newEdge );
            if( directed )
            {
                p = ( Person )node1.getValue( );
                p.follow( node2 );
                p = ( Person ) node2.getValue( );
                p.addFollower( node1 );
                node2.addEdge( node1 ); //add node1 to node2's followers
            }
            else if( !directed )
            {
                p = ( Person ) node1.getValue( );
                p.addMutual( node2 );
                p = ( Person )node2.getValue( );
                p.addMutual( node1 );    
                node1.addEdge( node2 ); //node1 and node2 follow each other, add to links so
                node2.addEdge( node1 ); //traversals work properly
            }
        }
    }

    // NAME: getDegree
    // PURPOSE: return the degree of a node
    // IMPORTS: vertex( DSAGraphNode)
    // EXPORTS: int degree
    public int getDegree( DSAGraphNode vertex )
    {
        int deg = 0;
        Iterator iter = edges.iterator( );
        DSAEdge curEdge = null;
        while( iter.hasNext( ) )
        {
            curEdge = ( DSAEdge )iter.next( );   
            if( curEdge.getTo( ) == vertex || ( curEdge.getFrom( ) == vertex
                && !curEdge.isDirected( ) ) )
            {
                deg++;
            }
        }
        return deg;
    }

    //  remove person object and from all of their followers lists
    // NAME: removeVertex
    // PURPOSE: remove a vertex from the graph
    // IMPORTS: label(String)
    // EXPORTS: NONE
    public void removeVertex( String label )
    {
        DSAEdge curEdge;
        DSAGraphNode curNode;
        Person p;
        vertices.remove( label, vertices );    
        displayVertices();
        //remove links/ edges
        //remove all edges that contain label
        Iterator iter = edges.iterator( );
        
        while( iter.hasNext( ) )
        {
            curEdge = ( DSAEdge )iter.next( );
            if( curEdge.getLabel( ).contains( label ) )
            {
                //remove edges connected to label
                curNode = curEdge.getFrom( );
                //remove node from everyones following/followers list
                if( curNode.getLabel( ).equals( label ) )
                {
                    curNode = curEdge.getTo( );
                    p = ( Person )curNode.getValue( );
                    p.removeFollower( label );
                    p.removeFollowing( label );
                }
                else
                {
                    p = ( Person )curNode.getValue( );
                    p.removeFollower( label );
                    p.removeFollowing( label );
                } 
                iter.remove( ); //removes curedge from list
            }
        }
    }

    // NAME: removeEdge
    // PURPOSE: to remove an edge from the graph and eeach node
    // IMPORTS: labels of 2 nodes
    // EXPORTS: NONE
    public void removeEdge( String label1, String label2 )
    {
        DSAGraphNode node2, node1 = null;
        node1 = getVertex( label1 );
        node2 = getVertex( label2 );
        String label = ( label1 + label2);
        DSAEdge edge = getEdge( label );
        Iterator iter = edges.iterator( );
        DSAEdge curEdge = null;

        if( edge.isDirected( ) )//edge is directed )
        {    
            //edges.remove( label, edges );
            while( iter.hasNext( ) )
            {
                curEdge = ( DSAEdge )iter.next( );
                if( curEdge.getLabel( ).equals( label ) )
                {
                    iter.remove( );                  
                    //remove node from links list
                    node2.removeEdge( node1 );
                    node1.display();
                }
            }
        }
        else
        {
            edge.setFrom( node2 );
            edge.setTo( node1 );
            edge.setDirected( true );
            node1.removeEdge( node2 );
        }
    }
        
    //ACCESSORS

    public DSALinkedList getVertices( )
    {
        return vertices;
    }

    public int getVertexCount( )
    {
        return vertices.getCount( );
    }

    public DSALinkedList getEdges( )
    {
        return edges;
    }

    // NAME: getVertex
    // PURPOSE: to find corresponding vertex in the list
    // IMPORTS: label of vertex to find
    // EXPORTs: DSAGraphNode - the vertex
    public DSAGraphNode getVertex( String label )
    {
        //return vertex with matching label
        Iterator iter;
        iter = vertices.iterator( );
        boolean finished = false;
        DSAGraphNode node = null;
        
           
        while( iter.hasNext( ) && !finished )
        {
            node = ( DSAGraphNode )iter.next( );
            if( node.getLabel( ).equals( label ) )
            {
                finished = true;
            }
        }
        if( !finished )
        {
            System.out.println( "Couldn't find vertex: " + label );
        }
        return node;
    }  

    // NAME: getEdge
    // PURPOSE: to find an edge within the linked list
    // IMPORTS: label of edge to find
    // EXPORTS: the edge
    public DSAEdge getEdge( String label )
    {
        Iterator iter;
        iter = edges.iterator( );
        boolean finished = false;
        DSAEdge edge = null;
    
        while( iter.hasNext( ) && !finished )  
        {
            edge = ( DSAEdge )iter.next( );
            if( edge.getLabel( ).equals( label ) )
            {
                finished = true;
            }
        }
        if( !finished )
        {
        //    System.out.println( "Couldn't find edge: "+ label );
            edge = null;
        }
        return edge;
    }    
    
    public int getEdgeCount( )
    {
        return edges.getCount( );
    }

    // returns number of followers as opposed to followers + following
    public int degree( DSAGraphNode vertex )
    {
        return vertex.getDegree( );
    }

    //if node1 is found in node2's links, then node1 is following node2
    // NAME: isAdjacent
    // PURPOSE: to determine whether on node is following another
    // IMPORTS: labels of each node
    // EXPORTS: boolean
    public boolean isAdjacent( String label1, String label2 )
    {
        boolean adjacent = false;
        DSAGraphNode node2 = vertices.iterateFind( label2, vertices );
        DSAGraphNode node1 = vertices.iterateFind( label1, node2.getAdjacent( ) );//check if node1 is         following node 2 
        if( node1 != null )
        {
            adjacent = true;
        } 
        return adjacent;    
    }

   /* private DSACircularQueue depthFirstSearch( )
    {
        DSACircularQueue t = new DSACircularQueue( );
        DSAStack stack = new DSAStack( );
    
        DSAGraphNode startVertex = ( DSAGraphNode )vertices.peekFirst( );
        DSAGraphNode curVertex, g;
        startVertex.setVisited( );
        stack.push( startVertex );
        t.enqueue( startVertex );

        while( !stack.isEmpty( ) )
        {
            for( Object o: startVertex.getAdjacent( ) )
            {
                g = ( DSAGraphNode )o;
                if( !g.getVisited( ) )
                {
                    t.enqueue( g );
                    g.setVisited( );
                    stack.push( g );
                }
            
            }
            startVertex = ( DSAGraphNode )stack.pop( );
        }
        return t;
    }*/       
    
    // NAME: breadthFirstSearch
    // PURPOSE: used to traverse the graph
    // IMPORTS: NONE
    // EXPORTS: DSACirculaQueue
    private DSACircularQueue breadthFirstSearch( )
    {
        //method for setting all vertices as new
        DSACircularQueue queue = new DSACircularQueue( );
        DSACircularQueue t = new DSACircularQueue( );

        DSAGraphNode startVertex = ( DSAGraphNode )vertices.peekFirst( );
        DSAGraphNode curVertex, g;
        startVertex.setVisited( );

        queue.enqueue( startVertex );
        t.enqueue( startVertex );

        while( !queue.isEmpty( ) )
        {
            curVertex = ( DSAGraphNode )queue.dequeue( );
            for( Object o: curVertex.getAdjacent( ) )
            {
                g = ( DSAGraphNode )o;
                if( !g.getVisited( ) )       
                {
                    t.enqueue( g );
                    g.setVisited( );
                    queue.enqueue( g );
                }
            }
        }
        return t;
    }
 
    // NAME: displayAdjacencyList
    // PURPOSE: dislplays all vertices and their links
    // IMPORTS: NONE
    // EXPORTS: NONE        
    public void displayAdjacencyList( )
    {
        DSAGraphNode g;
        System.out.println( "Adjacency List: " );
        for( Object o: vertices )
        {
            g = ( DSAGraphNode )o;
            g.display( );
        }   
    }
    
    // NAME: displayVertices
    // PURPOSE: displays all vertices
    // IMPORTS: NONE
    // EXPORTS: NONE
    public void displayVertices( )
    {
        DSAGraphNode g;
        System.out.println( "VERTICES: " );
        for( Object o: vertices )
        {
            g = ( DSAGraphNode )o;
            System.out.print( g.getLabel( ) + ", " );    
        }
        System.out.println( );
    }

    // NAME: displayEdges
    // PURPOSE: displays all edge labels
    // IMPORTS: NONE
    // EXPORTS: NONE
    public void displayEdges()
    {
        DSAEdge g;
        System.out.println( "EDGES: " );
        for( Object o: edges )
        {
            g = (DSAEdge)o;
            System.out.println( g.toString( ) );
        }
    }
}
