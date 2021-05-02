package br.com.application.negocio;

import br.univates.system32.CPF;

import java.time.LocalDate;

public class Evento {

    private String nome;
    private Jogo jogo;
    private CPF cpfOrg;
    private String detalhes;
    private Double premio;
    private LocalDate data;

    public Evento(String nome, Jogo jogo, CPF cpf, String detalhes, Double premio, LocalDate data){

        this.nome = nome;
        this.jogo = jogo;
        this.cpfOrg = cpf;
        this.detalhes = detalhes;
        this.premio = premio;
        this.data = data;

    }

    public String getNome() { return nome; }

    public Jogo getJogo() { return jogo; }

    public CPF getCpfOrg() { return cpfOrg; }

    public String getDetalhes() { return detalhes; }

    public Double getPremio() { return premio; }

    public LocalDate getData() { return data; }
}
