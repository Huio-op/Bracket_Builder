package br.com.application.persistencia;

import br.com.application.negocio.Evento;
import br.univates.system32.DataBase.DBConnection;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.DataBase.IDB;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.util.ArrayList;

public class DBEvento implements IDB<Evento> {

    private DBConnection connection;

    public DBEvento(){

        this.connection = DBApp.getConnection();

    }

    @Override
    public void save(Evento evento) throws DataBaseException, SQLException {

        if(evento != null){

            String date = evento.getData().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

            connection.runSQL("INSERT INTO evento (nome, jogo, cpf_organizador, detalhes, premio, data)" +
                    " VALUES( '" + evento.getNome() + "', " +
                    "(SELECT id_jogo FROM jogo WHERE id_jogo = "+evento.getJogo().getIdJogo()+"), " +
                    "(SELECT cpf FROM organizador WHERE cpf = '"+evento.getCpfOrg().getCPFStringNoChars()+"'), " +
                    "'"+evento.getDetalhes()+"', "+evento.getPremio()+", '"+date+"');");

        }

    }

    @Override
    public Evento load(String key) throws DataBaseException, SQLException {
        return null;
    }

    @Override
    public void delete(Evento evento) throws DataBaseException {

    }

    @Override
    public void edit(Evento object) throws DataBaseException {

    }

    @Override
    public ArrayList<Evento> loadAll() throws DataBaseException, SQLException {
        return null;
    }
}
