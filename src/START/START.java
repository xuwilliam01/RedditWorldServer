package START;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import Objects.Post;
import Server.Engine;
import Server.Server;
import Server.Subreddit;

public class START {
	
	public static void main(String[] args) {
		
		new Engine();
		Thread server = new Thread(new Server());
		server.run();
	}

}
