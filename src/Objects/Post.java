package Objects;

public class Post extends Object{

	String m_title;
	String m_url;
    int m_score;

	String subreddit;
	String author;
	int numComments;
	String permalink;
	String domain;
	String id;

    public String getDetails(){
		String details=author +" posted this and got " +numComments +" replies";
		return details;
	}

	public String getTitle(){
		return m_title;
	}
    public String getUrl(){
        return m_url;
    }

	public String getScore(){
		return Integer.toString(m_score);
	}

	public Post(String title, String url, int score) {
        super(0, 0, Object.SIGNPOST_IMAGE);
        m_title = title;
        m_url = url;
        m_score = score;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
