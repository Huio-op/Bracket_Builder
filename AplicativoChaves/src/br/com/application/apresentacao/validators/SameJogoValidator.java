package br.com.application.apresentacao.validators;

import br.com.application.persistencia.DBJogo;
import br.com.application.persistencia.DBUsuarios;
import br.univates.system32.DataBase.DataBaseException;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

import java.sql.SQLException;

public class SameJogoValidator extends ValidatorBase {
    @Override
    protected void eval() {

        if (srcControl.get() instanceof TextInputControl) {

            try {
                evalTextInputField();
            } catch (DataBaseException e) {
                System.out.println("Erro na validator");
            } catch (SQLException e) {
                System.out.println("Erro na validator");
            }

        }

    }

    /*
     * Valida o email para não criar Jogos no Banco de Dados com nomes repetidos.
     */
    private void evalTextInputField() throws DataBaseException, SQLException {

        TextInputControl textField = (TextInputControl) srcControl.get();
        hasErrors.set(false);
        DBJogo db = new DBJogo();

        if(db.checkNome(textField.getText())) {
            hasErrors.set(true);
        }

    }

}
