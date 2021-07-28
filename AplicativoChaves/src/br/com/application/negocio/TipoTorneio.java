package br.com.application.negocio;

public class TipoTorneio {

    private int id;
    private String nome;

    public TipoTorneio(String nome){

        this.nome = nome;

    }

    public TipoTorneio(int id,String nome){
        this.id = id;
        this.nome = nome;
    }

    public int getId() { return id; }

    public String getNome() { return nome; }

}
