package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tuio.ConnectTuioToFX;

/**
 * Application starts up here
 */
@SuppressWarnings("restriction")
public class Main extends Application {

    static String title = "NGUI";

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/MyView.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle(title);
			primaryStage.show();

			new ConnectTuioToFX(primaryStage.getScene().getRoot());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		launch(args);

	}
}
