import java.util.*;
import java.io.*;

public class Person implements Serializable
{
    private DSALinkedList posts;
    private DSALinkedList following;//may be irrelevant 
    private DSALinkedList followers;
    private String name;

    public Person( String inName )
    {
        posts = new DSALinkedList( );
        following = new DSALinkedList( );
        followers = new DSALinkedList( );
        name = inName;
    }

    public String getName( )
    {
        return name;
    }    
    
    public DSALinkedList getPosts( )
    {
        return posts;
    }

    public DSALinkedList getFollowing( )
    {
        return following;
    }

    public DSALinkedList getFollowers( )
    {
        return followers;
    }
    
    public int getFollowerCount( )
    {
        return followers.getCount( );
    }
    
    public int getFollowingCount( )
    {
        return following.getCount( );
    }    

    public int getPostsCount( )
    {
        return posts.getCount( );
    }

    public void displayPosts( )
    {
        Iterator iter = posts.iterator( );
        Post p;

        while( iter.hasNext( ) )
        {
            p = ( Post )iter.next( );
            System.out.println( name + ": " + p.getCaption( ) );
        }
       //iterator to display all posts in list
    }
    
    public void addFollower( DSAGraphNode newFollower )
    {
        followers.insertLast( newFollower );
    }

    public void follow( DSAGraphNode newFollow )
    {
        following.insertLast( newFollow );
    }

    public void addMutual( DSAGraphNode newPerson )
    {
        follow( newPerson );
        addFollower( newPerson );
    }

    public Post addPost( String caption ) 
    {
        Post newPost = new Post( name, caption );
        posts.insertLast( newPost );
        //sort order of likes?
        return newPost;
    }

    public void removeFollower( String label )
    {
        if( !followers.isEmpty( ) )
            followers.remove( label, followers );
    }

    public void removeFollowing( String label )
    {
        if( !following.isEmpty( ) )
            following.remove( label, following );
    }
    
/*    public void displayFollowers( )
    {
        DSALinkedListIterator iter = followers.iterator( );
        Person val;
        
        while( iter.hasNext( ) ) 
        {
            val = ( Person )iter.next( );    
            System.out.println( val.getName( ) + ", " );
        } 
    } */
}
    
     
    
