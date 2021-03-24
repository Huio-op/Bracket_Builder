package br.application.apresentacao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javax.swing.event.ChangeListener;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTooltip;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import com.jfoenix.validation.StringLengthValidator;

import br.application.apresentacao.validators.ComparePasswordValidator;
import br.application.apresentacao.validators.SameEmailValidator;
import br.application.negocio.Usuario;
import br.application.persistencia.DBUsuarios;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.JFX.JFXInfoDialog;
import br.univates.system32.JFX.JFXTransitionHandler;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class LoginController implements Initializable {

	@FXML
    private StackPane rootStackPane;

	@FXML
    private AnchorPane paneAnchor;
	
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

	private DBUsuarios db;
	
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

		 try {
			db = new DBUsuarios();
		} catch (DataBaseException e) {
			
			System.out.println(e.getMessage());
			e.printStackTrace();
			
		}
		
	}

	@FXML
	private void closeWindow(MouseEvent event) {

		System.exit(0);

	}

	@FXML
	void cadastroTransition(ActionEvent event) {
		th.transitionFadeExpand(paneLogin, JFXTransitionHandler.FADEOUT, 0.5, loginPrefWidth, loginPrefHeight,
				createPrefWidth, createPrefHeight);

		paneCreateAccount.toFront();

		th.transitionFadeExpand(paneCreateAccount, JFXTransitionHandler.FADEIN, 0.5, loginPrefWidth, loginPrefHeight,
				createPrefWidth, createPrefHeight);

	}

	@FXML
	void loginTransition(ActionEvent event) {

		th.transitionFadeExpand(paneCreateAccount, JFXTransitionHandler.FADEOUT, 0.5, createPrefWidth, createPrefHeight,
				loginPrefWidth, loginPrefHeight);

		paneLogin.toFront();

		th.transitionFadeExpand(paneLogin, JFXTransitionHandler.FADEIN, 0.5, createPrefWidth, createPrefHeight,
				loginPrefWidth, loginPrefHeight);

	}

	@FXML
	void createAccount(ActionEvent event) {

		if(textEmailCreate.validate() && textNomeCreate.validate() 
				&& textPassCreate.validate() && textPassConfirm.validate()) {
			
			String email = textEmailCreate.getText();
			String nome = textNomeCreate.getText();
			String senha = textPassCreate.getText();
			
			try {
				
				Usuario u = new Usuario(email,nome,senha);
				db.save(u);
				
				JFXButton btnSuccess = new JFXButton("Voltar à tela de Login");
				btnSuccess.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e)->{
					
					loginTransition(event);
					
				});
				
				JFXInfoDialog dialogSuccess = new JFXInfoDialog(rootStackPane, paneAnchor,"Sucesso!", "Conta criada com sucesso!", 
						Arrays.asList(btnSuccess));
				dialogSuccess.showDialogPane();
				
			} catch (DataBaseException e) {
				e.getMessage();
				e.printStackTrace();
			}
			
		}
	}

	@FXML
	void textFieldUserKeyReleased(KeyEvent event) {

	}

	private void createEmailValidator() {

		RequiredFieldValidator rfValidator = new RequiredFieldValidator();
		
		//Valida se o texto colocado é um email válido de acordo com o padrão regex apresentado abaixo
		RegexValidator rxValidator = new RegexValidator();
		rxValidator.setRegexPattern("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		
		SameEmailValidator seValidator = new SameEmailValidator();
		
		
		textEmailCreate.getValidators().add(rfValidator);
		rfValidator.setMessage("Este campo é obrigatório!");
		textEmailCreate.getValidators().add(rxValidator);
		rxValidator.setMessage("Digite um email válido!");
		textEmailCreate.getValidators().add(seValidator);
		seValidator.setMessage("Email já cadastrado!");
		
		textEmailCreate.focusedProperty().addListener(new javafx.beans.value.ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

				if (!newValue) {
					textEmailCreate.validate();
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
		
		RequiredFieldValidator rfvalidator = new RequiredFieldValidator();
		textPassCreate.getValidators().add(rfvalidator);
		rfvalidator.setMessage("Este campo é obrigatório!");
		

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

		RequiredFieldValidator rfValidator = new RequiredFieldValidator();
		textPassConfirm.getValidators().add(rfValidator);
		rfValidator.setMessage("Este campo é obrigatório!");
		ComparePasswordValidator cpValidator = new ComparePasswordValidator(textPassCreate);
		textPassConfirm.getValidators().add(cpValidator);
		cpValidator.setMessage("As senhas não coincidem!");

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
