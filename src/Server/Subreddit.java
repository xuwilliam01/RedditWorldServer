package Server;

import java.util.ArrayList;

import Objects.Post;
import Reddit.PostsList;

public class Subreddit implements Runnable{

	public final static int SIDE_LENGTH = 300;
	public final static int TILE_SIZE = 64;

	/**
	 * The radius around the sign (in tiles) where other signs cannot spawn
	 */
	public final static int SIGN_SPACE_RADIUS = 10;
	
	String name;

	Post[][] signGrid;
	boolean[][] usedTiles;

	ArrayList<Post> posts = new ArrayList<Post>();

	public Post[][] getSignGrid() {
		return signGrid;
	}

	public void setSignGrid(Post[][] signGrid) {
		this.signGrid = signGrid;
	}

	public boolean[][] getUsedTiles() {
		return usedTiles;
	}

	public void setUsedTiles(boolean[][] usedTiles) {
		this.usedTiles = usedTiles;
	}

	public ArrayList<Post> getPosts() {
		return posts;
	}

	public void setPosts(ArrayList<Post> posts) {
		this.posts = posts;
	}

	public Subreddit(String name) {
		this.name = name;
		Thread thread = new Thread(this);
		thread.run();
		placeSigns();
	}

	public void placeSigns() {
		for (Post sign : posts) {
			int row;
			int column;

			int noOfTries = 0;

			do {
				row = (int) (Math.random() * SIDE_LENGTH);
				column = (int) (Math.random() * SIDE_LENGTH);
				noOfTries++;
			} while (usedTiles[row][column] && noOfTries < 10000);
			
			if (noOfTries > 10000)
			{
				break;
			}
			
			usedTiles[row][column] = true;
			signGrid[row][column] = sign;
			sign.setX(column*TILE_SIZE);
			sign.setY(row*TILE_SIZE);
			
//			System.out.println("SignX: " + sign.getX());
//			System.out.println("SignY: " + sign.getY());

			for (int r = row - SIGN_SPACE_RADIUS; r <= row + SIGN_SPACE_RADIUS; r++) {
				for (int c = column - SIGN_SPACE_RADIUS; c <= column + SIGN_SPACE_RADIUS; c++) {

					// Formula for a circle (x-h)^2 + (y-k)^2 = r^2
					if (!(r < 0 || r >= SIDE_LENGTH || c < 0 || c >= SIDE_LENGTH || (r - row) * (r - row)
							+ (c - column) * (c - column) > SIGN_SPACE_RADIUS * SIGN_SPACE_RADIUS)) {

						usedTiles[r][c] = true;

					}
				}
			}
		}
	}

	@Override
	public void run() {
		//System.out.println("Runs");
		signGrid = new Post[SIDE_LENGTH][SIDE_LENGTH];
		usedTiles = new boolean[SIDE_LENGTH][SIDE_LENGTH];
		
		PostsList postList = new PostsList(name);
		posts = postList.fetch();
		
    	int i=0;
    	for(Post p: posts){
    		System.out.println(i++);
    		System.out.println(p.getUrl());
    	}
		
//		for (Post post:posts)
//		{
//			System.out.println(post.getTitle());
//		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

	
}
