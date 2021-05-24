package br.com.application.persistencia.filters;

import br.com.application.negocio.ChaveTorneio;
import br.com.application.negocio.Evento;
import br.com.application.negocio.Organizador;
import br.com.application.negocio.Participante;
import br.univates.system32.DataBase.Filter;

public class ParticipanteFilterBracket implements Filter<Participante> {
    private ChaveTorneio chave;

    public ParticipanteFilterBracket(ChaveTorneio chave){

        this.chave = chave;

    }

    @Override
    public boolean isApproved(Participante participante) {
        if(participante.getIdChave() == this.chave.getId()){
            return true;
        }else{
            return false;
        }
    }
}
