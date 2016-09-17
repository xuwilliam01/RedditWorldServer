package Objects;

public class Post extends Object{

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

	public Post(String title, String url) {
		super(0, 0, Object.SIGNPOST_IMAGE);
		
	}
	
	
}
