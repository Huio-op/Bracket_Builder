package br.application.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private final String url = "jdbc:postgresql://motty.db.elephantsql.com:5432/wnlrmkyd";
	private final String user = "wnlrmkyd";
	private final String password = "RfrS6oCl4yLc2BHElY_UiI9ELE0aCXNl";
	
	public void connect() {
		
		try(Connection connection = DriverManager.getConnection(url, user, password)){
			
			if(connection != null) {
				System.out.println("Connectou no servidor!");
			}else {
				System.out.println("Falha na conexão com o servidor!");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
