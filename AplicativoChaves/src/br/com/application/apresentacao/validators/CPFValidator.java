package br.com.application.apresentacao.validators;

import br.com.application.persistencia.DBOrganizador;
import br.univates.system32.CPF;
import br.univates.system32.DataBase.DataBaseException;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

import java.sql.SQLException;

public class CPFValidator extends ValidatorBase {
    @Override
    protected void eval() {
        if (srcControl.get() instanceof TextInputControl) {

            evalTextInputField();

        }

    }

    private void evalTextInputField() {

        TextInputControl textField = (TextInputControl) srcControl.get();
        hasErrors.set(false);

        if(!CPF.validaCPF(textField.getText())){

            hasErrors.set(true);

        }

    }

}
