package controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Callback;
import application.Album;
import application.Photo;
import application.Tag;
import application.User;
import application.FileStream;
import application.Supplement;
import java.util.Calendar;
import java.util.Locale;


public class SearchController {

	@FXML
	private Button createAlbumBtn, LogoutButton;
	@FXML
	private ChoiceBox<String> tagTypeChoiceBox, tagValueChoiceBox;
	@FXML
	private DatePicker fromDate, toDate;
	@FXML
	private ListView<Tag> tags;
	@FXML
	ListView<Photo> photoListView;

	ArrayList<User> users;
	private User user;

	boolean Checked, go;

	
	/** 
	 * @param user
	 * @param users
	 */
	public void start(User user, ArrayList<User> users) {
		this.user = user;
		this.users = users;

		photoListView.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>() {
			@Override
			public ListCell<Photo> call(ListView<Photo> photoList) {
				return new Supplement();
			}
		});

		ArrayList<String> tagtype = new ArrayList<String>();
		ArrayList<String> tagvalue = new ArrayList<String>();
		tagtype.add(0, "Tag Type");
		tagvalue.add(0, "Tag Value");
		ArrayList<Album> allalbums = user.getAlbums();
		for (Album curralbum : allalbums) {
			ArrayList<Photo> allphoto = curralbum.getPhotos();
			for (Photo photo : allphoto) {
				ArrayList<Tag> tag = photo.getTags();
				for (Tag t : tag) {
					if (!tagtype.contains(t.getTagName()))
						tagtype.add(t.getTagName());
					if (!tagvalue.contains(t.getTagValue()))
						tagvalue.add(t.getTagValue());
				}

			}

		}
		tagTypeChoiceBox.setItems(FXCollections.observableArrayList(tagtype));
		tagTypeChoiceBox.setValue("Tag Type");

		tagValueChoiceBox.setItems(FXCollections.observableArrayList(tagvalue));
		tagValueChoiceBox.setValue("Tag Value");

	}

	
	/** 
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
 
	
	/** 
	 * @param event
	 */
	public void handleBackToAlbumsButton(Event event) {
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
	 * @param event
	 */
	public void handleAddTag(ActionEvent event) {

		ObservableList<Tag> tagList = tags.getItems();

		Tag newTag = new Tag(tagTypeChoiceBox.getSelectionModel().getSelectedItem().toString(),
				tagValueChoiceBox.getSelectionModel().getSelectedItem().toString());
		for (Tag currentTag : tagList) {

			if (currentTag.equals(newTag)) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Error adding tag.");
				alert.showAndWait();
				return;
			}
		}

		tags.getItems().add(newTag);
		tags.refresh();
		tags.getSelectionModel().select(0);
		tagTypeChoiceBox.getSelectionModel().select(0);
		tagValueChoiceBox.getSelectionModel().select(0);

	}

	
	/** 
	 * @param event
	 */
	public void handleRemoveTag(ActionEvent event) {
		tags.getItems().remove(tags.getSelectionModel().getSelectedItem());
		tags.refresh();
		tags.getSelectionModel().select(0);

	}

	
	/** 
	 * @param event
	 */
	public void handleSearchPhotos(ActionEvent event) {

		photoListView.getItems().clear();
		Checked = false;
		go = false;

		ArrayList<Album> albumList = user.getAlbums();
		for (Album album : albumList) {
			ArrayList<Photo> photolist = album.getPhotos();
			for (Photo photo : photolist) {
				boolean added = false;
				ArrayList<Tag> phototag = photo.getTags();
				String[] photodate = photo.getCal().getTime().toString().split(" ");

				DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMM").withLocale(Locale.ENGLISH);
				TemporalAccessor accessor = parser.parse(photodate[1]);
				int month = accessor.get(ChronoField.MONTH_OF_YEAR);

				LocalDate photoDate = LocalDate.of(Integer.parseInt(photodate[5]), month,
						Integer.parseInt(photodate[2]));

				if (checkdatefields() && go) {

					String frdate = fromDate.getValue().toString();
					String[] fromdate = frdate.split("-");
					LocalDate f_date = LocalDate.of(Integer.parseInt(fromdate[0]), Integer.parseInt(fromdate[1]),
							Integer.parseInt(fromdate[2]));

					String todate = toDate.getValue().toString();
					String[] tdate = todate.split("-");
					LocalDate t_date = LocalDate.of(Integer.parseInt(tdate[0]), Integer.parseInt(tdate[1]),
							Integer.parseInt(tdate[2]));

					if (photoDate.isAfter(f_date) && photoDate.isBefore(t_date)) {

						if (photoListView.getItems().contains(photo)) {
							continue;
						} else {
							added = true;
							photoListView.getItems().add(photo);
							photoListView.refresh();
						}

					}
				}

				if (tags.getItems() != null && phototag != null && added == false) {
					for (Tag currTag : tags.getItems()) {
						for (Tag pTag : phototag) {
							if (currTag.getTagName().matches(pTag.getTagName()) && currTag.getTagValue().matches(pTag.getTagValue())) {
								if (photoListView.getItems().contains(photo)) {
									continue;
								} else {
									photoListView.getItems().add(photo);
									photoListView.refresh();
								}

							}

						}
					}

				}
			}
		}

	}


	
	/** 
	 * @return boolean
	 */
	public boolean checkdatefields() {

		if (Checked == false) {

			Checked = true;

			if (toDate.getValue() != null && fromDate.getValue() == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Incorrect field input");

				alert.showAndWait();
				return false;
			}
			if (toDate.getValue() == null && fromDate.getValue() != null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Incorrect Field Input");
				alert.showAndWait();
				return false;
			}

		}

		if (toDate.getValue() != null && fromDate.getValue() != null) {
			go = true;
			return true;
		}
		return false;
	}

	public void handleCreateAlbumFromResults() {
		if (photoListView.getItems().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Search is empty.");
			alert.showAndWait();

		} 
		else {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			Album newAlbum = new Album("Search Result " + dateFormat.format(cal.getTime()).toString());
			user.getAlbums().add(newAlbum);
			for (Photo currphoto : photoListView.getItems()) {
				newAlbum.getPhotos().add(currphoto);
			}
			FileStream.saveData(users);
		}
	}
}
