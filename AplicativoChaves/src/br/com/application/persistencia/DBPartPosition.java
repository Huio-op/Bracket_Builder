package br.com.application.persistencia;

import br.com.application.negocio.Evento;
import br.com.application.negocio.PartPosition;
import br.com.application.negocio.Participante;
import br.com.application.persistencia.filters.PartPositionFilterParticipante;
import br.com.application.persistencia.filters.PartPositionFilterPosition;
import br.univates.system32.DataBase.DBConnection;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.DataBase.Filter;
import br.univates.system32.DataBase.IDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DBPartPosition implements IDB<PartPosition> {

    private DBConnection connection;

    public DBPartPosition() {
        this.connection = DBApp.getConnection();
    }

    @Override
    public void save(PartPosition partPos) throws DataBaseException, SQLException {

        connection.runSQL("INSERT INTO part_pos VALUES( " + partPos.getIdPart() + "" +
                ", " + partPos.getPosicao() + ", " + partPos.getPontos() + ");");

    }

    @Override
    public PartPosition load(String key) throws DataBaseException, SQLException {
        return null;
    }

    @Override
    public void delete(PartPosition partPos) throws DataBaseException {

        if(partPos != null){

            connection.runSQL("DELETE FROM part_pos WHERE id_participante = " + partPos.getIdPart() + " AND posicao" + partPos.getPosicao()  + ";");

        }

    }

    @Override
    public void edit(PartPosition partPos) throws DataBaseException, SQLException {

        if(partPos != null){

            connection.runSQL("UPDATE part_pos SET pontos = "+ partPos.getPontos() +
                    "WHERE id_participante = " + partPos.getIdPart() + " AND posicao = " + partPos.getPosicao() + ";");

        }

    }

    @Override
    public ArrayList<PartPosition> loadAll() throws DataBaseException, SQLException {

        ArrayList<PartPosition> arrayPartPos = new ArrayList<>();
        String sql = "SELECT * FROM part_pos;";
        PartPosition partPos = null;

        ResultSet rs = connection.runQuerySQL(sql);

        if(rs.isBeforeFirst()) {
            while(rs.next()){
                int id = rs.getInt("id_participante");
                int posicao = rs.getInt("posicao");
                int pontos = rs.getInt("pontos");

                partPos = new PartPosition( id,posicao,pontos);
                arrayPartPos.add(partPos);
            }
        }
        return arrayPartPos;

    }

    @Override
    public ArrayList<PartPosition> loadFiltered(Filter filter) throws SQLException, DataBaseException {

        ArrayList<PartPosition> arrayFiltered = new ArrayList<PartPosition>();
        ArrayList<PartPosition> array = this.loadAll();

        for (PartPosition partPos: array) {
            if(filter.isApproved(partPos)){
                arrayFiltered.add(partPos);
            }
        }

        return arrayFiltered;

    }

    public ArrayList<PartPosition> loadMultiFiltered(ArrayList<Filter> filterArray) throws SQLException, DataBaseException {

        ArrayList<PartPosition> arrayFiltered = null;
        ArrayList<PartPosition> array = this.loadAll();

        for(Filter filter : filterArray){
            arrayFiltered = new ArrayList<PartPosition>();
            for (PartPosition partPos: array) {
                if(filter.isApproved(partPos)){
                    arrayFiltered.add(partPos);
                }
            }
            array = arrayFiltered;
        }

        return arrayFiltered;

    }

    public void addOrEdit (PartPosition partPos) throws SQLException, DataBaseException {

        ArrayList<Filter> filterArray = new ArrayList<>();
        filterArray.add(new PartPositionFilterParticipante(partPos.getIdPart()));
        filterArray.add(new PartPositionFilterPosition(partPos.getPosicao()));
        ArrayList<PartPosition> array = loadMultiFiltered(filterArray);

        if (!array.isEmpty()) {
            this.edit(partPos);
        } else {
            this.save(partPos);
        }

    }

}
