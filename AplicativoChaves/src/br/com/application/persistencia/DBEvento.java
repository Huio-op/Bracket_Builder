package br.com.application.persistencia;

import br.com.application.negocio.ChaveTorneio;
import br.com.application.negocio.Evento;
import br.com.application.negocio.Jogo;
import br.com.application.persistencia.filters.EventoFilterOwner;
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
import java.util.Map;

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
                    "'"+evento.getDescricao()+"', "+evento.getPremio()+", '"+date+"');");

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
    public void edit(Evento evento) throws DataBaseException {

        if(evento != null){

            String date = evento.getData().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));

            connection.runSQL("UPDATE evento SET nome = '"+ evento.getNome() +"', " +
                    "jogo = (SELECT id_jogo FROM jogo WHERE id_jogo = "+evento.getJogo().getIdJogo()+")," +
                    "cpf_organizador = (SELECT cpf FROM organizador WHERE cpf = '"+evento.getCpfOrg().getCPFStringNoChars()+"'), " +
                    "detalhes = '"+evento.getDescricao()+"', premio = "+evento.getPremio()+", data = '"+date+"' " +
                    "WHERE id_evento = " + evento.getId() + ";");

        }

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

    public ArrayList<String> loadAllNameAndId() throws DataBaseException, SQLException {

        ArrayList<String> array = new ArrayList<String>();

        String sql = "SELECT e.id_evento, e.nome FROM evento e ORDER BY e.id_evento;";
        ResultSet rs = connection.runQuerySQL(sql);

        if(rs.isBeforeFirst()){
            while(rs.next()){

                int id = rs.getInt("id_evento");
                String nomeEvento = rs.getString("nome");

                array.add(id + "- "+ nomeEvento);
            }
        }

        return array;

    }

    @Override
    public ArrayList<Evento> loadFiltered(Filter filter) throws SQLException, DataBaseException {

        ArrayList<Evento> arrayFiltered = new ArrayList<Evento>();
        ArrayList<Evento> array = this.loadAll();

        for (Evento evento: array) {
            if(filter.isApproved(evento)){
                arrayFiltered.add(evento);
            }
        }

        return arrayFiltered;

    }

    public ArrayList<Evento> loadMultiFiltered(ArrayList<Filter> filterArray) throws SQLException, DataBaseException {

        ArrayList<Evento> arrayFiltered = null;
        ArrayList<Evento> array = this.loadAll();

        for(Filter filter : filterArray){
            arrayFiltered = new ArrayList<Evento>();
            for (Evento evento: array) {
                if(filter.isApproved(evento)){
                    arrayFiltered.add(evento);
                }
            }
            array = arrayFiltered;
        }

        return arrayFiltered;

    }

    public boolean hasBracket(Evento evento) throws SQLException, DataBaseException {

        if(evento != null){
            ResultSet rs = connection.runQuerySQL("SELECT * FROM chave_torneio WHERE id_evento = " + evento.getId() + ";");
            if(rs.isBeforeFirst()){
                return true;
            }

        }
        return false;
    }

    public ChaveTorneio getBracket(Evento evento) throws SQLException, DataBaseException {

        ChaveTorneio chaveTorneio = null;

        if(evento != null){
            ResultSet rs = connection.runQuerySQL("SELECT * FROM chave_torneio WHERE id_evento = " + evento.getId() + ";");
            if(rs.isBeforeFirst()){
                rs.next();
                int idChave = rs.getInt("id_chave");
                int idTipo = rs.getInt("tipo");
                int qtdeParticipantes = rs.getInt("qtde_participantes");
                int idEvento = rs.getInt("id_evento");
                boolean concluido = rs.getBoolean("concluido");

                chaveTorneio = new ChaveTorneio( idChave,idTipo,qtdeParticipantes,idEvento,concluido );
            }

        }
        return chaveTorneio;
    }

}
