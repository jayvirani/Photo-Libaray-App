package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import application.Album;
import application.Photo;
import application.User;

/**
 * Controller for the Login page FXML
 * @author Anuj Chaturvedi
 * @author Jay Virani
 */
public class LoginController {
	@FXML
	private Button loginButton;
	
	@FXML
	private TextField usernameField;
	
	ArrayList<User> users;
	private final String path = "data/data.dat";
	Boolean validUser = false;

	/**
	 * Initialize the login screen
	 * @param event
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public void start(Stage stage) {
	}

	/**
	 * Handle actions when login button is clicked -- checks if user is valid and logs into user if it is if not gives an alert
	 * @param event
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */

	@SuppressWarnings("unchecked")
	@FXML
	public void handleLoginButton(ActionEvent event) {

		File file = new File(path);

		if (!file.exists() || !file.isFile()) {
			try {
				file.createNewFile();
				Album stockAlbum = new Album("stock");
				String stockPhotoPath = "data/stockPhotos";
				File photoFile1 = new File(stockPhotoPath + "/aboutUs1.jpg");
				File photoFile2 = new File(stockPhotoPath + "/aboutUs2.jpg");
				Image image1 = new Image(photoFile1.toURI().toString());
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(photoFile1.lastModified());
				Photo photo1 = new Photo(photoFile1.getName(),image1, cal);
				Image image2 = new Image(photoFile2.toURI().toString());
				Calendar cal2 = Calendar.getInstance();
				cal.setTimeInMillis(photoFile1.lastModified());
				Photo photo2 = new Photo(photoFile2.getName(),image2, cal2);
				stockAlbum.getPhotos().add(photo1);
				stockAlbum.getPhotos().add(photo2);
				User stock = new User("stock");
				stock.getAlbums().add(stockAlbum);
				users = new ArrayList<User>();
				users.add(stock);

				try {
					FileOutputStream fileOutputStream = new FileOutputStream(path);
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

					objectOutputStream.writeObject(users);

					objectOutputStream.close();
					fileOutputStream.close();
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}

		try {
			FileInputStream fileInputStream = new FileInputStream(path);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			users = (ArrayList<User>) objectInputStream.readObject();
			objectInputStream.close();
			fileInputStream.close();

			User user = null;
			String text = usernameField.getText();
			for (User currentUser : users) {
				if (currentUser.getName().equals(text)) {
					user = currentUser;

				}
			}

			if (text.equals("admin") || user != null) {
				FXMLLoader loader;
				Parent parent;

				if (text.equals("admin")) {
					loader = new FXMLLoader(getClass().getResource("/fxml/AdminSubsystem.fxml"));
					parent = (Parent) loader.load();
					AdminController admin = loader.<AdminController>getController();
					Scene scene = new Scene(parent);
					Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					admin.start(users);
					stage.setScene(scene);
					stage.show();
				} else {
					loader = new FXMLLoader(getClass().getResource("/fxml/UserSubsystem.fxml"));
					parent = (Parent) loader.load();
					UserController userController = loader.<UserController>getController();
					Scene scene = new Scene(parent);
					Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					userController.start(user, users);
					stage.setScene(scene);
					stage.show();
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Cannot find user");
				alert.showAndWait();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
