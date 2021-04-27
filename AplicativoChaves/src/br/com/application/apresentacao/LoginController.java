package br.com.application.apresentacao;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

import br.com.application.apresentacao.validators.PasswordValidator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;

import br.com.application.apresentacao.validators.ComparePasswordValidator;
import br.com.application.apresentacao.validators.SameEmailValidator;
import br.com.application.negocio.Usuario;
import br.com.application.persistencia.DBUsuarios;
import br.univates.system32.PasswordEncoder;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.JFX.JFXErrorDialog;
import br.univates.system32.JFX.JFXInfoDialog;
import br.univates.system32.JFX.JFXTransitionHandler;
import br.univates.system32.JFX.JFXValidatorCreator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

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
	private JFXTextField textEmailLogin;

	@FXML
	private JFXPasswordField textPasswordLogin;

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

	private double loginPrefHeight;
	private double loginPrefWidth;
	private double createPrefHeight;
	private double createPrefWidth;
	private JFXTransitionHandler th = new JFXTransitionHandler();

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
		loginEmailValidator();
		loginPasswordValidator();

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
    void doLogin(ActionEvent event) {

		if(textEmailLogin.validate() && textPasswordLogin.validate()){
			try {
				Usuario user = db.load(textEmailLogin.getText());
				HomeController.setUser(user);
				th.sceneTransition("/br/com/application/apresentacao/TelaHome.fxml", event);
			} catch (IOException e) {
				e.printStackTrace();
				JFXErrorDialog error = new JFXErrorDialog(rootStackPane, paneAnchor, e);
				error.showDialogPane();
			} catch (SQLException throwables) {
				throwables.printStackTrace();
				JFXErrorDialog error = new JFXErrorDialog(rootStackPane, paneAnchor, throwables);
				error.showDialogPane();
			} catch (DataBaseException e) {
				e.printStackTrace();
				JFXErrorDialog error = new JFXErrorDialog(rootStackPane, paneAnchor, e);
				error.showDialogPane();
			}
		}
		
    }

	@FXML
	void cadastroTransition(ActionEvent event) {
		th.transitionFadeExpand(paneLogin, JFXTransitionHandler.FADEOUT, 0.5, loginPrefWidth, loginPrefHeight,
				createPrefWidth, createPrefHeight);

		paneCreateAccount.toFront();

		th.transitionFadeExpand(paneCreateAccount, JFXTransitionHandler.FADEIN,
				0.5, loginPrefWidth, loginPrefHeight, createPrefWidth, createPrefHeight);

	}

	@FXML
	void loginTransition(ActionEvent event) {

		th.transitionFadeExpand(paneCreateAccount, JFXTransitionHandler.FADEOUT,
				0.5, createPrefWidth, createPrefHeight, loginPrefWidth, loginPrefHeight);

		paneLogin.toFront();

		th.transitionFadeExpand(paneLogin, JFXTransitionHandler.FADEIN,
				0.5, createPrefWidth, createPrefHeight, loginPrefWidth, loginPrefHeight);

	}

	@FXML
	void createAccount(ActionEvent event) {

		if (textEmailCreate.validate() && textNomeCreate.validate() && textPassCreate.validate()
				&& textPassConfirm.validate()) {
				
				try {
					String email = textEmailCreate.getText();
					String nome = textNomeCreate.getText();
					String senha;
					senha = PasswordEncoder.encodePassword(textPassCreate.getText());
					
					try {

						Usuario u = new Usuario(email, nome, senha);
						db.save(u);

						JFXButton btnSuccess = new JFXButton("Voltar à tela de Login");
						btnSuccess.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {

							textEmailCreate.setText("");
							textNomeCreate.setText("");
							textPassCreate.setText("");
							textPassConfirm.setText("");

							loginTransition(event);

						});

						JFXInfoDialog dialogSuccess = new JFXInfoDialog(rootStackPane, paneAnchor, "Sucesso!",
								"Conta criada com sucesso!", Arrays.asList(btnSuccess));
						dialogSuccess.showDialogPane();

					} catch (DataBaseException e) {
						JFXErrorDialog error = new JFXErrorDialog(rootStackPane, paneAnchor, e);
						error.showDialogPane();
					}	
					
				} catch (NoSuchAlgorithmException e1) {
					JFXErrorDialog error = new JFXErrorDialog(rootStackPane, paneAnchor, e1);
					error.showDialogPane();
				} catch (InvalidKeySpecException e1) {
					JFXErrorDialog error = new JFXErrorDialog(rootStackPane, paneAnchor, e1);
					error.showDialogPane();
				}
		}
	}

	@FXML
	void textFieldUserKeyReleased(KeyEvent event) {
	
	}

	private void loginEmailValidator() {

		// Valida se o texto colocado é um email válido de acordo com o padrão regex
		// apresentado abaixo
		RegexValidator rxValidator = new RegexValidator();
		rxValidator.setRegexPattern(
				"^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

		SameEmailValidator seValidator = new SameEmailValidator(true);

		rxValidator.setMessage("Digite um email válido!");
		seValidator.setMessage("Email não cadastrado!");

		JFXValidatorCreator.createCustomFieldValidator(textEmailLogin, Arrays.asList(rxValidator, seValidator)
				, true, false);

	}

	private void loginPasswordValidator(){

		PasswordValidator pValidator = new PasswordValidator(textEmailLogin);

		pValidator.setMessage("Senha incorreta!");

		JFXValidatorCreator.createCustomFieldValidator(textPasswordLogin, Arrays.asList(pValidator)
				, true, false);

	}

	private void createEmailValidator() {

		// Valida se o texto colocado é um email válido de acordo com o padrão regex
		// apresentado abaixo
		RegexValidator rxValidator = new RegexValidator();
		rxValidator.setRegexPattern(
				"^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

		SameEmailValidator seValidator = new SameEmailValidator(false);

		rxValidator.setMessage("Digite um email válido!");
		seValidator.setMessage("Email já cadastrado!");

		JFXValidatorCreator.createCustomFieldValidator(textEmailCreate, Arrays.asList(rxValidator, seValidator), true, true);

	}

	private void createNomeValidator() {

		JFXValidatorCreator.createRequiredFieldValidator(textNomeCreate);

	}

	private void createPasswordValidator() {

		JFXValidatorCreator.createRequiredFieldValidator(textPassCreate);

	}

	private void confirmPasswordValidator() {

		ComparePasswordValidator cpValidator = new ComparePasswordValidator(textPassCreate);
		cpValidator.setMessage("As senhas não coincidem!");

		JFXValidatorCreator.createCustomFieldValidator(textPassConfirm, Arrays.asList(cpValidator), true, true);

	}

}
