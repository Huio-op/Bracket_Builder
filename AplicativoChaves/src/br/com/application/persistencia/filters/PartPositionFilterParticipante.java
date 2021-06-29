package br.com.application.persistencia.filters;

import br.com.application.negocio.ChaveTorneio;
import br.com.application.negocio.PartPosition;
import br.com.application.negocio.Participante;
import br.univates.system32.DataBase.Filter;

public class PartPositionFilterParticipante implements Filter<PartPosition> {

    private int idPart;

    public PartPositionFilterParticipante (int idPart) {
        this.idPart = idPart;
    }

    @Override
    public boolean isApproved(PartPosition partPosition) {
        if (partPosition.getIdPart() == idPart) {
            return true;
        } else {
            return false;
        }
    }

}
