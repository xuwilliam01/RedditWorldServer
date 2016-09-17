package Server;

public class Engine implements Runnable{

	World world;
	
	static boolean [] objectIDs = new boolean[100000];
	
	public Engine()
	{
		world = new World();
	}
	
	@Override
	public void run() {
		
		
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

}
