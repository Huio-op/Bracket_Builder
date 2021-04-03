package br.application.apresentacao.validators;

import java.sql.SQLException;

import com.jfoenix.validation.base.ValidatorBase;

import br.application.persistencia.DBUsuarios;
import br.univates.system32.DataBase.DataBaseException;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

public class SameEmailValidator extends ValidatorBase{

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
	 * Valida o email para n�o criar Usuarios no Banco de Dados com emails repetidos.
	 */
	private void evalTextInputField() throws DataBaseException, SQLException {
		
		  TextInputControl textField = (TextInputControl) srcControl.get();
		  hasErrors.set(false);
		  DBUsuarios db = new DBUsuarios();
		  
		  if(db.checkEmail(textField.getText())) {
			  hasErrors.set(true);
		  }
		
	}

}
