package controller;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.Stage;
import application.Album;
import application.User;
import application.FileStream;


public class UserController{
	@FXML
	private Button open, add, delete, renameAlbum, search,
			cancel, confirm, logOut, actionButton;
	@FXML
	private TextField albumField;
	@FXML
	private ListView<Album> albums;
	private ArrayList<User> users;
	private User user;

	@FXML
	private Label title;
	
	public boolean rename = false;
	public String albumname;

	
	/** 
	 * @param user
	 * @param users
	 */
	public void start(User user, ArrayList<User> users) {
		this.disableInput(true);
		this.user = user;
		this.users = users;
		albums.setItems(FXCollections.observableArrayList(user.getAlbums()));
		albums.getSelectionModel().select(0);
		title.setText("User Dashboard For - " + user.getName().toString().toUpperCase());
	}

	
	/** 
	 * @param value
	 */
	public void disableInput(boolean value) {
		albumField.setDisable(value);
		confirm.setDisable(value);
		cancel.setDisable(value);
		open.setDisable(!value);
		add.setDisable(!value);
		delete.setDisable(!value);
		renameAlbum.setDisable(!value);
		search.setDisable(!value);
	}
	
	/** 
	 * @param event
	 */
	@FXML
	public void handleLogOutButton(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginScreen.fxml"));
			Parent parent = (Parent) loader.load();
			@SuppressWarnings("unused")
			LoginController controller = loader.<LoginController>getController();
			Scene scene = new Scene(parent);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}
	@FXML
	public void handleCancelButton() {
		this.disableInput(true);
		albumField.clear();
	}
	
	@FXML
	public void handleConfirmButton() {
		ObservableList<Album> albumList = albums.getItems();
		for (Album currentAlbum : albumList) {
			if (currentAlbum.getName().equals(albumField.getText())) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Error adding album");
				alert.showAndWait();
				return;
			}
		}
		if (rename) {
			albums.getSelectionModel().getSelectedItem().setName(albumField.getText());
			albums.refresh();
			FileStream.saveData(users);
			albumField.clear();
			this.disableInput(true);
			rename = false;
		} else {
			Album newAlbum = new Album(albumField.getText());
			user.getAlbums().add(newAlbum);
			albums.getItems().add(newAlbum);
			albums.getSelectionModel().select(newAlbum);
			albums.refresh();
			FileStream.saveData(users);
			albumField.clear();
			this.disableInput(true);
		}
	}
	@FXML
	public void handleAddAlbumButton() {
		disableInput(false);
	}
	
	/** 
	 * @param event
	 */
	@FXML
	public void handleOpenAlbumButton(ActionEvent event) {
		Album selectedAlbum = albums.getSelectionModel().getSelectedItem();

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
	@FXML
	public void handleDeleteAlbumButton() {
		Album album = albums.getSelectionModel().getSelectedItem();
		user.getAlbums().remove(album);
		albums.getItems().remove(album);
		albums.refresh();
		FileStream.saveData(users);
		Alert done = new Alert(AlertType.INFORMATION);
		done.setContentText("\"" + album.getName() + "\" was Deleted");
		FileStream.saveData(users);

	}
	@FXML
	public void handleRenameButton() {
		Album currentAlbum = albums.getSelectionModel().getSelectedItem();
		this.disableInput(false);
		albumField.setText(currentAlbum.getName());
		albumname = currentAlbum.getName();
		rename = true;

	}
	
	/** 
	 * @param event
	 */
	@FXML
	public void handlesearchPhotosButton(ActionEvent event) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SearchPhotos.fxml"));
			Parent parent = (Parent) loader.load();
			SearchController controller = loader.<SearchController>getController();
			Scene scene = new Scene(parent);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			controller.start(user, users);
			stage.setScene(scene);
			stage.show();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}
}
