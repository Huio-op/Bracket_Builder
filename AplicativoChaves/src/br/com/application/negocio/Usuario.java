package br.com.application.negocio;

import br.com.application.persistencia.DBUsuarios;
import br.univates.system32.CPF;
import br.univates.system32.DataBase.DataBaseException;

import java.util.Locale;

public class Usuario {

	protected String email;
	protected String nome;
	protected String senha;
	protected boolean organizador;
	
	public Usuario(String email, String nome, String senha) {
		this.email = email;
		this.nome = nome;
		this.senha = senha;
		this.organizador = false;
	}

	public Usuario(String email, String nome, String senha, boolean isOrganizador) {
		this.email = email;
		this.nome = nome;
		this.senha = senha;
		this.organizador = isOrganizador;
	}

	public Organizador turnOrganizador(CPF cpf, String nacionalidade) throws DataBaseException {

		Organizador o = new Organizador(cpf, this.email,nacionalidade,this.nome,this.senha);
		this.organizador = true;

		DBUsuarios dbu = new DBUsuarios();

		dbu.edit(this);

		return o;

	}

	public String getEmail() {
		return email;
	}

	public String getNome() {
		return nome;
	}

	public String getSenha() {
		return senha;
	}

	public String getFirstName(){

		String[] firstName = this.nome.split(" ");
		return firstName[0];

	}

	public boolean isOrganizador(){
		return this.organizador;
	}

	
}
