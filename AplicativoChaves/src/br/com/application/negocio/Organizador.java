package br.com.application.negocio;

import br.univates.system32.CPF;

public class Organizador {

	private CPF cpf;
	private String nome;
	private String senha;
	
	public Organizador(CPF cpf, String nome, String senha) {
		
		this.cpf = cpf;
		this.nome = nome;
		this.senha = senha;
		
	}

	public CPF getCpf() {
		return cpf;
	}

	public void setCpf(CPF cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	
	
}
