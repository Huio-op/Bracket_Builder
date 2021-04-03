package br.univates.system32.JFX;

import java.util.ArrayList;
import java.util.List;

import javax.xml.validation.Validator;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.base.ValidatorBase;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class JFXValidatorCreator {

	private JFXValidatorCreator() {
		
	}
	
	public static void createRequiredFieldValidator(JFXTextField textField) {
		
		setRequiredField(textField);
		
		setFocusedProperty(textField);
		
	}
	
	public static void createCustomFieldValidator(JFXTextField textField, List<ValidatorBase> validators) {
		
		setRequiredField(textField);
		
		validators.forEach(validator -> {
			textField.getValidators().add(validator);
		});
		
		setFocusedProperty(textField);
		
	}
	
	private static void setRequiredField(JFXTextField textField) {
		
		RequiredFieldValidator rfValidator = new RequiredFieldValidator();
		rfValidator.setMessage("Este campo é obrigatório!");
		textField.getValidators().add(rfValidator);
		
	}
	
	private static void setFocusedProperty(JFXTextField textField) {
		
		textField.focusedProperty().addListener(new javafx.beans.value.ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if (!newValue) {
					textField.validate();
				}

			}
		});
		
	}
	
public static void createRequiredFieldValidator(JFXPasswordField textField) {
		
		setRequiredField(textField);
		
		setFocusedProperty(textField);
		
	}
	
	public static void createCustomFieldValidator(JFXPasswordField textField, List<ValidatorBase> validators) {
		
		setRequiredField(textField);
		
		validators.forEach(validator -> {
			textField.getValidators().add(validator);
		});
		
		setFocusedProperty(textField);
		
	}
	
	private static void setRequiredField(JFXPasswordField textField) {
		
		RequiredFieldValidator rfValidator = new RequiredFieldValidator();
		rfValidator.setMessage("Este campo é obrigatório!");
		textField.getValidators().add(rfValidator);
		
	}
	
	private static void setFocusedProperty(JFXPasswordField textField) {
		
		textField.focusedProperty().addListener(new javafx.beans.value.ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if (!newValue) {
					textField.validate();
				}

			}
		});
		
	}
	
}
