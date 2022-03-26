import java.util.*;
import java.io.*;

// FILE: SocialSim.java
// AUTHOR: Elijah Combes
// PURPOSE: Used to run the social simulation program. Contains all apporpriate
//          methods used for performing actions on a graph and also the methods for 
//          simulating the spread of information through a network.
// COMMENTS: Also contains the menu system for the program and a main method
//           these should have been placed in a seperate class. The propogation 
//           algorithm uses a modified breadth first search to spread informtaion
//           from the original poster to their followers.
// REQUIRES: FileReader.java - to read and write a network
//           DSACircularQueue.java - used in the propgation algorithm
//           DSAGraph.java - to create and store the network
// Last Mod: 27th October 2019
public class SocialSim
{
    // NAME: main
    // PURPOSE: sort command line arguments to determine whether the program should
    //          run in interactive or simulation mode and then call the corresponding
    //          methods.
    // IMPORTS: args, an array of command line arguments
    // EXPORTS: NONE
    public static void main( String[] args )
    {
        int choice;
        DSAGraph graph = new DSAGraph( );
        double[] prob = new double[2]; // array to hold probabilities [0] = like, [1] = follow
        double runTime;

        if( args.length < 1 )
        {
            usage();
        }
        else if( args[0].equals( "-i" ) && args.length == 1 )
        {
            choice = menu( prob ); //for interactive mode
        }
        else if( args[0].equals( "-s" ) && args.length == 5 )
        {
            try
            {
                prob[0] = Double.parseDouble( args[3] );
                prob[1] = Double.parseDouble( args[4] );
                runTime = FileReader.readNetwork( args[1], graph );
                graph = FileReader.readEvent( args[2], graph, prob, runTime );
            }
            catch( IllegalArgumentException e )
            {
                System.out.println( "Invalid command line arguments" );
                usage( );
            }
        } 
        else 
        {
            usage( );
        }
    }            

    // NAME: usage
    // PURPOSE: prints how to correctly run the program to the user
    // IMPORTS: none
    // EXPORTS: NONE
    public static void usage( )
    {
            System.out.println( "Usage: " );
            System.out.println( "SocialSim -i " );
            System.out.println( "SocialSim -s <netfile> <eventfile> <prob_like> <prob_foll>");
    }       

    // NAME: menu
    // PURPOSE: provides a menu and user interafce for the user and stores their
    //          choices and calls the appropriate methods based on their choice.
    // IMPORTS: prob, an array of reals storing the prob_like and prob_follow
    // EXPORTS: int, should be void          
    public static int menu( double[] prob )
    {
        int choice = -1;
        int secondChoice = -1;
        Scanner sc = new Scanner( System.in );
        DSAGraph graph = new DSAGraph( );
        String fileName, name;
        Person p = null;
        Post newPost = null;   //
        DSAGraphNode node= null;
 
        while( choice != 0 )
        {
            try
            {
                System.out.println( "Please select an option: " );
                System.out.println( "0. Exit" );
                System.out.println( "1. Load network " );
                System.out.println( "2. Set probabilities " );
                System.out.println( "3. Node operations " );
                System.out.println( "4. Edge operations " );
                System.out.println( "5. New post " );
                System.out.println( "6. Display network" );
                System.out.println( "7. Display statistics " );
                System.out.println( "8. Update " );
                System.out.println( "9. Save network " );
                
                choice = sc.nextInt( );
                switch( choice )
                {
                    case 1:
                        fileName = getFileName( );
                        //load network
                        graph = loadNetwork( fileName );
                        break;
                    case 2:
                        //probabilities
                        updateProbabilities( prob );
                        break;
                    case 3:
                        //nodeOperations( );
                        System.out.println( "Please choose an option: " );
                        System.out.println( "1. Find " );
                        System.out.println( "2. Insert " );
                        System.out.println( "3. Delete " );
                        System.out.println( "4. Back " );
                        secondChoice = inputInt( );               
                        nodeOperations( secondChoice, graph );                    
                        //node
                        break;
                    case 4:
                        System.out.println( "Please choose an option: " );
                        System.out.println( "1. Like post " );
                        System.out.println( "2. Un-Like post " );
                        System.out.println( "3. Follow account " );
                        System.out.println( "4. Unfollow account " );
                        System.out.println( "5. Back " );
                        secondChoice = inputInt( );
                        edgeOperations( secondChoice, graph );
                         //edge
                        break;
                    case 5:
                        newPost = postOperations( graph, node ); 
                        //new post
                        break;
                    case 6:
                        System.out.println( "Vertices: " );
                        graph.displayVertices( );
                        System.out.println( "\nEdges: " );
                        graph.displayEdges( );
                        System.out.println( "\n" );
                        //display network
                        break;
                    case 7:
                        //display stats
                        displayStats( graph );
                        break;
                    case 8:
                        //update
                        propagate( graph, node, newPost, prob );
                        break;
                    case 9: 
                        fileName = getFileName( );
                        //save network
                        saveNetwork( graph, fileName );
                        break;
                    default:
                        System.exit( 0 );
                        break;
                }
            }
            catch( InputMismatchException a )
            {
               System.out.println( "Invalid choice, please choose one of the options above." );
               choice = 0;
            }
        }
        return choice;
    }  

