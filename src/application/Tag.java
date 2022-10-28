package application;

import java.io.Serializable;

/**
 * Class for tag info for a photo
 * @author Anuj Chaturvedi
 * @author Jay Virani
 */
public class Tag implements Serializable{

	private static final long serialVersionUID = 4164494876588522598L;
	
    private String tag, value;
    
    public Tag(String t, String v ) {
    	this.tag = t;
    	this.value = v;
    }
    
    
	/** 
	 * Tag getter
	 * @return String
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public String getTagName() {
		return tag;
	}
	
	/**
	 * Returns tag value
	 * @return an value of tag
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public String getTagValue() {
		return value;
	}
	
	/**
	 * Checks if two tags are the same
	 * @param other
	 * @return true if the tags are the same
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public boolean equals(Tag other) {
		return tag.equals(other.tag) && value.equals(other.value);
	}
	
	/**
	 * toString for tags
	 * @return toString representation of Tag object
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public String toString() {
		return tag + " - " + value;
	}
	
}