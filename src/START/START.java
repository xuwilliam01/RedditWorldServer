package START;

import Server.Engine;
import Server.Server;

public class START {

	public static void main(String[] args) {
		
		Thread server = new Thread(new Server());
		server.run();
		
		Thread engine = new Thread(new Engine());
		engine.run();

		
	}

}
