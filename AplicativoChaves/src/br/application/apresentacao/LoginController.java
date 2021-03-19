package br.application.apresentacao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.event.ChangeListener;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import br.univates.system32.JFXTransitionHandler;
import br.univates.system32.JFXTransitionHandler.TransitionTypes;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class LoginController implements Initializable {

	@FXML
	private Label labelClose;

	@FXML
	private StackPane stackPane;

	@FXML
	private AnchorPane paneCreateAccount;

	@FXML
	private JFXTextField textEmailCreate;

	@FXML
	private JFXButton btnVoltar;

	@FXML
	private JFXTextField textNomeCreate;

	@FXML
	private JFXPasswordField textPassCreate;

	@FXML
	private JFXPasswordField textPassConfirm;

	@FXML
	private JFXButton btnLogin;

	@FXML
	private JFXButton btnCadastro;

	@FXML
	private AnchorPane paneLogin;

	@FXML
	private JFXButton btnCriarConta;

	@FXML
	private ImageView imgIconEmail;

	@FXML
	private ImageView imgIconName;

	@FXML
	private ImageView imgIconPassword;

	@FXML
	private ImageView imgIconConfirmPass;

	JFXTransitionHandler th = new JFXTransitionHandler();
	private double loginPrefHeight;
	private double loginPrefWidth;
	private double createPrefHeight;
	private double createPrefWidth;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		this.loginPrefHeight = paneLogin.prefHeightProperty().doubleValue();
		this.loginPrefWidth = paneLogin.prefWidthProperty().doubleValue();
		this.createPrefHeight = paneCreateAccount.prefHeightProperty().doubleValue();
		this.createPrefWidth = paneCreateAccount.prefHeightProperty().doubleValue();

		createEmailValidator();
		createNomeValidator();
		createPasswordValidator();
		confirmPasswordValidator();

	}

	@FXML
	private void closeWindow(MouseEvent event) {

		System.exit(0);

	}

	@FXML
	void cadastroTransition(ActionEvent event) {
		th.transitionFadeExpand(paneLogin, TransitionTypes.FADEOUT, 0.5, loginPrefWidth, loginPrefHeight,
				createPrefWidth, createPrefHeight);

		paneCreateAccount.toFront();

		th.transitionFadeExpand(paneCreateAccount, TransitionTypes.FADEIN, 0.5, loginPrefWidth, loginPrefHeight,
				createPrefWidth, createPrefHeight);

	}

	@FXML
	void loginTransition(ActionEvent event) {

		th.transitionFadeExpand(paneCreateAccount, TransitionTypes.FADEOUT, 0.5, createPrefWidth, createPrefHeight,
				loginPrefWidth, loginPrefHeight);

		paneLogin.toFront();

		th.transitionFadeExpand(paneLogin, TransitionTypes.FADEIN, 0.5, createPrefWidth, createPrefHeight,
				loginPrefWidth, loginPrefHeight);

	}

	@FXML
	void createAccount(ActionEvent event) {

	}

	@FXML
	void textFieldUserKeyReleased(KeyEvent event) {

	}

	private void createEmailValidator() {

		RequiredFieldValidator validator = new RequiredFieldValidator();
		textEmailCreate.getValidators().add(validator);
		validator.setMessage("Este campo é obrigatório!");

		textEmailCreate.focusedProperty().addListener(new javafx.beans.value.ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if (!newValue) {
					textEmailCreate.validate();
					imgIconEmail.setImage(
							new Image("file:imgComponents/icon_crossmark.png"));
				}else if(newValue){
					
				}

			}
		});

	}

	private void createNomeValidator() {

		RequiredFieldValidator validator = new RequiredFieldValidator();
		textNomeCreate.getValidators().add(validator);
		validator.setMessage("Este campo é obrigatório!");

		textNomeCreate.focusedProperty().addListener(new javafx.beans.value.ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if (!newValue) {
					textNomeCreate.validate();
				}

			}
		});

	}

	private void createPasswordValidator() {

		RequiredFieldValidator validator = new RequiredFieldValidator();
		textPassCreate.getValidators().add(validator);
		validator.setMessage("Este campo é obrigatório!");

		textPassCreate.focusedProperty().addListener(new javafx.beans.value.ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if (!newValue) {
					textPassCreate.validate();
				}

			}
		});

	}

	private void confirmPasswordValidator() {

		RequiredFieldValidator validator = new RequiredFieldValidator();
		textPassConfirm.getValidators().add(validator);
		validator.setMessage("Este campo é obrigatório!");

		textPassConfirm.focusedProperty().addListener(new javafx.beans.value.ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if (!newValue) {
					textPassConfirm.validate();
				}

			}
		});

	}

}
