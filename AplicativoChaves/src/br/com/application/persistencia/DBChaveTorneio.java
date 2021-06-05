package br.com.application.persistencia;

import br.com.application.negocio.ChaveTorneio;
import br.com.application.negocio.Evento;
import br.com.application.negocio.Jogo;
import br.com.application.persistencia.filters.ParticipanteFilterBracket;
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

public class DBChaveTorneio implements IDB<ChaveTorneio> {

    private DBConnection connection;

    public DBChaveTorneio(){

        this.connection = DBApp.getConnection();

    }

    @Override
    public void save(ChaveTorneio chave) throws DataBaseException, SQLException {

        if(chave != null){

            connection.runSQL("INSERT INTO chave_torneio (tipo, qtde_participantes, id_evento, comecou)" +
                    " VALUES( (SELECT id_tipos_torneio FROM tipos_torneio WHERE id_tipos_torneio = "+chave.getTipoTorneio()+"), "
                    + chave.getQuantidadeParticipantes() + ", " +
                    "(SELECT id_evento FROM evento WHERE id_evento = "+chave.getIdEvento()+"),"+
                    chave.isComecou() +");");

        }

    }

    @Override
    public ChaveTorneio load(String id) throws DataBaseException, SQLException {
        ChaveTorneio chaveTorneio = null;

        String sql = "SELECT * FROM chave_torneio WHERE id_chave = "+Integer.parseInt(id)+";";

        ResultSet rs = connection.runQuerySQL(sql);

        if(rs.isBeforeFirst()) {
            rs.next();
            int idChave = rs.getInt("id_chave");
            int idTipo = rs.getInt("tipo");
            int qtdeParticipantes = rs.getInt("qtde_participantes");
            int idEvento = rs.getInt("id_evento");
            boolean concluido = rs.getBoolean("comecou");

            chaveTorneio = new ChaveTorneio( idChave,idTipo,qtdeParticipantes,idEvento,concluido );
        }
        return chaveTorneio;
    }

    @Override
    public void delete(ChaveTorneio chave) throws DataBaseException {

        if(chave != null){
            connection.runSQL("DELETE FROM chave_torneio WHERE id_chave = " + chave.getId() + ";");
        }

    }

    @Override
    public void edit(ChaveTorneio chave) throws DataBaseException {

        if(chave != null){

            connection.runSQL("UPDATE chave_torneio SET tipo = "+ chave.getTipoTorneio() +", " +
                    "qtde_participantes = "+chave.getQuantidadeParticipantes()+"," +
                    "comecou = "+chave.isComecou()+";");

        }

    }

    @Override
    public ArrayList<ChaveTorneio> loadAll() throws DataBaseException, SQLException {
        ChaveTorneio chaveTorneio = null;
        ArrayList<ChaveTorneio> array = new ArrayList<ChaveTorneio>();

        String sql = "SELECT * FROM chave_torneio;";

        ResultSet rs = connection.runQuerySQL(sql);

        if(rs.isBeforeFirst()) {
            while(rs.next()) {


                int idChave = rs.getInt("id_chave");
                int idTipo = rs.getInt("tipo");
                int qtdeParticipantes = rs.getInt("qtde_participantes");
                int idEvento = rs.getInt("id_evento");
                boolean concluido = rs.getBoolean("comecou");

                chaveTorneio = new ChaveTorneio(idChave, idTipo, qtdeParticipantes, idEvento, concluido);
                array.add(chaveTorneio);
            }
        }
        return array;
    }

    @Override
    public ArrayList<ChaveTorneio> loadFiltered (Filter filter) throws SQLException, DataBaseException {
        ArrayList<ChaveTorneio> arrayFiltered = new ArrayList<>();
        final ArrayList<ChaveTorneio> arrayAllChaves = this.loadAll();

        for (ChaveTorneio chave: arrayAllChaves ) {
            if ( filter.isApproved(chave) ){
                arrayFiltered.add(chave);
            }
        }

        return arrayFiltered;

    }

    public boolean isFilled (ChaveTorneio chave) throws SQLException, DataBaseException {

        final DBParticipante dbParticipante = new DBParticipante();
        return dbParticipante.loadFiltered(new ParticipanteFilterBracket(chave)).size() == chave.getQuantidadeParticipantes();

    }

}
