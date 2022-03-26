import java.util.*;
import java.io.*;

// FILE: DSAGraphNode.java
// AUTHOR: Elijah Combes
// PURPOSE: provides a class to create nodes to be used by DSAGraph
// REFERENCES: Previously submitted for practical 5
// Last Mod: 20/10/2019
public class DSAGraphNode implements Serializable
{
    private String label;
    private Object value;
    private DSALinkedList links;
    private boolean visited;

    // NAME: DSAGraphNode
    // PURPOSE: creates a DSAGraphNode object
    // IMPORTS: none
    // EXPORTS: graph node
    public DSAGraphNode( )
    {
        label = "";
        value = null;
        links = new DSALinkedList( );
        visited = false;
    }

    // NAME: DSAGraphNode
    // PURPOSE: alternate constructor for DSAGraphNode objects
    // IMPORTS: inLabel, a key for the node, inValue, the value for the node to store
    // EXPORTS: DSAGraphNode
    public DSAGraphNode( String inLabel, Object inValue )
    {
        label = inLabel;
        value = inValue;
        links = new DSALinkedList( );
        visited = false;
    }

    // NAME: getLabel
    // PURPOSE: returns the key for this node
    // IMPORTS: none:
    // EXPORTS: label, a string
    public String getLabel( )
    {
        return label;
    }

    // NAME: getAdjacent
    // PURPOSE: returns a list of adjacent nodes to this node
    // IMPORTS: none
    // EXPORTS: links, a DSALinkedList
    public DSALinkedList getAdjacent( )
    {
        return links;
    }

    // NAME: getValue
    // PURPOSE: returns the value stored by this node
    // IMPORTS: none
    // EXPORTS: value, an Object
    public Object getValue( )
    {
        return value;
    }
    
    // NAME: addEdge
    // PURPOSE: to add an edge between this node and the imported node,
    // IMPORTS: vertex, a DSAGraphNode
    // EXPORTS: none
    public void addEdge( DSAGraphNode vertex )
    {
        //add link to and from imported vertex
        links.insertLast(vertex);
        //links.iterateOverLinks(links);   
        //vertex.links.insertLast(this);   
    }

    // NAME: removeEdge
    // PURPOSE: removes an edge between this node and the imported node
    // IMPORTS: vertex, the node that this node has a relationship with
    // EXPORTS: none
    public void removeEdge( DSAGraphNode vertex )
    {
        if( !links.isEmpty( ) )
        {    
            links.remove( vertex.getLabel( ), links );
            //vertex.getAdjacent( ).remove( label, vertex.getAdjacent( ) );
        }    
        // remove vertex 2 from vertex 1 links
        // remove vertex 1 from vertex 2 links     
    }

    // NAME: setVisited
    // PURPOSE: sets the visited boolean as true, used for traversal
    // IMPORTS: none
    // EXPORTS: none
    public void setVisited( )
    {
        visited = true;
    }
    
    // NAME: clearVisited
    // PURPOSE: sets visited boolean to false, used for traversal of a graph
    // IMPORTS: none
    // EXPORTS: none
    public void clearVisited( )
    {
        visited = false;
    }

    // NAME: getVisited
    // PURPOSE: accessor for the visited boolean
    // IMPORTS: none
    // EXPORTS: visited, boolean
    public boolean getVisited( )
    {
        return visited;
    }

    // NAME: getDegree
    // PURPOSE: gets the number of edges connected to this vertex
    // IMPORTS: none
    // EXPORTS: degree, integer
    public int getDegree( )
    {
        return links.getCount( );
    }

    // NAME: display
    // PURPOSE: displays the classfields of this node to the user
    // IMPORTS: none
    // EXPORTS: the data the classfields contain in text form
    public String display( )
    {
        System.out.println( "Vertex: "+label );
        System.out.println( "Links: " );
        links.iterateOverLinks(links);
        return label;//(", Label: "+label);  
    }
}
