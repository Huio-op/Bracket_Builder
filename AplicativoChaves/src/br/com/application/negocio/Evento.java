package br.com.application.negocio;

import br.univates.system32.CPF;

import java.time.LocalDate;

public class Evento {

    private int id;
    private String nome;
    private Jogo jogo;
    private CPF cpfOrg;
    private String descricao;
    private Double premio;
    private LocalDate data;

    public Evento(String nome, Jogo jogo, CPF cpf, String detalhes, Double premio, LocalDate data){

        this.nome = nome;
        this.jogo = jogo;
        this.cpfOrg = cpf;
        this.descricao = detalhes;
        this.premio = premio;
        this.data = data;

    }

    public Evento(int id, String nome, Jogo jogo, CPF cpf, String detalhes, Double premio, LocalDate data){

        this.id = id;
        this.nome = nome;
        this.jogo = jogo;
        this.cpfOrg = cpf;
        this.descricao = detalhes;
        this.premio = premio;
        this.data = data;

    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }

    public Jogo getJogo() { return jogo; }

    public CPF getCpfOrg() { return cpfOrg; }

    public String getDescricao() { return descricao; }

    public Double getPremio() { return premio; }

    public LocalDate getData() { return data; }
}
