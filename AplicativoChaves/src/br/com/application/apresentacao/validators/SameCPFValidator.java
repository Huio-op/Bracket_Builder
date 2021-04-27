package br.com.application.apresentacao.validators;

import br.com.application.persistencia.DBOrganizador;
import br.com.application.persistencia.DBUsuarios;
import br.univates.system32.DataBase.DataBaseException;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

import java.sql.SQLException;

public class SameCPFValidator extends ValidatorBase {

    private boolean wantSame;

    public SameCPFValidator(boolean wantSame){
        this.wantSame = wantSame;
    }

    @Override
    protected void eval() {

        if (srcControl.get() instanceof TextInputControl) {

            try {
                evalTextInputField();
            } catch (DataBaseException e) {
                System.out.println("Erro na validator de CPF");
            } catch (SQLException e) {
                System.out.println("Erro na validator de CPF");
            }

        }

    }

    private void evalTextInputField() throws DataBaseException, SQLException {

        TextInputControl textField = (TextInputControl) srcControl.get();
        hasErrors.set(false);
        DBOrganizador db = new DBOrganizador();

        if(db.checkCPF(textField.getText()) && wantSame == false) {
            hasErrors.set(true);
        }else if(!db.checkCPF(textField.getText()) && wantSame == true){
            hasErrors.set(true);
        }

    }

}