    // NAME: updateProbabilities
    // PURPOSE: To change the probabilities by prompting the user for new ones
    // IMPORTS: prob, array of reals containing probabilities
    // EXPORTS: NONE (prob array altered by reference)
    public static void updateProbabilities( double[] prob )
    {
        int choice;

        System.out.println( "Which probability would you like to update? :" );
        System.out.println( "1. Like probability " );
        System.out.println( "2. Follow probability " );
        
        choice = inputInt( );
        System.out.println( "Please enter the new probability: " );
        prob[ choice - 1 ] = inputReal( );
    } 
    
    // NAME: displayStats
    // PURPOSE: to display the starts of one person to the user
    // IMPORTS: graph(DSAGraph) the network so it can be searched for the person they
    //          want
    // EXPORTS: NONE
    public static void displayStats( DSAGraph graph )
    {
        Person p;
        DSAGraphNode node;
        String name;
        Scanner sc = new Scanner( System.in );
        System.out.println( "Please enter the name of the account you wish to show" ); 
        name = sc.nextLine( );
        node = graph.getVertex( name );
        p = ( Person )node.getValue( );
        p.displayPosts( );
    }

    // NAME: edgeOperations
    // PURPOSE: for the user to add new follows (edges) and remove follows,
    //          changing the relationships between nodes in the graph
    // IMPORTS: graph(DSAGraph) network to search through, choice(int) - the
    //          users action choice. 
    // EXPORTS: none
    public static void edgeOperations( int choice, DSAGraph graph )
    {
        DSAEdge newEdge = null;
        String name1, name2 = ""; 
        Scanner sc = new Scanner( System.in );
        switch( choice )
        {
            case 1:
                //Like post
                break;
            case 2:
                //unlike post
                break;
            case 3:
                System.out.println( "Please enter your account name: " );
                name1 = sc.nextLine( );
                System.out.println( "Please enter the account to follow: ");
                name2 = sc.nextLine( );       
                // name1 follows name2
                graph.addEdge( name1, name2, true );
                System.out.println( name1 + " is now following " + name2 );
                //follow account
               break;
            case 4:
                System.out.println( "Please enter your account name: " ); 
                name1 = sc.nextLine( );
                System.out.println( "Please enter the account to unfollow: " );
                name2 = sc.nextLine( );
                graph.removeEdge( name1, name2 );
                //unfollow acc ie remove edge
               break;
            case 5:
                break;
            default:
                System.out.println( "invalid choice " );
        }
    
    }

