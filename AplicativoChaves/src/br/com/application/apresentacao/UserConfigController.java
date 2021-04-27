package br.com.application.apresentacao;

import br.com.application.apresentacao.validators.ComparePasswordValidator;
import br.com.application.negocio.Organizador;
import br.com.application.negocio.Usuario;
import br.com.application.persistencia.DBOrganizador;
import br.com.application.persistencia.DBUsuarios;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.JFX.JFXErrorDialog;
import br.univates.system32.JFX.JFXInfoDialog;
import br.univates.system32.JFX.JFXTransitionHandler;
import br.univates.system32.JFX.JFXValidatorCreator;
import br.univates.system32.PasswordEncoder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

public class UserConfigController implements Initializable {

    @FXML
    private AnchorPane anchorBackgroundSett;

    @FXML
    private JFXTextField textNewName;

    @FXML
    private JFXPasswordField textNewPass;

    @FXML
    private JFXPasswordField textConfirmPass;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblName;

    @FXML
    private Label lblOrg;

    private JFXTransitionHandler th = new JFXTransitionHandler();
    private DBUsuarios db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            db = new DBUsuarios();
        } catch (DataBaseException e) {
            e.printStackTrace();
            JFXErrorDialog error = new JFXErrorDialog((StackPane) anchorBackgroundSett.getParent().getParent().getParent(),
                    anchorBackgroundSett.getParent().getParent(), e);
            error.showDialogPane();
        }

        this.lblEmail.setText(HomeController.user.getEmail());
        this.lblName.setText(HomeController.user.getNome());

        if(HomeController.user.isOrganizador()){
            try {
                DBOrganizador dbOrganizadorg = new DBOrganizador();
                Organizador org = dbOrganizadorg.loadFromEmail(HomeController.user.getEmail());
                this.lblOrg.setText(org.getCpf().getCPFString());
            } catch (DataBaseException | SQLException e) {
                e.printStackTrace();
            }
        }else{
            this.lblOrg.setText("Não cadastrado");
        }
    }

    public void returnPage(ActionEvent event){

        try {
            th.transitionFadeFXML(anchorBackgroundSett, "/br/com/application/apresentacao/TelaHomePage.fxml", 1);
        } catch (IOException e) {
            e.printStackTrace();
            JFXErrorDialog error = new JFXErrorDialog((StackPane) anchorBackgroundSett.getParent().getParent().getParent(),
                    anchorBackgroundSett.getParent().getParent(), e);
            error.showDialogPane();
        }

    }

    public void delete(ActionEvent event){


        try {
            db.delete(HomeController.user);

            JFXButton btnSuccess = new JFXButton("Voltar à tela principal");
            btnSuccess.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {

                textNewName.setText("");
                textNewPass.setText("");
                textConfirmPass.setText("");

                try {
                    th.sceneTransition("/br/com/application/apresentacao/TelaLogin.fxml", event);
                    HomeController.user = null;
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }


            });

            JFXInfoDialog dialogSuccess = new JFXInfoDialog((StackPane) anchorBackgroundSett.getParent().getParent().getParent(),
                    anchorBackgroundSett.getParent().getParent(), "Sucesso!",
                    "Conta excluida com sucesso!", Arrays.asList(btnSuccess));

            dialogSuccess.showDialogPane();

        } catch (DataBaseException e) {
            e.printStackTrace();
        }


    }

    public void save(ActionEvent event){

        String newName = null;
        String newPass = null;

        if(!textNewName.getText().equals("")){
            newName = textNewName.getText();
        }
        if(!textNewPass.getText().equals("")){

            confirmPasswordValidator();

            if(textConfirmPass.validate()){
                try {
                    newPass = PasswordEncoder.encodePassword(textNewPass.getText());
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    JFXErrorDialog error = new JFXErrorDialog((StackPane) anchorBackgroundSett.getParent().getParent().getParent(),
                            anchorBackgroundSett.getParent().getParent(), e);
                    error.showDialogPane();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                    JFXErrorDialog error = new JFXErrorDialog((StackPane) anchorBackgroundSett.getParent().getParent().getParent(),
                            anchorBackgroundSett.getParent().getParent(), e);
                    error.showDialogPane();
                }
            }

        }

        JFXButton btnSuccess = new JFXButton("Voltar à tela principal");
        btnSuccess.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {

            textNewName.setText("");
            textNewPass.setText("");
            textConfirmPass.setText("");

            returnPage(event);

        });

        JFXInfoDialog dialogSuccess = new JFXInfoDialog((StackPane) anchorBackgroundSett.getParent().getParent().getParent(),
                anchorBackgroundSett.getParent().getParent(), "Sucesso!",
                "Conta modificada com sucesso!", Arrays.asList(btnSuccess));


        if(newName !=null && newPass != null){


            try {
                Usuario u = new Usuario(HomeController.user.getEmail(), newName,
                        newPass, HomeController.user.isOrganizador());
                db.edit(u);
            } catch (DataBaseException e) {
                e.printStackTrace();
            }
            refreshUser();
            dialogSuccess.showDialogPane();

        }else if(newName != null){

            try {
                Usuario u = new Usuario(HomeController.user.getEmail(), newName,
                        HomeController.user.getSenha(), HomeController.user.isOrganizador());
                db.edit(u);
                refreshUser();
                dialogSuccess.showDialogPane();

            } catch (DataBaseException e) {
                e.printStackTrace();
                JFXErrorDialog error = new JFXErrorDialog((StackPane) anchorBackgroundSett.getParent().getParent().getParent(),
                        anchorBackgroundSett.getParent().getParent(), e);
                error.showDialogPane();
            }


        }else if(newPass != null){

            try {
                Usuario u = new Usuario(HomeController.user.getEmail(), HomeController.user.getNome(),
                        newPass, HomeController.user.isOrganizador());
                db.edit(u);
                refreshUser();
                dialogSuccess.showDialogPane();

            } catch (DataBaseException e) {
                e.printStackTrace();
                JFXErrorDialog error = new JFXErrorDialog((StackPane) anchorBackgroundSett.getParent().getParent().getParent(),
                        anchorBackgroundSett.getParent().getParent(), e);
                error.showDialogPane();
            }
        }

    }

    private void confirmPasswordValidator() {

        ComparePasswordValidator cpValidator = new ComparePasswordValidator(textNewPass);
        cpValidator.setMessage("As senhas não coincidem!");

        JFXValidatorCreator.createCustomFieldValidator(textConfirmPass, Arrays.asList(cpValidator),
                true, true);

    }

    private void refreshUser(){

        try {
            HomeController.setUser(db.load(HomeController.user.getEmail()));
        } catch (DataBaseException e) {
            e.printStackTrace();
            JFXErrorDialog error = new JFXErrorDialog((StackPane) anchorBackgroundSett.getParent().getParent().getParent(),
                    anchorBackgroundSett.getParent().getParent(), e);
            error.showDialogPane();
        } catch (SQLException e1) {
            e1.printStackTrace();
            JFXErrorDialog error = new JFXErrorDialog((StackPane) anchorBackgroundSett.getParent().getParent().getParent(),
                    anchorBackgroundSett.getParent().getParent(), e1);
            error.showDialogPane();
        }

    }

}
