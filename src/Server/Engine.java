package Server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import Objects.Post;

public class Engine implements Runnable{

	public static ArrayList<Subreddit> subreddits;
	public static ArrayList<String> subNames;
	public static Subreddit frontPage;

	static boolean[] objectIDs = new boolean[100000];
	public final static int TICK_RATE = 60;

	public Engine() {
		subreddits = new ArrayList<Subreddit>();
		subNames = new ArrayList<String>();
		frontPage = new Subreddit("frontpage");
		addSubreddit(frontPage);
		Thread thread = new Thread(this);
		thread.start();
		
	}

	public static int useNextID() {
		for (int id = 0; id < objectIDs.length; id++) {
			if (!objectIDs[id]) {
				objectIDs[id] = true;
				return id;
			}
		}
		return -1;

	}

	public static void removeID(int id) {
		objectIDs[id] = false;
	}

	public static void addSubreddit(Subreddit subreddit) {
		subreddits.add(subreddit);
		subNames.add(subreddit.getName());
	}

	public static Subreddit getSubreddit(String name) {
		for (Subreddit sub : subreddits) {
			if (sub.getName().equals(name)) {
				return sub;
			}
		}
		return frontPage;
	}

	@Override
	public void run() {
		try {
			File file = new File("Subreddits");
			Scanner scan = new Scanner(file);
			Scanner fileScan;

			while (scan.hasNext()) {
				String name = scan.nextLine();
				if (Engine.subNames.contains(name)) {
					continue;
				}
				fileScan = new Scanner(new File(name));

				ArrayList<Post> posts = new ArrayList<Post>();

				int noOfPosts = fileScan.nextInt();

				for (int no = 0; no < noOfPosts; no++) {

					String title = fileScan.nextLine();
					String url = fileScan.nextLine();
					int score = Integer.parseInt(fileScan.nextLine());
					posts.add(new Post(title, url, score));
				}

				Subreddit subreddit = new Subreddit(name);
				subreddit.setPosts(posts);

				Engine.addSubreddit(subreddit);
				fileScan.close();
			}
			scan.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("Got here");
		Scanner in = new Scanner(System.in);
		while (in.nextLine().equals("1")) {

			System.out.println("Saving subs");

			try {
				PrintWriter write = new PrintWriter(new File("Subreddits"));

				for (String name : Engine.subNames) {
					write.println(name);

				}
				write.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (Subreddit sub : Engine.subreddits) {
				try {
					PrintWriter subWriter;
					subWriter = new PrintWriter(new File(sub.getName()));
					subWriter.println(sub.getPosts().size());

					for (Post post : sub.getPosts()) {
						subWriter.println(post.getTitle());
						subWriter.println(post.getUrl());
						subWriter.println(post.getScore());
					}
					subWriter.close();

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("Done");
		in.close();
		
	}

}
