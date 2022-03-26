// FILE: DSAEdge.java
// AUTHOR: Elijah Combes
// PURPOSE: Provides a class for the creation of edges to be used in 
//          conjuction with DSAGraph class
// REQUIRES: DSAGraph class
// Last mod: 15/10/2019
         
import java.util.*;
import java.io.*;

public class DSAEdge implements Serializable
{
    private String label;
    private boolean visited;
    private DSAGraphNode startNode;
    private DSAGraphNode endNode;
    private boolean directed;

    // NAME: DSAEdge
    // PURPOSE: constructor for a DSAEdge object
    // IMPORTS: label ( a key for the edge ), start ( the graph node where 
    //          the edge starts from ), end ( graph node where the edge ends)                        ,inDir( boolean determining whether it is a directed edge )
    // EXPORTS: DSAEdge object          
    public DSAEdge( String inLabel, DSAGraphNode start, DSAGraphNode end, boolean inDir )
    {
        label = inLabel;
        visited = false;
        directed = inDir;
        startNode = start;
        endNode = end;
    }

    // NAME: getLabel
    // PURPOSE: returns label classfield
    // IMPORTS: none
    // EXPORTS: label (String)
    public String getLabel( )
    {
        return label;
    }

    // NAME: getVisited
    // PURPOSE: returns visited classfield
    // IMPORTS: none
    // EXPORTS visited (boolean)
    public boolean getVisited( )
    {
        return visited;
    }

    // NAME: getFrom
    // PURPOSE: returns the node that the edge starts from
    // IMPORTS: none
    // EXPORTS: startNode a graph node
    public DSAGraphNode getFrom( )
    {
        return startNode;
    }        
    
    // NAME: getTo
    // PURPOSE: returns the node that the edge ends at
    // IMPORTS: none
    // EXPORTS: endNode a graph node 
    public DSAGraphNode getTo( )
    {
        return endNode;
    }

    // NAME: isDirected
    // PURPOSE: returns 
    // IMPORTS: none
    // EXPORTS: isDirected a boolean
    public boolean isDirected( )
    {
        return directed;
    }

    // NAME: setTo
    // PURPOSE: Mutator to change the end node
    // IMPORTS: node, a graph node that the edge ends at
    // EXPORTS: none
    public void setTo( DSAGraphNode node )
    {
        endNode = node;
    }
    
    // NAME: setFrom
    // PURPOSE: mutator to change the start node
    // IMPORTS: node, a graoh node that the edge starts at
    // EXPORTS: none
    public void setFrom( DSAGraphNode node )
    {
        startNode = node;
    }

    // NAME: toString
    // PURPOSE: convert all edge classfields to string format
    // IMPORTS: none
    // EXPORTS: String
    public String toString( )
    {
        return ( "Label: "+label+", start: "+startNode.getLabel( )+", end: "+endNode.getLabel( )+", directed: "+directed+", vistited: "+visited );
    }

    // NAME: setDirected
    // PURPOSE: change the value of the directed boolean
    // IMPORTS: inDir, the new value for directed
    // EXPORTS: none 
    public void setDirected( boolean inDir )
    {
        directed = inDir;
    }

    // NAME: setVisited
    // PURPOSE: used when traversing the graph to set this edge as visited
    // IMPORTS: none
    // EXPORTS: none         
    public void setVisited( )
    {
        visited = true;   
    }
    
    // NAME: clearVisited
    // PURPOSE: sets the visited boolean to false
    // IMPORTS: none
    // EXPORTS: none
    public void clearVisted( )
    {
        visited = false;
    }
}
