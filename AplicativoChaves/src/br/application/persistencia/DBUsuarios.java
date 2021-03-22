package br.application.persistencia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.application.negocio.Usuario;
import br.univates.system32.DataBase.DBConnection;
import br.univates.system32.DataBase.DataBaseException;

public class DBUsuarios {
	
	private DBConnection connection;
	
	public DBUsuarios() throws DataBaseException {
		
		this.connection = new DBConnection("wnlrmkyd", "RfrS6oCl4yLc2BHElY_UiI9ELE0aCXNl", "wnlrmkyd"
				, "motty.db.elephantsql.com", "5432");
		
	}

	public void save(Usuario user) throws DataBaseException {
		
		if(user != null) {
			
			connection.runSQL("INSERT INTO usuario VALUES( '" + user.getEmail() + "', '"+user.getNome()+"', '"+user.getSenha()+"');");
			
		}
		
	}
	
	public Usuario load(String email) throws DataBaseException, SQLException {
		
		String sql = "SELECT * FROM usuario WHERE email = "+email+";";
		Usuario u = null;
		
		ResultSet rs = connection.runQuerySQL(sql);
		
		if(rs.isBeforeFirst()) {
			rs.next();
			String userEmail = rs.getString("email");
			String nome = rs.getString("nome");
			String senha = rs.getString("senha");
			
			u = new Usuario(userEmail,nome,senha);
			
		}
		
		return u;
		
	}
	
	public ArrayList<Usuario> loadAll() throws DataBaseException, SQLException {
		
		ArrayList<Usuario> array = new ArrayList<Usuario>();
		String sql = "SELECT * FROM usuario;";
		
		ResultSet rs = connection.runQuerySQL(sql);
		
		if(rs.isBeforeFirst()) {
			while(rs.next()) {
		
				String userEmail = rs.getString("email");
				String nome = rs.getString("nome");
				String senha = rs.getString("senha");
				
				Usuario u = new Usuario(userEmail,nome,senha);
				array.add(u);
			}
		}
		
		return array;
		
	}
	
}
