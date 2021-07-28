package br.com.application.negocio;

public class PartPosition {

    private int idPart;
    private int posCol;
    private int posLin;
    private int pontos;

    public PartPosition(int idPart, int posCol, int posLin, int pontos) {
        this.idPart = idPart;
        this.posCol = posCol;
        this.posLin = posLin;
        this.pontos = pontos;
    }

    public int getIdPart() {
        return idPart;
    }

    public void setIdPart(int idPart) {
        this.idPart = idPart;
    }

    public int getPosCol() {
        return posCol;
    }

    public void setPosCol(int posCol) {
        this.posCol = posCol;
    }

    public int getPosLin() {
        return this.posLin;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
}
