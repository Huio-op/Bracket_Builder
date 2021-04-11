package br.com.application.negocio;

public class Usuario {

	private String email;
	private String nome;
	private String senha;
	
	public Usuario(String email, String nome, String senha) {
		this.email = email;
		this.nome = nome;
		this.senha = senha;
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

	
}
