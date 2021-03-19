package br.application;
	
import br.application.persistencia.DBConnection;
import br.application.persistencia.DBUsuarios;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.initStyle(StageStyle.UNDECORATED);
			Parent root = FXMLLoader.load(getClass().getResource("/br/application/apresentacao/TelaLogin.fxml"));
			Scene scene = new Scene(root,650,550);
			scene.getStylesheets().add(getClass().getResource("/br/application/apresentacao/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			DBConnection connection = new DBConnection();
			connection.connect();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		
	}
}
