package br.com.application;

import br.com.application.persistencia.DBApp;
import br.univates.system32.DataBase.DBConnection;
import br.univates.system32.DataBase.DataBaseException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class SystemBracketBuilder extends Application {

	Properties p = new Properties();

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			DBApp.getConnection().connectionTest();

		} catch(DataBaseException e){
			System.out.println(e.getMessage());
		}

		if (!DBApp.isDBCreated()) {
			byte[] array = getClass().getResourceAsStream("/br/com/application/resources/scripts/DDL_BracketBuilder.pgsql").readAllBytes();
			DBApp.getConnection().runSQL(new String(array, StandardCharsets.UTF_8));
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

	public static void start(String[] args) {
		launch(args);
	}

}
