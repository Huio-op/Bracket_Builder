package br.com.application.persistencia.filters;

import br.com.application.negocio.Evento;
import br.univates.system32.DataBase.Filter;

public class EventoFilterJogo implements Filter<Evento> {


    private String idJogo;

    public EventoFilterJogo(String idJogo){

        this.idJogo = idJogo;

    }

    @Override
    public boolean isApproved(Evento evt) {
        if(evt.getJogo().getIdJogo() == Integer.parseInt(idJogo)){

            return true;

        }else{
            return false;
        }
    }
}

