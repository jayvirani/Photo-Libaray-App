package application;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Class for an Album
 * @author Anuj Chaturvedi
 * @author Jay Virani
 */
public class Album implements Serializable{

	private static final long serialVersionUID = -3223270621042516826L;
	
	private String name;
	private ArrayList<Photo> photos;
	int length;

	/**
	 * Constructor for an Album
	 * @param name
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public Album(String name) {
		this.name = name;
		photos = new ArrayList<Photo>();
		length = photos.size();
	}
	
	
	/** 
	 * Name getter
	 * @return String
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public String getName() {
		return this.name;
	}
	
	
	/** 
	 * Name setter
	 * @param name
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	/** 
	 * Photos getter
	 * @return ArrayList<Photo>
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public ArrayList<Photo> getPhotos() {
		return this.photos;
	}

	
	/** 
	 * Photo Count getter
	 * @return int
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public int getPhotoCount() {
		return this.photos.size();
	}

	
	/** 
	 * Checks to see if 2 albums are the same
	 * @param other
	 * @return boolean
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public boolean equals(Album other) {
		return name.equals(other.name);
	}
	
	
	/** 
	 * toString for an album
	 * @return String
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public String toString() {
		String result = "NAME: " + name + "\nPHOTO COUNT: " + photos.size(); 
		
		return result;
	}
	
}