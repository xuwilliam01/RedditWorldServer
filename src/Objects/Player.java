package Objects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Player extends Object implements Runnable{

	Socket socket;
	BufferedReader input;
	PrintWriter output;
	
	/**
	 * Constructor
	 * @param socket
	 * @param x
	 * @param y
	 * @param id
	 * @param image
	 */
	public Player(Socket socket , int x, int y, String image)
	{
		super (x,y,image);
		this.socket = socket;
		
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void run() {
		
		
	}
	
	
}
