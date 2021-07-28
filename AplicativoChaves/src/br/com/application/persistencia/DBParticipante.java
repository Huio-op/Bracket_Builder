package br.com.application.persistencia;

import br.com.application.negocio.Evento;
import br.com.application.negocio.Jogo;
import br.com.application.negocio.PartPosition;
import br.com.application.negocio.Participante;
import br.com.application.persistencia.filters.PartPositionFilterParticipante;
import br.univates.system32.CPF;
import br.univates.system32.DataBase.DBConnection;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.DataBase.Filter;
import br.univates.system32.DataBase.IDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DBParticipante implements IDB<Participante> {

    private DBConnection connection;
    private DBPartPosition dbPartPosition = new DBPartPosition();

    public DBParticipante(){
        this.connection = DBApp.getConnection();
    }

    @Override
    public void save(Participante participante) throws DataBaseException, SQLException {

        if(participante != null){

            connection.runSQL("INSERT INTO participante (nome, id_chave)" +
                    " VALUES( '" + participante.getNome() + "'," +
                    "(SELECT id_chave FROM chave_torneio WHERE id_chave = "+participante.getIdChave()+"));");

        }

        if (!participante.getPositions().isEmpty()) {
            for (PartPosition partPos : participante.getPositions()) {
                dbPartPosition.save(partPos);
            }

        }

    }

    @Override
    public Participante load(String idParticipante) throws DataBaseException, SQLException {

        String sql = "SELECT * FROM participante WHERE id_participante= "+Integer.parseInt(idParticipante)+";";
        Participante participante = null;

        ResultSet rs = connection.runQuerySQL(sql);

        if(rs.isBeforeFirst()) {
            rs.next();
            int id = rs.getInt("id_participante");
            String nome = rs.getString("nome");
            int idChave = rs.getInt("id_chave");

            participante = new Participante(id, nome, idChave);

            participante.setPositions(dbPartPosition.loadFiltered(new PartPositionFilterParticipante(Integer.parseInt(idParticipante))));

        }

        return participante;

    }

    @Override
    public void delete(Participante participante) throws DataBaseException {

        if(participante != null){

            connection.runSQL("DELETE FROM participante WHERE id_participante = " + participante.getId() + ";");

        }

    }

    @Override
    public void edit(Participante participante) throws DataBaseException, SQLException {

        if (participante != null){

            connection.runSQL("UPDATE participante SET nome = '"+ participante.getNome() +"' " +
                    "WHERE id_participante = " + participante.getId() + ";");

        }

        if (!participante.getPositions().isEmpty()) {
            for (PartPosition partPos : participante.getPositions()) {
                dbPartPosition.addOrEdit(partPos);
            }

        }

    }

    @Override
    public ArrayList<Participante> loadAll() throws DataBaseException, SQLException {

        ArrayList<Participante> array = new ArrayList<Participante>();
        String sql = "SELECT * FROM participante;";
        Participante participante = null;

        ResultSet rs = connection.runQuerySQL(sql);

        if(rs.isBeforeFirst()) {
            while(rs.next()){
                int id = rs.getInt("id_participante");
                String nome = rs.getString("nome");
                int idChave = rs.getInt("id_chave");

                participante = new Participante(id, nome, idChave);
                participante.setPositions(dbPartPosition.loadFiltered(new PartPositionFilterParticipante(id)));
                array.add(participante);
            }
        }
        return array;
    }

    @Override
    public ArrayList<Participante> loadFiltered(Filter filter) throws SQLException, DataBaseException {
        ArrayList<Participante> arrayFiltered = new ArrayList<Participante>();
        ArrayList<Participante> array = this.loadAll();

        for (Participante participante: array) {
            if(filter.isApproved(participante)){
                arrayFiltered.add(participante);
            }
        }

        return arrayFiltered;
    }
}
