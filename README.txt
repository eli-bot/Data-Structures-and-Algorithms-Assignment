AUTHOR: Elijah Combes
Social network simulator

how to run: 
        - Interactive mode - java SocialSim -i
        - Simulation mode - java SocialSim -s <netFile> <eventFile> <prob_like> <prob_follow>
            where the files are text files and the prob are reals

SocialSim.java - program used emulate the spread of information throughout a social network

FileReader.java - deals with file io for the program                

DSAGraph.java - graph data structure used to represent and store a social network
 
DSAGraphNode.java - used to store and represent each node in the graph, used by DSAGraph

DSAEdge.java - used to represent relationships between two graph nodes/people

Person.java - used to store information about each person in the social network

Post.java - used to store information about each post a person in the network makes

The following classes are data structures that are used by the files above

DSALinkedList.java - linked list data Structure
    DSAListNode.java - list nodes to be stored in the linked list

DSAQueue - abstract class showing basic implementation of a queue structure
    DSACircularQueue - inherits from DSAQueue, makes a circular FIFO queue