   // NAME: nodeOperations
   // PURPOSE: allows the user to find, insert and delete nodes in the graph
   // IMPORTS: choice(int), graph(DSAGraph)
   // EXPORTS: NONE
    public static void nodeOperations( int choice, DSAGraph graph )
    {
        Person p;
        String name;
        Scanner sc = new Scanner( System.in );
        switch( choice )
        {
            case 1: 
                // find
                System.out.println( "Please enter the name: " );
                name = sc.nextLine( ); 
                DSAGraphNode node = graph.getVertex( name );
                node.display( );
                break;
            case 2:
                //insert
                System.out.println( "Please enter the account name: " );
                name = sc.nextLine( ); 
                p = new Person( name );
                graph.addVertex( name, p );
                System.out.println( name + " added successfully. " );                          
                break;
            case 3:
                System.out.println( "Please enter the name of the account to remove: " );
                name = sc.nextLine( );
                graph.removeVertex( name ); 
                System.out.println( name + " has been removed. " );
                //delete  removerVertex in graph etc
                break;
            case 4:
                break;
            default:
                System.out.println( "Invalid choice, please try again" );
                break;
        }
    }
    
   // NAME: postOperations
   // PURPOSE: allows user to add a new post
   // IMPORTS: graph(DSAGraph) post is added to a node in this graph
   // EXPORTS: NONE 
    public static Post postOperations( DSAGraph graph, DSAGraphNode node )
    {
        String name, caption = "";
        Scanner sc = new Scanner( System.in );
        Post newPost;
        Person p = null;

        System.out.println( "Please enter the name of the poster: " );
        name = sc.nextLine( );
        System.out.println( "Please enter the post/caption: " );
        caption = sc.nextLine( );
        //adds new post to "name"  
        node = graph.getVertex( name );
        p = ( Person )node.getValue( );
        newPost = p.addPost( caption );         
        System.out.println( "Post added successfully. " );
        return newPost;
    }

    // NAME: propagate
    // PURPOSE: to simulate the spread of a new post throughout a social network
    //          spreads post to the posters' followers, giving them the chance to 
    //          like the post which will spread it to their followers, giving
    //          their followers the chance to like the post and follow the original
    //          poster.
    // COMMENTS: should have been split into multiple methods
    // IMPORTS: graph(DSAGraph) - the network, poster(DSAGraphNode) - the person
    //          who is making the post, newPost(Post) - the new post to be spread,
    //          prob[] - array of probabilities for liking and following
    // EXPORTS: t(DSACircularQueue) - queue that contains every person that
    //          has seen the post regardless if they liked it or not.               
    public static DSACircularQueue propagate( DSAGraph graph, DSAGraphNode poster, Post newPost , double[] prob )
    {
        Person ogPoster = ( Person )poster.getValue( );
        DSACircularQueue queue = new DSACircularQueue( );
        DSACircularQueue t = new DSACircularQueue( );//t can represent total spread ie people that saw the post
        DSAGraphNode curVertex, g;
        Person curPerson = ogPoster;
        queue.enqueue( poster );
      
        clearVisited( graph.getVertices( ) );
        while( !queue.isEmpty( ) )
        {
            curVertex = ( DSAGraphNode )queue.dequeue( );
            for( Object o: curVertex.getAdjacent( ) ) //get curVertex's followers
            {
                g = ( DSAGraphNode )o;
                curPerson = ( Person )g.getValue( );
                if( !g.getVisited( ) )
                {
                    t.enqueue( g ); //g has seen the post
                    if( probability( prob[0] ) )//probability of liking a post
                    {
                        //g liked the post
                        newPost.like( );
                        System.out.println( curPerson.getName( ) + " liked " + 
                                            ogPoster.getName( ) + "'s post." );
                        queue.enqueue( g );//only if g likes post
                        
                        // chance for g to follow poster if not already following
                        if( validateFollow( graph, ogPoster, curPerson ) )
                        {  
                            //chance to follow ogPoster
                            if( probability( prob[1] ) )
                            {
                                //g follows original poster
                                graph.addEdge( curPerson.getName( ), ogPoster.getName( ), true );
                                System.out.println( curPerson.getName( ) + " followed " + 
                                                    ogPoster.getName( ) + "." );
                            }     
                        }       
                    }
                    g.setVisited( );
                }
            }
        }
        return t;
    } 
    
