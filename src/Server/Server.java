package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Objects.Player;
import Objects.Object;

public class Server implements Runnable{

	public final static int port = 4200;
	ServerSocket server;
	public static ArrayList<Player> players = new ArrayList<Player>();
	
	public Server ()
	{
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	/**
	 * Accept clients
	 */
	public void run() {
		
		while (true)
		{
			try {
				Socket client = server.accept();
				
				Player player = new Player(client,Integer.MIN_VALUE,Integer.MIN_VALUE,Object.PLAYER_IMAGE);
				players.add(player);
				
				
				
			} catch (IOException e) {
				System.out.println("IO error");
				e.printStackTrace();
			}
			
			
		}
	}
	
	public static void removePlayer (Player player)
	{
		players.remove(player);
	}
	

}
