package br.application.apresentacao.validators;

import java.sql.SQLException;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.base.ValidatorBase;

import br.application.persistencia.DBUsuarios;
import br.univates.system32.DataBase.DataBaseException;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Pane;

public class ComparePasswordValidator extends ValidatorBase {

	private JFXPasswordField panelToCompare;
	private String originalPassword;
	
	public ComparePasswordValidator(JFXPasswordField panelToCompare) {
		this.panelToCompare = panelToCompare;
	}
	
	@Override
	protected void eval() {

		if (srcControl.get() instanceof TextInputControl) {

			this.originalPassword = this.panelToCompare.getText();
			evalTextInputField();

		}

	}

	private void evalTextInputField(){

		TextInputControl textField = (TextInputControl) srcControl.get();
		hasErrors.set(false);
		
		if(!textField.getText().equals(originalPassword)) {
			hasErrors.set(true);
		}

	}

}
