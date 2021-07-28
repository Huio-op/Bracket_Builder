package br.com.application.apresentacao.validators;

import br.com.application.persistencia.DBUsuarios;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

public class EvenParticipantsValidator extends ValidatorBase {


    @Override
    protected void eval() {
        if (srcControl.get() instanceof TextInputControl) {

            evalTextInputField();

        }
    }

    private void evalTextInputField(){

        TextInputControl textField = (TextInputControl) srcControl.get();
        hasErrors.set(false);

        if(Integer.parseInt(textField.getText())%2 != 0 ) {
            hasErrors.set(true);
        }

    }

}
