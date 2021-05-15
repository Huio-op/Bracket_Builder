package br.com.application.negocio;

public class ChaveTorneio {

    private int id;
    private int tipoTorneio;
    private int quantidadeParticipantes;
    private int idEvento;
    private boolean concluido;

    public ChaveTorneio(int tipoTorneio, int quantidadeParticipantes, int idEvento){

        this.tipoTorneio = tipoTorneio;
        this.quantidadeParticipantes = quantidadeParticipantes;
        this.idEvento = idEvento;

    }

    public ChaveTorneio(int id, int tipoTorneio, int quantidadeParticipantes, int idEvento, boolean concluido){

        this.id = id;
        this.tipoTorneio = tipoTorneio;
        this.quantidadeParticipantes = quantidadeParticipantes;
        this.idEvento = idEvento;
        this.concluido = concluido;

    }

    public int getId() { return id; }

    public int getTipoTorneio() { return tipoTorneio; }

    public int getQuantidadeParticipantes() { return quantidadeParticipantes; }

    public int getIdEvento() { return idEvento; }

    public boolean isConcluido() { return concluido; }

    public void setTipoTorneio(int tipoTorneio) { this.tipoTorneio = tipoTorneio; }

    public void setQuantidadeParticipantes(int quantidadeParticipantes) { this.quantidadeParticipantes = quantidadeParticipantes; }

    public void setConcluido(boolean concluido) { this.concluido = concluido; }
}
