package Objects;

public class Signpost extends Object{

	String title;
	String url;
	
	int width;
	int height;
	
	public Signpost(int x, int y, String title, String url) {
		super(x, y, Object.SIGNPOST_IMAGE);
		
		width = 16*4;
		height = 16*4;
				
		
	}
	
	

}
