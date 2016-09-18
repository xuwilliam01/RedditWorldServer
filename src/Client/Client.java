package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client implements Runnable {

	public static final String IP = "127.0.0.1";

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		System.out.print("Enter your name: ");
		String name = scan.nextLine();
		new Client(name, IP, Server.Server.PORT);

		scan.close();
	}

	Socket socket;
	BufferedReader input;
	PrintWriter output;
	String name;

	public Client(String name, String IP, int port) {
		try {

			socket = new Socket(IP, port);

			System.out.println("Connected to server");

			this.name = name;
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream());

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Thread thread = new Thread(new Writer());
		thread.start();

		run();

	}

	@Override
	public void run() {
		while (true) {
			try {
				String message = input.readLine();

				System.out.println(message);

			} catch (IOException e) {

				break;
			}

		}
	}

	class Writer implements Runnable {

		@Override
		public void run() {
			while (true) {
				Scanner scan = new Scanner(System.in);

				String command = scan.nextLine();
				output.println(command);
				output.flush();
			}

		}

	}

}
