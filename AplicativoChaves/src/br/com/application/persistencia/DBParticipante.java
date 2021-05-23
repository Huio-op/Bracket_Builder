package br.com.application.persistencia;

import br.com.application.negocio.Evento;
import br.com.application.negocio.Jogo;
import br.com.application.negocio.Participante;
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

    public DBParticipante(){

        this.connection = DBApp.getConnection();

    }

    @Override
    public void save(Participante participante) throws DataBaseException, SQLException {

        if(participante != null){

            connection.runSQL("INSERT INTO participante (nome, posicao, pontos, id_chave)" +
                    " VALUES( '" + participante.getNome() + "', " +participante.getPosicao() +", "+participante.getPontos()+
                    "(SELECT id_chave FROM chave_torneio WHERE id_chave = '"+participante.getIdChave()+");");

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
            int posicao = rs.getInt("posicao");
            int pontos = rs.getInt("pontos");
            int idChave = rs.getInt("id_chave");

            participante = new Participante( id,nome,posicao,pontos,idChave);

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
    public void edit(Participante participante) throws DataBaseException {

        if(participante != null){

            connection.runSQL("UPDATE participante SET nome = '"+ participante.getNome() +"', " +
                    "posicao = "+participante.getPosicao()+"," + "pontos = "+participante.getPontos()+";");

        }


    }

    @Override
    public ArrayList<Participante> loadAll() throws DataBaseException, SQLException {

        ArrayList<Participante> array = new ArrayList<Participante>();
        String sql = "SELECT * FROM participante;";
        Participante participante = null;

        ResultSet rs = connection.runQuerySQL(sql);

        if(rs.isBeforeFirst()) {
            rs.next();
            int id = rs.getInt("id_participante");
            String nome = rs.getString("nome");
            int posicao = rs.getInt("posicao");
            int pontos = rs.getInt("pontos");
            int idChave = rs.getInt("id_chave");

            participante = new Participante( id,nome,posicao,pontos,idChave);
            array.add(participante);

        }

        return array;

    }


    @Override
    public ArrayList<Participante> loadFiltered(Filter filter) throws SQLException, DataBaseException {
        return null;
    }
}
