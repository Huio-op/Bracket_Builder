package br.com.application.negocio;

public class PartPosition {

    private int idPart;
    private int posicao;
    private int pontos;

    public PartPosition(int idPart, int posicao, int pontos) {
        this.idPart = idPart;
        this.posicao = posicao;
        this.pontos = pontos;
    }

    public int getIdPart() {
        return idPart;
    }

    public void setIdPart(int idPart) {
        this.idPart = idPart;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }
}
