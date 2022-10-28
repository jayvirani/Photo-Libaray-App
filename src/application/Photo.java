package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import javafx.scene.image.Image;

/**
 * Photo class
 * @author Anuj Chaturvedi
 * @author Jay Virani
 */

public class Photo implements Serializable{

	private static final long serialVersionUID = -6386115881929055391L;

	private ArrayList<Tag> tags;
	private String name, caption;
	private SerializableImage image;
	private Calendar cal;
	/**
	 * Photo Constructor based on name and a serializable image 
	 * @param name
	 * @param image
	 * @param cal
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public Photo(String name, SerializableImage image, Calendar cal) {
		this.name = name;
		this.caption = "";
		this.image = image;
		this.tags = new ArrayList<Tag>();
		this.cal = cal;
		this.cal.set(Calendar.MILLISECOND, 0);
	}

	/**
	 * Photo Contstructor based on name and image information
	 * @param name
	 * @param image
	 * @param cal
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	
	public Photo(String name, Image image, Calendar cal) {
		this.name = name;
		this.caption = "";
		this.image = new SerializableImage(image);
		this.cal = cal;
		this.tags = new ArrayList<Tag>();
		this.cal.set(Calendar.MILLISECOND, 0);
	}
	
	/**
	 * Returns the name of this photo
	 * @return the name of this photo
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the caption of this photo
	 * @return the caption of this photo
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * Returns the image represented by this photo
	 * @return the image represented by this photo
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public Image getImage() {
		return image.getImage();
	}

	/**
	 * Returns the tags for this photo
	 * @return an arraylist of tags
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public ArrayList<Tag> getTags() {
		return tags;
	}

	/**
	 * Returns the last modified date of this photo
	 * @return the last modified date of this photo
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public Calendar getCal() {
		return cal;
	}

	/**
	 * Set the caption of this photo
	 * @param caption the new caption of this photo
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * Compares this photo to another
	 * @param other the photo to be compared to
	 * @return true if the photos are equal, false otherwise
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public boolean equals(Photo other) {
		return this.name.equals(other.name);
	}

}
