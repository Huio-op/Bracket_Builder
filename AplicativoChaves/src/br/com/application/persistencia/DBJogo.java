package br.com.application.persistencia;

import br.com.application.negocio.Jogo;
import br.com.application.negocio.Usuario;
import br.univates.system32.DataBase.DBConnection;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.DataBase.IDB;

import java.io.FileNotFoundException;
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

            connection.runSQL("INSERT INTO jogo VALUES( '"+  jogo.getNome() +"',  '"+jogo.getImg()+"')");

        }

    }

    @Override
    public Jogo load(String nome) throws DataBaseException, SQLException {



        String sql = "SELECT * FROM jogo WHERE nome = '"+nome+"';";
        Jogo jogo = null;

        ResultSet rs = connection.runQuerySQL(sql);

        if(rs.isBeforeFirst()) {
            rs.next();
            String jogoNome = rs.getString("nome");
            byte[] img = rs.getBytes("img");

            try {
                jogo = new Jogo(jogoNome,img);
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
        return null;
    }

    public boolean checkNome(String nome) throws DataBaseException, SQLException {

        Boolean alreadyExists = false;

        String sql = "SELECT * FROM jogo WHERE email = '"+nome+"';";
        ResultSet rs = connection.runQuerySQL(sql);

        if(rs.isBeforeFirst()) {
            alreadyExists = true;
        }

        return alreadyExists;

    }
}