    // NAME: clearVisited
    // PURPOSE: to set all vertices in a graph to unvisited
    // IMPORTS: graph
    // EXPORTS: none
    public static void clearVisited( DSALinkedList vertices )
    {
        Iterator iter = vertices.iterator( );
        DSAGraphNode curNode = null;

        while( iter.hasNext( ) )
        {
            curNode = ( DSAGraphNode )iter.next( );
            curNode.clearVisited( );
        }        
    }
         
 
    // NAME: validateFollow
    // PURPOSE: validates whether a person can follow another
    //          ie. cant follow themself and cant follow twice
    // IMPORTS: graph, poster, curPerson
    // EXPORTS: valid (boolean)
    public static boolean validateFollow( DSAGraph graph, Person poster, Person curPerson )
    {   
        boolean valid = false;
        
        // checks if curPerson is already following the original poster
        if( !graph.isAdjacent( curPerson.getName( ), poster.getName( ) ) )
        {
            // a person cannot follow themself
            if( !( curPerson == poster ) )
            {
                valid = true;
            }
        }
        return valid;
    }

    // NAME: probability
    // PURPOSE: determines whether a person will like or follow based on the
    //          imported probability, if true then the action occurs
    // IMPORTS: probability(real), 
    // EXPORTS: action(Boolean)         
    public static boolean probability( double probability )
    {
        boolean action = false; //action represents liking or following
        
        if( new Random( ).nextDouble( ) <= probability )
        {
            action = true;
        }
        return action;
    }
    
    // NAME: inputInt
    // PURPOSE: retrieve integer from user input
    // IMPORTS: NONE
    // EXPORTS: integer, the int they entered
    public static int inputInt( )
    {
        int userInput;
        Scanner sc;
        sc = new Scanner( System.in );
        userInput = sc.nextInt( );

        return userInput;
    } 
    
    // NAME: inputReal
    // PURPOSE: retrieve a real from user input
    // IMPORTS: NONE
    // EXPORTS: double
    public static double inputReal( )
    {
        double userInput;
        Scanner sc;
        sc = new Scanner( System.in );
        userInput = sc.nextDouble( );
    
        return userInput;
    }   
    
    // NAME: loadNetwork
    // PURPOSE: to load a serialized network from a file
    // IMPORTS: fileName(String)
    // EXPORTS: graph(DSAGraph)
    public static DSAGraph loadNetwork( String fileName )
    {
        FileInputStream fileStrm;
        ObjectInputStream objStrm;
        DSAGraph inObj = null;
    
        try
        {
            fileStrm = new FileInputStream( fileName );
            objStrm = new ObjectInputStream( fileStrm ) ;
            inObj = ( DSAGraph )objStrm.readObject( );
            objStrm.close( );
            System.out.println( "Load successful!" );
        }
        catch( ClassNotFoundException e )
        {
            System.out.println( "Class DSAGraph not found" );
        }
        catch( Exception e )
        {
            throw new IllegalArgumentException( "Unable to load object from file" );
        }
        return inObj;
    }

    // NAME: saveNetwork
    // PURPOSE: to save a network/graph to a file
    // IMPORTS: graph - the graph to save, fileName
    // EXPORTS: NONE
    public static void saveNetwork( DSAGraph graph, String fileName )
    {
        FileOutputStream fileStrm;
        ObjectOutputStream objStrm;
        
        try
        {
            fileStrm = new FileOutputStream( fileName );
            objStrm = new ObjectOutputStream( fileStrm );
            objStrm.writeObject( graph );
            objStrm.close( );
            System.out.println( "Save successful!" );
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException( "Unable to save object to file" );
        }
    }

    // NAME: getFileName
    // PURPOSE: gets a file name from the user
    // IMPORTS: NONE
    // EXPORTS fileName(String)
    public static String getFileName( )
    {
        Scanner sc = new Scanner( System.in );
        String fileName = "default.txt";
        try
        {
            System.out.println( "Please enter the file name: " );
            fileName = sc.nextLine( );
        }
        catch( InputMismatchException e )
        {
            System.out.println( "Invalid file name" );
        }
        return fileName;
    }        
}                            
