package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;
import application.Album;
import application.Photo;
import application.User;
import application.FileStream;
import application.Supplement;

/**
 * Controller for the Album Display page FXML
 * @author Anuj Chaturvedi
 * @author Jay Virani
 */

public class AlbumController {
	@FXML
	private Button addPhotoButton, deletePhotoButton, moveToAlbumButton, copyToAlbumButton, recaptionButton,
	cancelButton, confirmButton, buttonType, LogoutButton;
	@FXML
	private TextField captionField;
	@FXML
	private Label albumNameLabel, captionLabelFXML, AlbumName;
	@FXML
	private ChoiceBox<String> albumNameField;
	@FXML
	private ListView<Photo> photos;
	private ArrayList<User> users;
	private User user;
	private Album selectedAlbum;

	/**
	 * Starts the Album view
	 * @param users
	 * @param user
	 * @param selectedAlbum
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */

	public void start(ArrayList<User> users, User user, Album selectedAlbum) {
		this.users = users;
		this.user = user;
		this.selectedAlbum = selectedAlbum;
		AlbumName.setText(selectedAlbum.getName());

		photos.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>() {
			@Override
			public ListCell<Photo> call(ListView<Photo> photoList) {
				return new Supplement();
			}
		});

		photos.setItems(FXCollections.observableArrayList(selectedAlbum.getPhotos()));
		photos.getSelectionModel().select(0);
		disableInput(true);

		ArrayList<String> albumnames = new ArrayList<String>();
		albumnames.add(0, " ");
		ArrayList<Album> allalbums = user.getAlbums();
		for (Album curralbum : allalbums) {
			albumnames.add(curralbum.getName());
		}

		albumNameField.setItems(FXCollections.observableArrayList(albumnames));
		albumNameField.setValue(" ");

