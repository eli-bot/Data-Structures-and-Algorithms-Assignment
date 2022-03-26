import java.util.*;
import java.io.*;

public class Post implements Serializable
{
    private String name; //name of poster
    private String caption;
    private int likes;

    public Post( String inName, String cap )
    {
        name = inName;
        caption = cap;
        likes = 0;
    }

    public String getName( )
    {
        return name;
    }
    
    public String getCaption( )
    {
        return caption;
    }

    public int getLikes( )
    {
        return likes;
    }

    public void like( )
    {
        likes++;
    }

    public void dislike( )
    {
        likes--;
    }        

    public void setCaption( String cap )
    {
        caption = cap;
    }

    public void displayPost( )
    {
        System.out.println( name + ": " + caption );
        System.out.println( likes + " <3 " );
    }
} 
