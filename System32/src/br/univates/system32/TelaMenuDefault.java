package br.univates.system32;

public class TelaMenuDefault {

	private MenuConfig config;
	
	public TelaMenuDefault(MenuConfig config) {
		this.config = config;
	}
	
	public void exibir() {
		
		Menu m = new Menu(config);
		
		m.gerar();
	}
	
	public void fechar() {
		System.exit(0);
	}
	
}
