package application;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * class for data saving
 * @author Anuj Chaturvedi
 * @author Jay Virani
 */
public class FileStream {

	
	/** 
	 * Saves data into a dat file
	 * @param users
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public static void saveData(ArrayList<User> users) {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream("data/data.dat");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

			objectOutputStream.writeObject(users);

			objectOutputStream.close();
			fileOutputStream.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
