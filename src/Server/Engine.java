package Server;

import java.util.ArrayList;

public class Engine{

	public static ArrayList<Subreddit> subreddits;
	public static ArrayList<String> subNames;
	public static Subreddit frontPage;
	
	Subreddit world;
	
	static boolean [] objectIDs = new boolean[100000];
	public final static int TICK_RATE = 60;
	
	
	
	public Engine()
	{
		subreddits = new ArrayList<Subreddit>();
		subNames = new ArrayList<String>();
		frontPage = new Subreddit("frontpage");
		addSubreddit(frontPage);
	}

	
	
	public static int useNextID()
	{
		for (int id = 0; id < objectIDs.length; id++) {
			if (!objectIDs[id]) {
				objectIDs[id] = true;
				return id;
			}
		}
		return -1;
		
	}
	
	public static void removeID(int id)
	{
		objectIDs[id]=false;
	}
	
	public static void addSubreddit(Subreddit subreddit)
	{
		subreddits.add(subreddit);
		subNames.add(subreddit.getName());
	}
	
	public static Subreddit getSubreddit(String name)
	{
		for (Subreddit sub:subreddits)
		{
			if (sub.getName().equals(name))
			{
				return sub;
			}
		}
		return frontPage;
	}

}
