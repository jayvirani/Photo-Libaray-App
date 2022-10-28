package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * Runs the application
 * @author Anuj Chaturvedi
 * @author Jay Virani
 */
public class Main extends Application {
	
	
	
	/** 
	 * Sets the stage for JavaFX
	 * @param primaryStage
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/LoginScreen.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/** 
	 * Main method to run the program (RUN PROGRAM HERE)
	 * @param args
	 * @author Anuj Chaturvedi
	 * @author Jay Virani
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
