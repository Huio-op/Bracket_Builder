package br.univates.system32.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

	private Connection connection;
	private ResultSet rs;
	
	private String url;
	private String user; 
	private String password; 
	private String dbName;
	
	public DBConnection(String user, String password, String dbname, String ip, String port) throws DataBaseException {
		
		this.connection = null;
		
		ip = (ip == null ? "localhost" : ip);
        port = (port == null ? "5432" : port );
		
		if(user == null || password == null) {
			throw new DataBaseException("Nome do usuário ou senha não definido.");
		}
		if(dbname == null) {
			throw new DataBaseException("Nome do banco de dados não foi definido.");
		}
		
		this.url = "jdbc:postgresql://" + ip + ":"+ port + "/" + dbname;
		this.dbName = dbname;
		this.user = user;
		this.password = password;
		this.dbName = dbname;
		
	}
	
	public void connect() throws DataBaseException {
		
		try{
			
			Connection connection = DriverManager.getConnection(url, user, password);
			this.connection = connection;
			
		} catch (SQLException e) {
			throw new DataBaseException("Falha na conexão com o servidor!");
		}
		
	}
	
	public void connectionTest() throws DataBaseException {
		
			try {
				
				connect();
				this.connection.close();
				
			}catch (SQLException e) {
					throw new DataBaseException("Teste de Concexão com o Banco de Dados falhou.");
				}
			catch (DataBaseException e) {
				throw new DataBaseException("Teste de Concexão com o Banco de Dados falhou.");
			}
			
		
	}
	
	public void executeSQL(String sql) throws DataBaseException {
		Statement stm;
		
		try {
			
			stm = connection.createStatement();
			
			try {
				
				stm.executeUpdate(sql);
				
			} catch (SQLException e) {
				throw new DataBaseException("Falha na execução do comando SQL.");
			} catch (NullPointerException e) {
				throw new DataBaseException("A conexão com o Banco de Dados não foi efetuada.");
			}
			
		} catch (SQLException e1) {
			throw new DataBaseException("Falha na Criação de statement.");
		}
		
		
	}
	
	public void runSQL(String sql) throws DataBaseException {
		
		if(this.connection == null) {
			this.connect();
		}
		
		executeSQL(sql);
		
	}
	
	public ResultSet executeQuerySQL(String sql) throws DataBaseException {
		
		Statement stm;
		
		try {
			stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			this.rs = rs;
		} catch (SQLException e) {
			throw new DataBaseException("Falha na execução da Query.");
		}
		
		return rs;
				
	}
	
	public ResultSet runQuerySQL(String sql) throws DataBaseException {
		
		if(connection == null) {
			
			this.connect();
		 
		}
		
		this.rs = executeQuerySQL(sql);
		return rs;
		
	}
	
	public void closeConnection() throws DataBaseException {
		
		try {
			this.connection.close();
		} catch (SQLException e) {
			throw new DataBaseException("Falha ao fechar a conexão com o Banco de Dados.");
		} catch (NullPointerException e) {
			throw new DataBaseException("Conexão com Banco de Dados não efetuada.");
		}
		
	}
	
}
