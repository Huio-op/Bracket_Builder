package br.com.application.persistencia;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.application.Main;
import br.com.application.negocio.Usuario;
import br.univates.system32.DataBase.DBConnection;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.DataBase.IDB;
import br.univates.system32.PasswordEncoder;

public class DBUsuarios implements IDB<Usuario> {
	
	private DBConnection connection;
	
	public DBUsuarios() throws DataBaseException {

		this.connection = DBApp.getConnection();

//		this.connection = new DBConnection("wnlrmkyd", "RrqNt3iigJVgBsFIPOMi3KurycXoU7cD", "wnlrmkyd"
//				, "motty.db.elephantsql.com", "5432");
		
	}

	public void save(Usuario user) throws DataBaseException {
		
		if(user != null) {
			
			connection.runSQL("INSERT INTO usuario VALUES( '" + user.getEmail() + "', '"+user.getNome()+"', " +
					"'"+user.getSenha()+"', "+user.isOrganizador()+");");
			
		}
		
	}

	@Override
	public void delete(Usuario user) throws DataBaseException {

		if(user.isOrganizador()){

		}

	}

	@Override
	public void edit(Usuario user) throws DataBaseException {

		if(user != null) {

			connection.runSQL("UPDATE usuario SET nome = '"+user.getNome()+"', senha = '"+user.getSenha()+"', isOrganizador = " +user.isOrganizador()+" "+
					"WHERE email = '" + user.getEmail() + "';");

		}

	}

	public Usuario load(String email) throws DataBaseException, SQLException {
		
		String sql = "SELECT * FROM usuario WHERE email = '"+email+"';";
		Usuario u = null;
		
		ResultSet rs = connection.runQuerySQL(sql);
		
		if(rs.isBeforeFirst()) {
			rs.next();
			String userEmail = rs.getString("email");
			String nome = rs.getString("nome");
			String senha = rs.getString("senha");
			Boolean organizador = rs.getBoolean("isOrganizador");
			
			u = new Usuario(userEmail,nome,senha,organizador);
			
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
				Boolean organizador = rs.getBoolean("isOrganizador");

				Usuario u = new Usuario(userEmail,nome,senha,organizador);
				array.add(u);
			}
		}
		
		return array;
		
	}
	
	
	/*
	 * Método que retorna apenas uma coluna do Usuário, sendo ela definida pelo campo fieldToFilter em forma de String
	 * 
	 * @param fieldToFilter recebe o nome do campo em que será retornado todas as instâncias. ex: email.
	 */
	public ArrayList<String> loadAllFiltered(String fieldToFilter) throws DataBaseException, SQLException {
		
		ArrayList<String> array = new ArrayList<String>();
		String sql = "SELECT "+fieldToFilter+" FROM usuario;";
		
		ResultSet rs = connection.runQuerySQL(sql);
		
		if(rs.isBeforeFirst()) {
			while(rs.next()) {
	
				String aux = rs.getString(fieldToFilter);
				array.add(aux);
				
			}
		}
		
		return array;
		
	}
	
	public boolean checkEmail(String email) throws DataBaseException, SQLException {
		
		Boolean alreadyExists = false;
		
		String sql = "SELECT * FROM usuario WHERE email = '"+email+"';";
		ResultSet rs = connection.runQuerySQL(sql);
		
		if(rs.isBeforeFirst()) {
			alreadyExists = true;
		}
		
		return alreadyExists;
		
	}

	public boolean checkPassword(String email, String password) throws DataBaseException, SQLException,
			NoSuchAlgorithmException, InvalidKeySpecException {

		Boolean passRight = false;

		String sql = "SELECT u.senha FROM usuario u WHERE email = '"+email+"';";
		ResultSet rs = connection.runQuerySQL(sql);

		if(rs.isBeforeFirst()) {
			rs.next();
			String passEncripted = rs.getString("senha");
			passRight = PasswordEncoder.comparePasswords(password, passEncripted);
		}

		return passRight;

	}
	
}
