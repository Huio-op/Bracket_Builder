package br.com.application;
	
import br.univates.system32.DataBase.DBConnection;
import br.univates.system32.DataBase.DataBaseException;
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
			DBConnection db = new DBConnection("wnlrmkyd", "RfrS6oCl4yLc2BHElY_UiI9ELE0aCXNl", "wnlrmkyd"
					, "motty.db.elephantsql.com", "5432");
			db.connectionTest();
		} catch(DataBaseException e){
			System.out.println(e.getMessage());
		}
		
		try {
			//primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setResizable(false);
			Parent root = FXMLLoader.load(getClass().getResource("/br/com/application/apresentacao/TelaLogin.fxml"));
			Scene scene = new Scene(root,950,750);
			scene.getStylesheets().add(getClass().getResource("/br/com/application/apresentacao/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.getMessage();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
