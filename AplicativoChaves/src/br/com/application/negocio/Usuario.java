package br.com.application.negocio;

import br.univates.system32.CPF;

import java.util.Locale;

public class Usuario {

	protected String email;
	protected String nome;
	protected String senha;
	protected boolean isOrganizador;
	
	public Usuario(String email, String nome, String senha) {
		this.email = email;
		this.nome = nome;
		this.senha = senha;
		this.isOrganizador = false;
	}

	public void turnOrganizador(CPF cpf, String nacionalidade){

		Organizador o = new Organizador(cpf, this.email,nacionalidade,this.nome,this.senha);

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
		return this.isOrganizador;
	}

	
}
