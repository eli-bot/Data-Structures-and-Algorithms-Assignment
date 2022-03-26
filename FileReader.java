import java.util.*;
import java.io.*;

// FILE: FileReader.java
// AUTHOR: Elijah Combes
// PURPOSE: read and convert text files to a DSAGraph object, or an event
//          , also writes timesteps from the simulation to a file by appending
// COMMENTS: standard file io
// REQUIRES: SocialSim.java - for propagation algorithm for events, 
//           DSAGraph.java  - for storing a graph/network
//           DSACircularQueue.java for storing return value of SocialSim.propagate() 
// Last Mod: 27th October 2019
public class FileReader
{

    //input file, if F:Kira:Jen, then jen follows kira
    // NAME: readNetwork
    // PURPOSE: to read in a netork from a text file and create a corresponding
    //          graph to store the network.
    // IMPORTS: fileANme(String)
    // EXPORTS: graph(DSAGraph)          
    public static double readNetwork( String fileName, DSAGraph graph )
    {
        FileInputStream fileStrm = null;
        InputStreamReader rdr;
        BufferedReader bufRdr;
        
        String line, name1, name2;
        //DSAGraph graph = new DSAGraph( );
        StringTokenizer strTok;
        Person newPerson;
        double startTime = 0.0; 
        double endTime = 0.0;
        double runTime = 0.0;
        
        try
        {
            fileStrm = new FileInputStream( fileName );
            rdr = new InputStreamReader( fileStrm );
            bufRdr = new BufferedReader( rdr );
        
            line  = bufRdr.readLine( );
            startTime = System.nanoTime( ); //get time before creating graph
            while( line != null )
            {
                strTok = new StringTokenizer( line, ":" );
                if( !( strTok.countTokens( ) > 1 ) )
                {
                    name1 = strTok.nextToken( );
                    newPerson = new Person( name1 ); 
                    graph.addVertex( name1, newPerson );
                }
                else
                {
                    name1 = strTok.nextToken( );
                    name2 = strTok.nextToken( );
                    graph.addEdge( name2, name1, true );
                }        
                line = bufRdr.readLine( );             
            }
            endTime = System.nanoTime( ); // get time after making graph
            fileStrm.close( );
        }
        catch( IOException e )
        {
            if( fileStrm != null )
            {
                try
                {
                    fileStrm.close( );
                }
                catch( IOException ex2 ) {}
            }
        }
        runTime = ( endTime - startTime ) / 1000.0;
        return runTime;
    }

    // NAME: readEvent
    // PURPOSE: to read in event from a text file and then apply each event
    //          to the network
    // COMMENTS: also appends each timestep to a file, a timestep occurs after an
    //           event is complete.
    // IMPORTS: fileName, graph, prob[]
    // EXPORTS: graph(DSAGraph)
    public static DSAGraph readEvent( String fileName, DSAGraph graph , 
                                    double[] prob, double graphTime )
    {
        FileInputStream fileStrm = null;
        InputStreamReader rdr;
        BufferedReader bufRdr;

        DSACircularQueue queue = new DSACircularQueue( );
        DSAGraphNode node;
        Person p;
        Post newPost;

        String line, name1, name2, caption;
        StringTokenizer strTok;
        double startTime = 0.0;
        double endTime = 0.0;
        double runTime = 0.0;
        int before;
        String writeFile = ( "Simulation_" + System.currentTimeMillis( ) + ".txt" );
        File file = new File( writeFile );
        
        try
        {
            fileStrm = new FileInputStream( fileName );
            rdr = new InputStreamReader( fileStrm );    
            bufRdr = new BufferedReader( rdr );
        
            line = bufRdr.readLine( );
            while( line != null )
            {
                strTok = new StringTokenizer( line, ":" );
                name1 = strTok.nextToken( );
                if( name1.equals( "F" ) )
                {
                    name1 = strTok.nextToken( );
                    name2 = strTok.nextToken( );
                    graph.addEdge( name1, name2, true );
                }
                else if( name1.equals( "P" ) )
                {
                    name1 = strTok.nextToken( );
                    caption = strTok.nextToken( );
                    node = graph.getVertex( name1 );
                    p = ( Person )node.getValue( );
                    newPost = p.addPost( caption );
                    //propagate post
                    startTime = System.nanoTime( );
                    before = node.getDegree( );
                    queue = SocialSim.propagate( graph, node, newPost, prob );   
                    endTime = System.nanoTime( );
                    //runTime for spread of each post
                    runTime = ( endTime - startTime ) / 1000.0;
                    fileWriter( graph, node, before, newPost, runTime, 
                                queue.getCount( ), writeFile, graphTime );
                }
                else if( name1.equals( "A" ) )
                {
                    // add new vertex to the graph
                    name1 = strTok.nextToken( );
                    p = new Person( name1 );
                    graph.addVertex( name1, p );
                }
                else if( name1.equals( "U" ) )
                {
                    //unfollow a:b, b unfollows a
                    name1 = strTok.nextToken( );
                    name2 = strTok.nextToken( );      
                    graph.removeEdge( name2, name1 );
                }
                line = bufRdr.readLine( );
            }               
            fileStrm.close( );
        }
        catch( IOException e )
        {
            if( fileStrm != null )
            {
                try
                {
                    fileStrm.close( );
                }
                catch( IOException ex2 ) { }
            }
        }
        return graph;
    }                      

    // NAME: fileWrite
    // PURPOSE: to append the statistics of a graph/timestep to a unique file
    //          each run
    // IMPORTS: graph(DSAGraph) - the network used for stats, poster(Person) - the
    //          person who has made a post , before(int) - Persons follower count
    //          before an event occurs, runTime(double) - time that the propogation
    //          took to complete, spread(int) - # of people to see the post.
    // EXPORTS: none                 
    public static void fileWriter( DSAGraph graph , DSAGraphNode poster, int before,
                                   Post newPost, double runTime, int spread, 
                                   String fileName, double graphTime )
    {
        FileWriter fileStrm = null;
        BufferedWriter out = null;
        PrintWriter pw;
        int newFollowers = poster.getDegree( ) - before; //# of new followers
        
        try
        {
            fileStrm = new FileWriter( fileName, true );
            out = new BufferedWriter( fileStrm );
            pw = new PrintWriter( out );
            // fileStrm = new FileOutputStream( "uniqueFileName" );
         //   pw = new PrintWriter( fileStrm );
            
            pw.println( "Timestep: " );
            pw.println( "Time taken to create network: " + graphTime + " micro seconds." );
            pw.println( poster.getLabel( ) + "'s post reached " + spread + " people." );
            pw.println( "The post collected " + newPost.getLikes( ) + " likes." );
            pw.println( poster.getLabel( ) + " gained " + newFollowers + " followers." );
            pw.println( "Spread time: " + runTime + " micro seconds." );
            pw.println( );
            out.close( );        
        }
        catch( IOException e )
        {
            if( out != null )
            {
                try
                {
                    out.close( );
                }
                catch( IOException ex2 ) {}
            }
        }
    } 
                

}

