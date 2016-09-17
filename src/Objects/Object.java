package Objects;

import Server.Engine;

public class Object {

	public final static String PLAYER_IMAGE = "0";
	public final static String SIGNPOST_IMAGE = "s";
	
	int x;
	int y;
	int id;
	String image;
	boolean exists;

	/**
	 * Constructor
	 * 
	 * @param x the x position
	 * @param y
	 * @param image
	 */
	public Object(int x, int y, String image) {
		this.x = x;
		this.y = y;
		this.id = Engine.useNextID();
		this.image = image;
		this.exists = true;
	}

	public void destroy()
	{
		Engine.removeID(id);
		this.exists = false;
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

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isExists() {
		return exists;
	}

	public void setExists(boolean exists) {
		this.exists = exists;
	}

	
}
