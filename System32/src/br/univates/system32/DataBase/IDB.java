package br.univates.system32.DataBase;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IDB<T> {

    public void save( T object) throws DataBaseException, SQLException;
    public T load (String key) throws DataBaseException, SQLException;
    public  void delete(T object) throws DataBaseException;
    public void edit(T object) throws DataBaseException, SQLException;
    public ArrayList<T>  loadAll() throws DataBaseException, SQLException;
    public ArrayList<T> loadFiltered(Filter filter) throws SQLException, DataBaseException;

}
