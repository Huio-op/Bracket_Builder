package br.com.application.persistencia;

import br.com.application.negocio.Jogo;
import br.com.application.negocio.Usuario;
import br.univates.system32.DataBase.DBConnection;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.DataBase.Filter;
import br.univates.system32.DataBase.IDB;

import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBJogo implements IDB<Jogo> {

    private DBConnection connection;

    public DBJogo(){

        this.connection = DBApp.getConnection();

    }

    @Override
    public void save(Jogo jogo) throws DataBaseException, SQLException {

        if(jogo != null){

            PreparedStatement ps = connection.getConnection().prepareStatement("INSERT INTO jogo (nome,image) VALUES( ? , ? )");
            ps.setString(1, jogo.getNome());
            ps.setBytes(2, jogo.getImg());

            ps.executeUpdate();
            ps.close();

        }

    }

    @Override
    public Jogo load(String idJogo) throws DataBaseException, SQLException {



        String sql = "SELECT * FROM jogo WHERE id_jogo = "+Integer.parseInt(idJogo)+";";
        Jogo jogo = null;

        ResultSet rs = connection.runQuerySQL(sql);

        if(rs.isBeforeFirst()) {
            rs.next();
            int id = rs.getInt("id_jogo");
            String jogoNome = rs.getString("nome");
            byte[] img = rs.getBytes("image");

            try {
                jogo = new Jogo( id,jogoNome,img);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }

        return jogo;

    }

    @Override
    public void delete(Jogo object) throws DataBaseException {

    }

    @Override
    public void edit(Jogo object) throws DataBaseException {

    }

    @Override
    public ArrayList<Jogo> loadAll() throws DataBaseException, SQLException {

        ArrayList<Jogo> array = new ArrayList<Jogo>();

        String sql = "SELECT * FROM jogo";
        ResultSet rs = connection.runQuerySQL(sql);

        if(rs.isBeforeFirst()) {
            while(rs.next()){

                int id = rs.getInt("id_jogo");
                String jogoNome = rs.getString("nome");
                byte[] img = rs.getBytes("image");

                try {
                    Jogo jogo = new Jogo(id,jogoNome,img);
                    array.add(jogo);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }

        }

        return array;

    }

    @Override
    public ArrayList<Jogo> loadFiltered(Filter filter) {
        return null;
    }

    public boolean checkNome(String nome) throws DataBaseException, SQLException {

        Boolean alreadyExists = false;

        String sql = "SELECT * FROM jogo WHERE nome = '"+nome+"';";
        ResultSet rs = connection.runQuerySQL(sql);

        if(rs.isBeforeFirst()) {
            alreadyExists = true;
        }

        return alreadyExists;

    }
}
