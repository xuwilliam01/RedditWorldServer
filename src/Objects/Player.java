package Objects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import Server.Engine;
import Server.Server;

public class Player extends Object implements Runnable {

	Socket socket;
	BufferedReader input;
	PrintWriter output;
	StringBuilder message;
	
	String name = "Player";
	
	
	/**
	 * Constructor
	 * 
	 * @param socket
	 * @param x
	 * @param y
	 * @param id
	 * @param image
	 */
	public Player(Socket socket, int x, int y, String image) {
		super(x, y, image);
		this.socket = socket;

		// Set up I/O
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void run() {
		
		while (true)
		{
			try {
				
				String command = input.readLine();
				String [] tokens = command.split(" ");
				
				if (tokens[0].equals("P"))
				{
					setX(Integer.parseInt(tokens[1]));
					setY(Integer.parseInt(tokens[2]));
				}
				
				
			} catch (IOException e) {
				
				// The player disconnected
				Server.removePlayer(this);
				
				try {
					input.close();
					output.close();
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				break;
				
			}
		}
	}
	
	/**
	 * Writing thread
	 * @author William Xu
	 *
	 */
	class Writer implements Runnable
	{

		@Override
		public void run() {
			while (true)
			{
				try {
					
					for (Player player: Server.players)
					{
							queueMessage("P ");
							queueMessage(player.getID() + " ");
							queueMessage(player.getX() + " ");
							queueMessage(player.getY() + " ");
							queueMessage(player.getImage());
					}
					
					flushWriter();
					Thread.sleep((int)(1000.0/Engine.TICK_RATE));
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void sendMessage(String message)
	{
		output.println(message);
		output.flush();
	}
	
	public void queueMessage(String text)
	{
		this.message.append(text);
	}
	
	public void flushWriter()
	{
		output.println(message);
		output.flush();
	}
	
}
