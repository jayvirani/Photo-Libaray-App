package application;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * Class for a user in the program
 * @author Anuj Chaturvedi
 * @author Jay Virani
 */


public class User implements Serializable{
	
	private static final long serialVersionUID = 5167429166516579807L;
	
	public String name;
	public ArrayList<Album> albums;
	
	public User(String username) {
		this.name = username;
		albums = new ArrayList<Album>();
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
	 * Album getter
	 * @return ArrayList<Album>
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public ArrayList<Album> getAlbums(){
		return this.albums;
	}
	
	
	/** 
	 * toString for a User (displays the username)
	 * @return String
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public String toString() {
		return this.name;
	}
	
	
	/** 
	 * Checks if two users are the same
	 * @param u
	 * @return boolean
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public boolean equals(User u) {
		return this.name.equals(u.name);
	}
	
}
	
	
	
	