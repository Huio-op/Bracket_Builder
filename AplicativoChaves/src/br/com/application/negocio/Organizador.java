package br.com.application.negocio;

import br.univates.system32.CPF;

public class Organizador extends Usuario{

	private CPF cpf;
	private int evetnos_realizados;
	private int nota;
	private String nacionalidade;
	
	protected Organizador(CPF cpf, String email,String nacionalidade, String nome, String senha) {

		super(email, nome,senha);
		this.cpf = cpf;
		this.nacionalidade = nacionalidade;
		this.evetnos_realizados = 0;
		this.nota = 0;
		
	}

	public Organizador(CPF cpf, String email,String nacionalidade, String nome, String senha, int evetnos_realizados, int nota) {

		super(email, nome,senha);
		this.cpf = cpf;
		this.nacionalidade = nacionalidade;
		this.evetnos_realizados = evetnos_realizados;
		this.nota = nota;

	}

	public CPF getCpf() {
		return cpf;
	}

	public void setCpf(CPF cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return this.nome;
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

	public String getNacionalidade(){ return this.nacionalidade;}

	public int getEvetnos_realizados() {return this.evetnos_realizados;}
	
	public int getNota() {return this.nota;}
	
}
