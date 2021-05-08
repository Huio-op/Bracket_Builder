package br.com.application.persistencia;

import br.com.application.negocio.Evento;
import br.com.application.negocio.Jogo;
import br.univates.system32.CPF;
import br.univates.system32.DataBase.DBConnection;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.DataBase.IDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DBEvento implements IDB<Evento> {

    private DBConnection connection;
    private DBJogo dbJogo;

    public DBEvento(){

        this.connection = DBApp.getConnection();
        this.dbJogo = new DBJogo();

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
    public Evento load(String idEvento) throws DataBaseException, SQLException {

        String sql = "SELECT * FROM evento WHERE id_evento = "+Integer.parseInt(idEvento)+";";
        Evento evento = null;

        ResultSet rs = connection.runQuerySQL(sql);

        if(rs.isBeforeFirst()) {
            rs.next();
            int id = rs.getInt("id_evento");
            String nomeEvento = rs.getString("nome");
            int idJogo = rs.getInt("jogo");
            String cpfOrgString = rs.getString("cpf_organizador");
            String detalhes = rs.getString("detalhes");
            Double premio = rs.getDouble("premio");
            String data = rs.getString("data");

            Jogo jogo = dbJogo.load(String.valueOf(idJogo));

            CPF cpfOrg = new CPF();
            cpfOrg.setCPF(cpfOrgString);

            String[] dataSplit= data.split("/");
            LocalDate dataForm = LocalDate.of(Integer.parseInt(dataSplit[2]),Integer.parseInt(dataSplit[1]),Integer.parseInt(dataSplit[0]));

            evento = new Evento( id,nomeEvento,jogo,cpfOrg,detalhes,premio,dataForm);



        }

        return evento;

    }

    @Override
    public void delete(Evento evento) throws DataBaseException {

        if(evento != null){

            connection.runSQL("DELETE FROM evento WHERE id_evento = " + evento.getId() + ";");

        }

    }

    @Override
    public void edit(Evento object) throws DataBaseException {

    }

    @Override
    public ArrayList<Evento> loadAll() throws DataBaseException, SQLException {

        ArrayList<Evento> array = new ArrayList<Evento>();

        String sql = "SELECT * FROM evento;";
        ResultSet rs = connection.runQuerySQL(sql);

        if(rs.isBeforeFirst()){
            while(rs.next()){

                int id = rs.getInt("id_evento");
                String nomeEvento = rs.getString("nome");
                int idJogo = rs.getInt("jogo");
                String cpfOrgString = rs.getString("cpf_organizador");
                String detalhes = rs.getString("detalhes");
                Double premio = rs.getDouble("premio");
                String data = rs.getString("data");

                Jogo jogo = dbJogo.load(String.valueOf(idJogo));

                CPF cpfOrg = new CPF();
                cpfOrg.setCPF(cpfOrgString);

                String[] dataSplit= data.split("-");
                LocalDate dataForm = LocalDate.of(Integer.parseInt(dataSplit[0]),Integer.parseInt(dataSplit[1]),Integer.parseInt(dataSplit[2]));

                Evento evento = new Evento( id,nomeEvento,jogo,cpfOrg,detalhes,premio,dataForm);
                array.add(evento);
            }
        }

        return array;

    }
}
