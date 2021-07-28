package br.com.application.negocio;

import br.com.application.persistencia.DBPartPosition;
import br.univates.system32.DataBase.DataBaseException;

import java.util.ArrayList;

public class Participante {

    private int id;
    private String nome;
    private int idChave;
    private ArrayList<PartPosition> positions = new ArrayList<PartPosition>();

    private final DBPartPosition dbPartPosition = new DBPartPosition();

    public Participante(String nome, int idChave){

        this.nome = nome;
        this.idChave = idChave;

    }

    public Participante(int id, String nome, int idChave){

        this.id = id;
        this.nome = nome;
        this.idChave = idChave;

    }

    public void setNome( String nome ) { this.nome = nome; }

    public void setPositions(ArrayList<PartPosition> positions) { this.positions = positions; }

    public void addPosition(PartPosition position) {
        int contador = 0;
        for (PartPosition partPos : this.positions) {
            if (partPos.getPosCol() == position.getPosCol() && partPos.getPosLin() == position.getPosLin()) {
                partPos = position;
                contador++;
            }
        }
        if (contador == 0) {
            this.positions.add(position);
        }
    }

    public void removePosition(PartPosition position) { this.positions.remove(position); }

    public void cleanPositions() {
        try {
            dbPartPosition.deleteAllFromPart(this.id);
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
        this.positions = new ArrayList<PartPosition>();
    }

    public int getId() { return id; }

    public String getNome() { return nome; }

    public int getIdChave() { return idChave; }

    public ArrayList<PartPosition> getPositions() { return positions; }


    public PartPosition getPositionByMiniatureId(int id, int col) {
        for (PartPosition partPos : this.positions) {
            if (partPos.getPosLin() == id && partPos.getPosCol() == col) {
                return partPos;
            }
        }
        return null;
    }


}
