package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			TabPane tabPane = (TabPane)FXMLLoader.load(this.getClass().getResource("Principal.fxml"));
			Scene scene = new Scene(tabPane);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("TesteFX");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
