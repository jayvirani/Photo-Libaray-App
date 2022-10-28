package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import application.Album;
import application.Photo;
import application.Tag;
import application.User;
import application.FileStream;

public class PictureController {

	
	@FXML
	private ImageView imageView;
	
	@FXML
	private Label photoName, caption, dateTaken;
	
	@FXML
	private Button next, previous, back, cancel, confirm, addTag, deleteTag, logout;
	
	@FXML
	private TextField tagType, tagValue;
	
	@FXML
	private ListView<Tag> tags;
	
	private Album selectedAlbum;
	private User user;
	SimpleDateFormat dateTimeformat = new SimpleDateFormat("MM/dd/yyyy 'at' hh:mm a");
	
	ArrayList<User> users;
	ListView<Photo> photos;
	
	
	/** 
	 * The start method displays the photo along with the name, caption, and date taken.
	 * @param users
	 * @param photos
	 * @param user
	 * @param selectedAlbum
	 */
	public void start(ArrayList<User> users, ListView<Photo> photos, User user, Album selectedAlbum) {
		this.users = users;
		this.photos = photos;
		this.user = user;
		this.selectedAlbum = selectedAlbum;
		Photo photo = photos.getSelectionModel().getSelectedItem();
		imageView.setImage(photo.getImage());
		photoName.setText(photo.getName());
		caption.setText(photo.getCaption());
		dateTaken.setText(dateTimeformat.format(photo.getCal().getTime()));
		tags.setItems(FXCollections.observableArrayList(photos.getSelectionModel().getSelectedItem().getTags()));
		tags.getSelectionModel().select(0);
		disableInput(true);
	}
	/**
	 * Method that handles the switching between photos when viewing PhotoSubSystem.
	 * @param event
	 */
	public void handleSwitchButton(ActionEvent event) {
		int currentIndex = photos.getSelectionModel().getSelectedIndex();

		if (((Button) event.getSource()).equals(next)) {
			if (currentIndex < photos.getItems().size())
				currentIndex++;
			else
				currentIndex = 0;
		} else {
			if (currentIndex > 0)
				currentIndex--;
			else
				currentIndex = photos.getItems().size() - 1;
		}

		photos.getSelectionModel().select(currentIndex);
		Photo selectedPhoto = photos.getSelectionModel().getSelectedItem();
		imageView.setImage(selectedPhoto.getImage());
		photoName.setText(selectedPhoto.getName());
		caption.setText(selectedPhoto.getCaption());
		dateTaken.setText(dateTimeformat.format(selectedPhoto.getCal().getTime()));
	}

	/**
	 * This method handles when the add tag button is clicked
	 */
	public void handleAddTagButton() {
		disableInput(false);
	}

	/**
	 * Handles the delete button and deletes the selected tag. Saves the users data.
	 * 
	 * @param event
	 */
	public void handleDeleteTagButton(ActionEvent event) {
		photos.getSelectionModel().getSelectedItem().getTags().remove(tags.getSelectionModel().getSelectedItem());
		FileStream.saveData(users);
		tags.getItems().remove(tags.getSelectionModel().getSelectedItem());
		tags.refresh();
		tags.getSelectionModel().select(0);
	}

	/**
	 * Adds new tag and saves data if tag does not already exist for photo
	 */
	public void handleConfirmButton() {
		ArrayList<Tag> tagList = photos.getSelectionModel().getSelectedItem().getTags();
		Tag newTag = new Tag(tagType.getText(), tagValue.getText());
		tagType.clear();
		tagValue.clear();

		for (Tag currentTag : tagList) {
			if (currentTag.equals(newTag)) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Error adding tag. already exists");
				alert.showAndWait();
				return;
			}
		}

		disableInput(true);
		tagList.add(newTag);
		FileStream.saveData(users);
		tags.getItems().add(newTag);
		tags.refresh();
		tags.getSelectionModel().select(0);
	}

	/**
	 * Cancels insertion of tag if user clicks cancel
	 * 
	 * @param event
	 *            takes the mouse click event.
	 */
	public void handleCancelTagButton(ActionEvent event) {
		tagType.clear();
		tagValue.clear();
		this.disableInput(true);

	}

	/**
	 * Brings user back to User Display if clicked
	 * 
	 * @param event
	 *            takes the mouse click event.
	 */
	public void handleBackButton(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AlbumDisplay.fxml"));
			Parent parent = (Parent) loader.load();
			AlbumController controller = loader.<AlbumController>getController();
			Scene scene = new Scene(parent);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			controller.start(users, user, selectedAlbum);
			stage.setScene(scene);
			stage.show();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Disables certain buttons based off of different scenarios
	 * 
	 * @param value - boolean value
	 */
	protected void disableInput(boolean value) {
		confirm.setDisable(value);
		cancel.setDisable(value);
		tagType.setDisable(value);
		tagValue.setDisable(value);
		addTag.setDisable(!value);
		deleteTag.setDisable(!value);
		next.setDisable(!value);
		previous.setDisable(!value);
	}

	/**
	 * Logs user out of account if clicked. 
	 * 
	 * @param event
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