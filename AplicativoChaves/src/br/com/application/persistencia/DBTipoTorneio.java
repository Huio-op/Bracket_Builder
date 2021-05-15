package br.com.application.persistencia;

import br.com.application.negocio.Evento;
import br.com.application.negocio.Jogo;
import br.com.application.negocio.TipoTorneio;
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

public class DBTipoTorneio implements IDB<TipoTorneio> {

    private DBConnection connection;
    private DBEvento dbEvento;

    public DBTipoTorneio(){
        this.connection = DBApp.getConnection();
    }

    @Override
    public void save(TipoTorneio tipoTorneio) throws DataBaseException, SQLException {

        if(tipoTorneio != null){
            connection.runSQL("INSERT INTO tipos_torneio (nome) VALUES( '"+ tipoTorneio.getNome() +"' );");
        }

    }

    @Override
    public TipoTorneio load(String id) throws DataBaseException, SQLException {

        String sql = "SELECT * FROM tipos_torneio WHERE id_tipos_torneio = "+Integer.parseInt(id)+";";
        TipoTorneio tipoTorneio = null;

        ResultSet rs = connection.runQuerySQL(sql);

        if(rs.isBeforeFirst()) {
            rs.next();
            int idLoaded = rs.getInt("id_tipos_torneio");
            String nome = rs.getString("nome");

            tipoTorneio = new TipoTorneio( idLoaded,nome );
        }
        return tipoTorneio;
    }

    @Override
    public void delete(TipoTorneio tipoTorneio) throws DataBaseException {

        if(tipoTorneio != null){
            connection.runSQL("DELETE FROM tipos_torneio WHERE id_tipos_torneio = " + tipoTorneio.getId() + ";");
        }

    }

    @Override
    public void edit(TipoTorneio tipoTorneio) throws DataBaseException {

        if(tipoTorneio != null){
            connection.runSQL("UPDATE tipos_torneio SET nome = '"+ tipoTorneio.getNome() +"';");
        }

    }

    @Override
    public ArrayList<TipoTorneio> loadAll() throws DataBaseException, SQLException {

        ArrayList<TipoTorneio> array = new ArrayList<TipoTorneio>();

        String sql = "SELECT * FROM tipos_torneio;";
        TipoTorneio tipoTorneio;

        ResultSet rs = connection.runQuerySQL(sql);

        if(rs.isBeforeFirst()) {
            while (rs.next()){
                int id = rs.getInt("id_tipos_torneio");
                String nome = rs.getString("nome");

                tipoTorneio = new TipoTorneio( id,nome );
                array.add(tipoTorneio);
            }
        }

        return array;
    }

    @Override
    public ArrayList<TipoTorneio> loadFiltered(Filter filter) throws SQLException, DataBaseException {
        return null;
    }
}
