package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter writer = new PrintWriter(client.getOutputStream());
				
				
				Player player = new Player(client,reader,writer,0,0,Object.PLAYER_IMAGE, Engine.frontPage);
				Thread thread = new Thread(player);
				thread.start();
				
				players.add(player);
				
				System.out.println("Player has connected");
				
				
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
	
	public static void addToAll (Player player)
	{
		for (Player no:players)
		{
			no.addPlayerToList(player);
		}
	}

}
