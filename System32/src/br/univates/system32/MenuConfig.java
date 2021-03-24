package br.univates.system32;

public interface MenuConfig
{
    public int getNumItens();
    public char getAtalho(int i);
    public String getDesc(int i);
    public void executar(int op);
    public String getTitulo();
    public char getAtalhoSair();
    public String getDescSair();
    public boolean isRepetir();
}

