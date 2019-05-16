/*
 * @Dilanas Sabaliauskas
 */

package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("fxml/Ui.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add("/css/DigitsOnly.css");
		primaryStage.setScene(scene);
		primaryStage.setTitle("Loan calculator");
		primaryStage.show();
	}
}