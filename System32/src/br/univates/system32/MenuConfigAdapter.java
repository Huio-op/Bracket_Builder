package br.univates.system32;

public class MenuConfigAdapter implements MenuConfig{

	@Override
	public int getNumItens() {
		return 0;
	}

	@Override
	public char getAtalho(int i) {
		String aux = (i+1)+"";
		return aux.charAt(0);
	}

	@Override
	public String getDesc(int i) {
		return null;
	}

	@Override
	public void executar(int op) {
		
	}

	@Override
	public String getTitulo() {
		return "*********M E N U*********";
	}

	@Override
	public char getAtalhoSair() {
		return 'x';
	}

	@Override
	public String getDescSair() {
		return "Sair do Sistema.";
	}

	@Override
	public boolean isRepetir() {
		return true;
	}

}
