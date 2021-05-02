package br.com.application.persistencia;

import br.com.application.negocio.Evento;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.DataBase.IDB;

import java.sql.SQLException;
import java.util.ArrayList;

public class DBEvento implements IDB<Evento> {


    @Override
    public void save(Evento object) throws DataBaseException, SQLException {

    }

    @Override
    public Evento load(String key) throws DataBaseException, SQLException {
        return null;
    }

    @Override
    public void delete(Evento object) throws DataBaseException {

    }

    @Override
    public void edit(Evento object) throws DataBaseException {

    }

    @Override
    public ArrayList<Evento> loadAll() throws DataBaseException, SQLException {
        return null;
    }
}
