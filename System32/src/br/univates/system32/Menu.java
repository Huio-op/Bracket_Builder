package br.univates.system32;

import br.univates.system32.Entrada;

public class Menu
{
    private MenuConfig config;

    public Menu(MenuConfig config)
    {
        this.config = config;
    }
    
    public void gerar()
    {
    	String m = "MENU";
    	if(!config.getTitulo().isEmpty()) {
    		m = config.getTitulo() + "\n\n";
    	}
        
        for (int i = 0; i < config.getNumItens(); i++)
        {
            m += "["+config.getAtalho(i)+"] "+config.getDesc(i)+"\n";
        }
        if(config.isRepetir()) {
        
	        String sair = "[x] - Sair";
	        
	        if(!config.getDescSair().isEmpty()) {
	        	sair = "["+ config.getAtalhoSair() +"]"+ config.getDescSair();
	        }
	        
	        m += sair;
        }
        
        char op = ' ';
        
        while (op != 'x')
        {
            op = Entrada.leiaChar(m);
            
            for (int i = 0; i < config.getNumItens(); i++)
            {
                if (op == config.getAtalho(i))
                {
                    config.executar(i);
                    break;
                }
            }
            if(!config.isRepetir()) {
            	op = 'x';
            }
        }
    }
}