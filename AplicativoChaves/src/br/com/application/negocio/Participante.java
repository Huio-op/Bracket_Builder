package br.com.application.negocio;

public class Participante {

    private int id;
    private String nome;
    private int posicao;
    private int pontos;
    private int idChave;

    public Participante(String nome, int posicao, int pontos, int idChave){

        this.nome = nome;
        this.posicao = posicao;
        this.pontos = pontos;
        this.idChave = idChave;

    }

    public Participante(String nome,  int idChave){

        this.nome = nome;
        this.posicao = 0;
        this.pontos = 0;
        this.idChave = idChave;

    }

    public Participante(int id, String nome, int posicao, int pontos, int idChave){

        this.id = id;
        this.nome = nome;
        this.posicao = posicao;
        this.pontos = pontos;
        this.idChave = idChave;

    }

    public void setNome( String nome ) { this.nome = nome; }

    public void setPosicao( int posicao ) {
        this.posicao = posicao;
    }

    public void setPontos( int pontos ) {
        this.pontos = pontos;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getPosicao() {
        return posicao;
    }

    public int getPontos() {
        return pontos;
    }

    public int getIdChave() {
        return idChave;
    }
}
