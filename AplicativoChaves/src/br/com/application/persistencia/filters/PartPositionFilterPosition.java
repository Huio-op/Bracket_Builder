package br.com.application.persistencia.filters;

import br.com.application.negocio.PartPosition;
import br.univates.system32.DataBase.Filter;

public class PartPositionFilterPosition implements Filter<PartPosition> {

    private int position;

    public PartPositionFilterPosition(int position) {
        this.position = position;
    }

    @Override
    public boolean isApproved(PartPosition partPos) {
        if (partPos.getPosicao() == this.position) {
            return true;
        } else {
            return false;
        }
    }
}
