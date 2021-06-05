package br.com.application.persistencia.filters;

import br.com.application.negocio.ChaveTorneio;
import br.univates.system32.DataBase.Filter;

public class ChaveFilterEvento implements Filter<ChaveTorneio> {

    private int idEvento;

    public ChaveFilterEvento( int idEvento ) {
        this.idEvento = idEvento;
    }

    @Override
    public boolean isApproved(ChaveTorneio chaveTorneio) {
        if (chaveTorneio.getIdEvento() == this.idEvento) {
            return true;
        } else {
            return false;
        }
    }
}
