package Objects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import Server.Engine;
import Server.Server;
import Server.Subreddit;

public class Player extends Object implements Runnable {

	Socket socket;
	BufferedReader input;
	PrintWriter output;
	StringBuilder message;
	String name = "Player";

	ArrayList<Player> playersToAppend;
	
	Subreddit subreddit;
	
	boolean [][] signsSent;
	
	int screenWidth = 2560;
	int screenHeight = 1440;
	

	/**
	 * Constructor
	 * 
	 * @param socket
	 * @param x
	 * @param y
	 * @param id
	 * @param image
	 */
	public Player(Socket socket, BufferedReader reader, PrintWriter writer, int x, int y, String image, Subreddit subreddit) {
		super(x, y, image);
		this.socket = socket;
		message = new StringBuilder();
		playersToAppend = new ArrayList<Player>();
		
		
		this.subreddit = subreddit;
		signsSent = new boolean [Subreddit.SIDE_LENGTH][Subreddit.SIDE_LENGTH];

		// Set up I/O
		input = reader;
		output = writer;

		Thread thread = new Thread(new Writer());
		thread.start();

		Server.addToAll(this);
	}

	@Override
	public void run() {

		while (true) {
			try {

				String command = input.readLine();
				System.out.println(command);
				String[] tokens = command.split(" ");

				if (tokens[0].equals("P")) {
					setX(Integer.parseInt(tokens[1]));
					setY(Integer.parseInt(tokens[2]));
					Server.addToAll(this);
					sendNewSigns();
				}
				else if (tokens[0].equals("N"))
				{
					setName(tokens[1]);
					Server.addToAll(this);
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
	 * 
	 * @author William Xu
	 *
	 */
	class Writer implements Runnable {

		@Override
		public void run() {
			while (true) {
				try {

					ArrayList<Player> toRemove = new ArrayList<Player>();

					for (Player player : playersToAppend) {
						queueMessage("P ");
						queueMessage(player.getID() + " ");
						queueMessage(player.getX() + " ");
						queueMessage(player.getY() + " ");
						queueMessage(player.getImage());
						toRemove.add(player);
					}

					for (Player player : toRemove) {
						playersToAppend.remove(player);
					}

					flushWriter();
					Thread.sleep((int) (1000.0 / Engine.TICK_RATE));

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void sendNewSigns()
	{
		int startRow = (int)(getY()-screenHeight/2.0 - 64);
		if (startRow < 0)
		{
			startRow = 0;
		}
		
		int endRow = (int)(getY()+screenHeight/2.0 + 64);
		if (endRow >= Subreddit.SIDE_LENGTH)
		{
			endRow = Subreddit.SIDE_LENGTH-1;
		}
		
		int startColumn = (int)(getX() - screenWidth/2.0 - 64);
		if (startColumn < 0)
		{
			startColumn = 0;
		}
		
		int endColumn = (int)(getX() + screenWidth/2.0 + 64);
		if (endColumn >= Subreddit.SIDE_LENGTH)
		{
			endColumn = Subreddit.SIDE_LENGTH-1;
		}
		
		for (int row = startRow; row <= endRow; row++)
		{
			for (int column = startColumn; column<= endColumn; column++)
			{
				
				if (subreddit.getSignGrid()[row][column]!=null && !signsSent[row][column])
				{
					signsSent[row][column]=true;
					Post post = subreddit.getSignGrid()[row][column];
					queueMessage("S ");
					queueMessage(post.getID() + " ");
					queueMessage(post.getX() + " ");
					queueMessage(post.getY() + " ");
					queueMessage(post.getUrl() + " ");
					queueMessage(post.getTitle() + " ");
					queueMessage(post.getScore() + " ");
				}
				
			}
		}
	}

	public void sendMessage(String message) {
		output.println(message);
		output.flush();
	}

	public void queueMessage(String text) {
		this.message.append(text);
	}

	public void flushWriter() {
		if (message.length() > 0) {
			output.println(message);
			output.flush();
			message = new StringBuilder();
		}
	}

	public void addPlayerToList(Player player) {
		playersToAppend.add(player);
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public BufferedReader getInput() {
		return input;
	}

	public void setInput(BufferedReader input) {
		this.input = input;
	}

	public PrintWriter getOutput() {
		return output;
	}

	public void setOutput(PrintWriter output) {
		this.output = output;
	}

	public StringBuilder getMessage() {
		return message;
	}

	public void setMessage(StringBuilder message) {
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Player> getPlayersToAppend() {
		return playersToAppend;
	}

	public void setPlayersToAppend(ArrayList<Player> playersToAppend) {
		this.playersToAppend = playersToAppend;
	}

	public Subreddit getSubreddit() {
		return subreddit;
	}

	public void setSubreddit(Subreddit subreddit) {
		this.subreddit = subreddit;
		
	}
	
	
	
	
}
