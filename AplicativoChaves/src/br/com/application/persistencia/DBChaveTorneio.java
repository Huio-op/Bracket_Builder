package br.com.application.persistencia;

import br.com.application.negocio.ChaveTorneio;
import br.com.application.negocio.Evento;
import br.com.application.negocio.Jogo;
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

            connection.runSQL("INSERT INTO chave_torneio (tipo, qtde_participantes, id_evento, concluido)" +
                    " VALUES( (SELECT id_tipos_torneio FROM tipos_torneio WHERE id_tipos_torneio = "+chave.getTipoTorneio()+"), "
                    + chave.getQuantidadeParticipantes() + ", " +
                    "(SELECT id_evento FROM evento WHERE id_evento = "+chave.getIdEvento()+"),"+
                    chave.isConcluido() +");");

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
            boolean concluido = rs.getBoolean("concluido");

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
                boolean concluido = rs.getBoolean("concluido");

                chaveTorneio = new ChaveTorneio(idChave, idTipo, qtdeParticipantes, idEvento, concluido);
                array.add(chaveTorneio);
            }
        }
        return array;
    }

    @Override
    public ArrayList<ChaveTorneio> loadFiltered(Filter filter) throws SQLException, DataBaseException {
        return null;
    }
}