		albumNameField.setDisable(true);
		albumNameLabel.setDisable(true);
		captionLabelFXML.setDisable(true);
		captionField.setDisable(true);
	}

	/**
	 * Handles actions on pressing the Add Photo button
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public void handleAddPhotoButton() {
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Choose Image");
		chooser.getExtensionFilters().addAll(
				new ExtensionFilter("Image Files", "*.bmp", "*.BMP", "*.gif", "*.GIF", "*.jpg", "*.JPG", "*.png",
						"*.PNG"),
				new ExtensionFilter("Bitmap Files", "*.bmp", "*.BMP"),
				new ExtensionFilter("GIF Files", "*.gif", "*.GIF"), new ExtensionFilter("JPEG Files", "*.jpg", "*.JPG"),
				new ExtensionFilter("PNG Files", "*.png", "*.PNG"));
		File selectedFile = chooser.showOpenDialog(null);

		if (selectedFile != null) {
			Image image = new Image(selectedFile.toURI().toString());
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(selectedFile.lastModified());
			Photo newPhoto = new Photo(selectedFile.getName(), image, cal);

			for (Photo currentPhoto : selectedAlbum.getPhotos()) {
				if (currentPhoto.equals(newPhoto)) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Error adding photo");
					alert.showAndWait();
					return;
				}
			}

			photos.getItems().add(newPhoto);
			selectedAlbum.getPhotos().add(newPhoto);
			FileStream.saveData(users);
		}
	}

	/**
	 * Handles any sort of alteration to a photo including the recaptioning, moving albums, and copy to album
	 * @param event
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public void handleAlterPhotoButton(ActionEvent event) {
		buttonType = (Button) event.getSource();
		disableInput(false);

		if (buttonType.equals(recaptionButton)) {
			captionLabelFXML.setDisable(false);
			captionField.setDisable(false);
			captionField.setText(photos.getSelectionModel().getSelectedItem().getCaption());
			confirmButton.setText("Confirm Caption");
		} else if (buttonType.equals(moveToAlbumButton)) {
			albumNameLabel.setDisable(false);
			albumNameField.setDisable(false);
			confirmButton.setText("Confirm Move");
		} else if (buttonType.equals(copyToAlbumButton)) {
			albumNameLabel.setDisable(false);
			albumNameField.setDisable(false);
			confirmButton.setText("Confirm Copy");
		}

	}
	/**
	 * Handles actions for the cancel button
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public void handleCancelButton() {
		this.disableInput(true);
		confirmButton.setText("Confirm.");
		if (buttonType.equals(recaptionButton)) {
			captionLabelFXML.setDisable(true);
			captionField.setDisable(true);
			captionField.clear();
		} else {
			albumNameLabel.setDisable(true);
			albumNameField.setDisable(true);
			albumNameField.setValue(" ");
		}



	}

	/**
	 * Handles actions for the confirm button
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public void handleConfirmButton() {
		if (buttonType.equals(recaptionButton)) {
			photos.getSelectionModel().getSelectedItem().setCaption(captionField.getText());
			photos.refresh();
			captionLabelFXML.setDisable(true);
			captionField.setDisable(true);
			confirmButton.setText("Confirm");
			captionField.clear();
		} else {
			String destAlbum = albumNameField.getSelectionModel().getSelectedItem();
			Photo toBeMoved = photos.getSelectionModel().getSelectedItem();

			for (Album currentAlbum : user.getAlbums()) {
				if (currentAlbum.getName().equals(destAlbum)) {
					for (Photo currentPhoto : currentAlbum.getPhotos()) {
						if (currentPhoto.equals(toBeMoved)) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setContentText("Error moving.");
							alert.showAndWait();
							return;
						}
					}
					currentAlbum.getPhotos().add(toBeMoved);
					if (buttonType.equals(moveToAlbumButton)) {
						photos.getSelectionModel().select(0);
						handleDeletePhotoButton();
					}
					albumNameLabel.setDisable(true);
					albumNameField.setDisable(true);
					albumNameField.setValue(" ");
					disableInput(true);
					FileStream.saveData(users);
					return;
				}
			}
		}
		disableInput(true);
		FileStream.saveData(users);
	}

	/**
	 * Disables certain screens depending on what the user is doing/wants to do
	 * @param value
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public void disableInput(boolean value) {
		confirmButton.setDisable(value);
		cancelButton.setDisable(value);
		addPhotoButton.setDisable(!value);
		deletePhotoButton.setDisable(!value);
		copyToAlbumButton.setDisable(!value);
		moveToAlbumButton.setDisable(!value);
		copyToAlbumButton.setDisable(!value);
	}
	/**
	 * Handles actions for the delete photo button
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public void handleDeletePhotoButton() {
		selectedAlbum.getPhotos().remove(photos.getSelectionModel().getSelectedItem());
		photos.getItems().remove(photos.getSelectionModel().getSelectedItem());
		photos.refresh();
		photos.getSelectionModel().select(0);
		FileStream.saveData(users);
	}

	/**
	 * Handles actions for the open photo button (opens the photo in PhotoSubsystem)
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */

	public void handleOpenPhotoButton(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PhotoSubSystem.fxml"));
			Parent parent = (Parent) loader.load();
			PictureController controller = loader.<PictureController>getController();
			Scene scene = new Scene(parent);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			controller.start(users, photos, user, selectedAlbum);
			stage.setScene(scene);
			stage.show();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Handles actions for the back button (goes back a page)
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */

	public void handleBackButton(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/UserSubsystem.fxml"));
			Parent parent = (Parent) loader.load();
			UserController controller = loader.<UserController>getController();
			Scene scene = new Scene(parent);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			controller.start(user, users);
			stage.setScene(scene);
			stage.show();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Handles actions for the logout button (goes back to the login screen)
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */

	public void handleLogoutButton(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginScreen.fxml"));
			Parent parent = (Parent) loader.load();
			Scene scene = new Scene(parent);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

	
}