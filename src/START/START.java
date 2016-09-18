package START;

import Server.Engine;
import Server.Server;

public class START {

	public static void main(String[] args) {
		
		new Engine();
		Thread server = new Thread(new Server());
		server.run();
		
		
	}

}
