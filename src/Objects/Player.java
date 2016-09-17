package Objects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import Server.Engine;
import Server.Server;

public class Player extends Object implements Runnable {

	Socket socket;
	BufferedReader input;
	PrintWriter output;
	StringBuilder message;
	String name = "Player";

	ArrayList<Player> playersToAppend;

	/**
	 * Constructor
	 * 
	 * @param socket
	 * @param x
	 * @param y
	 * @param id
	 * @param image
	 */
	public Player(Socket socket, BufferedReader reader, PrintWriter writer, int x, int y, String image) {
		super(x, y, image);
		this.socket = socket;
		message = new StringBuilder();
		playersToAppend = new ArrayList<Player>();

		// Set up I/O
		input = reader;
		output = writer;

		Thread thread = new Thread(new Writer());
		thread.start();
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
}
