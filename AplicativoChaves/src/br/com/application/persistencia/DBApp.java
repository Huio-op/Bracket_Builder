package br.com.application.persistencia;

import br.univates.system32.DataBase.DBConnection;
import br.univates.system32.DataBase.DataBaseException;

public class DBApp {

    public static DBConnection db;

    public static DBConnection getConnection(){

        if(db == null){
            try {
                db = new DBConnection("wnlrmkyd", "RrqNt3iigJVgBsFIPOMi3KurycXoU7cD", "wnlrmkyd"
                        , "motty.db.elephantsql.com", "5432");
                return db;
            } catch (DataBaseException e) {
                e.printStackTrace();
            }
        }

        return db;

    }

    public static boolean isDBCreated() {

        try {
            db.connect();
            db.runQuerySQL("SELECT * FROM usuario;");
        } catch (DataBaseException e) {
            return false;
        }
        return true;
    }

}
