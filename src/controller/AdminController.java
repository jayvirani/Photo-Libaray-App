package controller;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import application.User;
import javafx.scene.Node;

/**
 * Controller for the Admin page FXML
 * @author Anuj Chaturvedi
 * @author Jay Virani
 */


public class AdminController {
	@FXML
	private Button actionTypeButton, logOutButton, cancelButton, confirmButton, createUserButton, deleteUserButton,
			listUsersButton;
	@FXML
	private TextField userField;
	@FXML
	private ListView<User> users;

	/**
	 * Initalize users on start
	 * @param users
	 */

	public void start(ArrayList<User> users) {
		this.users.setItems(FXCollections.observableArrayList(users));
		this.users.getSelectionModel().select(0);
		this.users.setVisible(false);
		disableInput(true);
	}

	/**
	 * Handles actions for cancel button
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */

	public void handleCancelButton() {
		userField.clear();
		disableInput(true);
	}

	/**
	 * Handles actions for confirm button
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	 
	public void handleConfirmButton() {
		User newUser = new User(userField.getText());
		ObservableList<User> userList = users.getItems();

		for (User currentUser : userList) {
			if (currentUser.getName().equals(newUser.getName())) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Error adding User. User exists. ");
				alert.showAndWait();
				return;
			}
		}
		users.getItems().add(newUser);
		users.refresh();
		saveData();
		userField.clear();
		disableInput(true);
	}

	/**
	 * Handles deleting a user when delete is pressed
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public void handleDeleteUserButton() {
		User user = users.getSelectionModel().getSelectedItem();
		users.getItems().remove(user);
		users.refresh();
		saveData();
	}

	/**
	 * Handles actions for adding a user on button press
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public void handleAddButton() {
		disableInput(false);
	}

	/**
	 * Handles actions for listing users on button press
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */

	public void handleListUsersButton() {
		users.setVisible(true);
		users.refresh();
	}

	/**
	 * Disables certain screens depending on what the user is doing/wants to do
	 * @param value
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */

	protected void disableInput(boolean value) {
		userField.setDisable(value);
		confirmButton.setDisable(value);
		cancelButton.setDisable(value);
		createUserButton.setDisable(!value);
		deleteUserButton.setDisable(!value);
		listUsersButton.setDisable(!value);
	}

	/**
	 * Save state for a user by generating data.dat file and writing to it
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	private void saveData() {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream("data/data.dat");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

			objectOutputStream.writeObject(new ArrayList<>(Arrays.asList(users.getItems().toArray())));
			objectOutputStream.close();
			fileOutputStream.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Handles logout button (loads the login screen)
	 * @param event
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