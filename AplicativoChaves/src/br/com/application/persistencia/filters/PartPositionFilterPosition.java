package br.com.application.persistencia.filters;

import br.com.application.negocio.PartPosition;
import br.univates.system32.DataBase.Filter;

public class PartPositionFilterPosition implements Filter<PartPosition> {

    private int posCol;
    private int posLin;

    public PartPositionFilterPosition(int posCol, int posLin) {
        this.posCol = posCol;
        this.posLin = posLin;
    }

    @Override
    public boolean isApproved(PartPosition partPos) {
        if (partPos.getPosCol() == this.posCol && partPos.getPosLin() == this.posLin) {
            return true;
        } else {
            return false;
        }
    }
}
