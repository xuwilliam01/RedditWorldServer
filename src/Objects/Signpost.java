package Objects;

public class Signpost extends Object{

	String title;
	String url;

	String subreddit;
	String author;
	int points;
	int numComments;
	String permalink;
	String domain;
	String id;

	String getDetails(){
		String details=author +" posted this and got " +numComments +" replies";
		return details;
	}

	String getTitle(){
		return title;
	}

	String getScore(){
		return Integer.toString(points);
	}

	public Signpost(int x, int y, String title, String url) {
		super(x, y, Object.SIGNPOST_IMAGE);
		
	}
	
}
