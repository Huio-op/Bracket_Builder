package br.com.application.negocio;

import java.util.ArrayList;

public class Participante {

    private int id;
    private String nome;
    private int posicao;
    private int pontos;
    private int idChave;
    private ArrayList<PartPosition> positions = new ArrayList<PartPosition>();

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

    public void setPontos( int pontos ) { this.pontos = pontos; }

    public void setPositions(ArrayList<PartPosition> positions) { this.positions = positions; }

    public void addPosition(PartPosition position) {
        int contador = 0;
        for (PartPosition partPos : this.positions) {
            if (partPos.getPosicao() == position.getPosicao()) {
                partPos = position;
                contador++;
            }
        }
        if (contador == 0) {
            this.positions.add(position);
        }

    }

    public void removePosition(PartPosition position) { this.positions.remove(position); }

    public int getId() { return id; }

    public String getNome() { return nome; }

    public int getPosicao() { return posicao; }

    public int getPontos() { return pontos; }

    public int getIdChave() { return idChave; }

    public ArrayList<PartPosition> getPositions() { return positions; }


}
