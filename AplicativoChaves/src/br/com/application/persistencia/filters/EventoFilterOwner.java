package br.com.application.persistencia.filters;

import br.com.application.negocio.Evento;
import br.com.application.negocio.Organizador;
import br.com.application.negocio.Usuario;
import br.univates.system32.DataBase.Filter;

public class EventoFilterOwner implements Filter<Evento> {

    private Organizador org;

    public EventoFilterOwner(Organizador org){

        this.org = org;

    }

    @Override
    public boolean isApproved(Evento evt) {
        if(evt.getCpfOrg().getCPFStringNoChars().equals(org.getCpf().getCPFStringNoChars())){

            return true;

        }else{
            return false;
        }
    }
}
