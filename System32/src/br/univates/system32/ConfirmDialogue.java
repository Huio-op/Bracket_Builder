package br.univates.system32;

public class ConfirmDialogue {

	private boolean confirma;
	private String questionamento;
	
	public ConfirmDialogue(String questao) {
		this.questionamento = questao;
		this.confirma = false;
	}
	
	public boolean isConfirmado() {
		
		Menu m = new Menu( new MenuConfig () {

			@Override
			public int getNumItens() {
				return 2;
			}

			@Override
			public char getAtalho(int i) {
				char[] at = {'S', 'N'};
				return at[i];
			}

			@Override
			public String getDesc(int i) {
				String[] desc = {"Sim", "Não"};
				return desc[i];
			}

			@Override
			public void executar(int op) {
			
				if(op == 0) {
					confirma = true;
				}else {
					confirma = false;
				}
				
			}

			@Override
			public String getTitulo() {
				return questionamento;
			}

			@Override
			public char getAtalhoSair() {
				return ' ';
			}

			@Override
			public String getDescSair() {
				return "";
			}

			@Override
			public boolean isRepetir() {
				return false;
			}
			
		});
		m.gerar();
		return confirma;
		
	}
	
}
