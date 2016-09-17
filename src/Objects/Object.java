package Objects;

public class Object {

	int x;
	int y;
	int id;
	String image;
	
	/** Constructor
	 * 
	 * @param x
	 * @param y
	 * @param id
	 * @param image
	 */
	public Object(int x, int y, int id, String image)
	{
		this.x=x;
		this.y=y;
		this.id=id;
		this.image=image;
		
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
}
