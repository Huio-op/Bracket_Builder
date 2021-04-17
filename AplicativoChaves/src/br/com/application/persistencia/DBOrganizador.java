package br.com.application.persistencia;

import br.com.application.negocio.Organizador;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.DataBase.IDB;

import java.sql.SQLException;
import java.util.ArrayList;

public class DBOrganizador implements IDB<Organizador> {
    @Override
    public void save(Organizador object) throws DataBaseException, SQLException {

    }

    @Override
    public Organizador load(String key) throws DataBaseException, SQLException {
        return null;
    }

    @Override
    public void delete(Organizador object) throws DataBaseException {

    }

    @Override
    public void edit(Organizador object) throws DataBaseException {

    }

    @Override
    public ArrayList<Organizador> loadAll() throws DataBaseException, SQLException {
        return null;
    }
}
