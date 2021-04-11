package br.com.application.apresentacao.validators;

import br.com.application.persistencia.DBUsuarios;
import br.univates.system32.DataBase.DataBaseException;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public class PasswordValidator extends ValidatorBase {

    private JFXTextField email;

    public PasswordValidator(JFXTextField email){
        this.email = email;
    }

    @Override
    protected void eval() {

        if (srcControl.get() instanceof TextInputControl) {

            try {
                evalTextInputField();
            } catch (DataBaseException e) {
                System.out.println("Erro no validator (database)");
            } catch (SQLException e) {
                System.out.println("Erro no validator (SQL)");
            } catch (InvalidKeySpecException e) {
                System.out.println("Erro no validator (InvalidKey)");
            } catch (NoSuchAlgorithmException e) {
                System.out.println("Erro no validator (No such Algorithm)");
            }

        }

    }
    /*
     * Valida o email para não criar Usuarios no Banco de Dados com emails repetidos.
     */
    private void evalTextInputField() throws DataBaseException, SQLException, InvalidKeySpecException, NoSuchAlgorithmException {

        TextInputControl passField = (TextInputControl) srcControl.get();
        hasErrors.set(false);
        DBUsuarios db = new DBUsuarios();

        if(!db.checkPassword(email.getText(), passField.getText())) {
            hasErrors.set(true);
        }

    }
}
