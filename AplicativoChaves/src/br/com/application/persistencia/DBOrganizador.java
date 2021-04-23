package br.com.application.persistencia;

import br.com.application.Main;
import br.com.application.negocio.Organizador;
import br.com.application.negocio.Usuario;
import br.univates.system32.CPF;
import br.univates.system32.DataBase.DBConnection;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.DataBase.IDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBOrganizador implements IDB<Organizador> {

    private DBConnection connection;

    public DBOrganizador() throws DataBaseException {

        this.connection = DBApp.getConnection();

//        this.connection = new DBConnection("wnlrmkyd", "RrqNt3iigJVgBsFIPOMi3KurycXoU7cD", "wnlrmkyd"
//                , "motty.db.elephantsql.com", "5432");

    }

    @Override
    public void save(Organizador object) throws DataBaseException, SQLException {

        if(object != null) {

            connection.runSQL("INSERT INTO organizador VALUES( '" + object.getCpf().getCPFStringNoChars() + "', " +
                    "(SELECT email FROM usuario WHERE email = '"+object.getEmail()+"'), '"+object.getNacionalidade()+"', " +
                    ""+object.getEvetnos_realizados()+", "+object.getNota()+");");

        }

    }

    @Override
    public Organizador load(String cpfOrg) throws DataBaseException, SQLException {
        String sql = "SELECT * FROM organizador WHERE cpf = '"+cpfOrg+"';";
        Organizador o = null;

        ResultSet rs = connection.runQuerySQL(sql);

        if(rs.isBeforeFirst()) {
            rs.next();
            CPF cpf = new CPF();
            cpf.setCPF(rs.getString("cpf"));
            String orgEmail = rs.getString("email");
            String nacionalidade = rs.getString("nacionalidade");

            String sqlUsu = "SELECT * FROM usuario WHERE email = '"+orgEmail+"';";
            ResultSet rsUsu = connection.runQuerySQL(sqlUsu);
            String nome = null;
            String senha = null;
            if(rsUsu.isBeforeFirst()){
                rs.next();
                nome = rs.getString("nome");
                senha = rs.getString("senha");
            }

            int eventosRealizados = rs.getInt("eventos_realizados");
            int nota = rs.getInt("nota");

            o = new Organizador(cpf,orgEmail,nacionalidade, nome, senha, eventosRealizados, nota);

        }

        return o;
    }

    public Organizador loadFromEmail(String email) throws DataBaseException, SQLException {
        String sql = "SELECT * FROM organizador WHERE email = '"+email+"';";
        Organizador o = null;

        ResultSet rs = connection.runQuerySQL(sql);

        if(rs.isBeforeFirst()) {
            rs.next();
            CPF cpf = new CPF();
            cpf.setCPF(rs.getString("cpf"));
            String orgEmail = rs.getString("email");
            String nacionalidade = rs.getString("nacionalidade");

            String sqlUsu = "SELECT * FROM usuario WHERE email = '"+orgEmail+"';";
            ResultSet rsUsu = connection.runQuerySQL(sqlUsu);
            String nome = null;
            String senha = null;
            if(rsUsu.isBeforeFirst()){
                rsUsu.next();
                nome = rsUsu.getString("nome");
                senha = rsUsu.getString("senha");
            }

            int eventosRealizados = rs.getInt("eventos_realizados");
            int nota = rs.getInt("nota");

            o = new Organizador(cpf,orgEmail,nacionalidade, nome, senha, eventosRealizados, nota);

        }

        return o;
    }

    @Override
    public void delete(Organizador org) throws DataBaseException {

        connection.runSQL("DELETE FROM organizador WHERE email = '" + org.getEmail() + "';");

    }

    public void deleteFromEmail(String email) throws DataBaseException {

        connection.runSQL("DELETE FROM organizador WHERE email = '" + email + "';");

    }

    @Override
    public void edit(Organizador object) throws DataBaseException {

    }

    @Override
    public ArrayList<Organizador> loadAll() throws DataBaseException, SQLException {
        return null;
    }

    public boolean checkCPF(String cpf) throws DataBaseException, SQLException {

        Boolean alreadyExists = false;

        String sql = "SELECT * FROM organizador WHERE cpf = '"+cpf+"';";
        ResultSet rs = connection.runQuerySQL(sql);

        if(rs.isBeforeFirst()) {
            alreadyExists = true;
        }

        return alreadyExists;

    }

}
