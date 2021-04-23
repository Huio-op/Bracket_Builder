package br.com.application.apresentacao;

import br.com.application.negocio.Organizador;
import br.com.application.persistencia.DBOrganizador;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.JFX.JFXErrorDialog;
import br.univates.system32.JFX.JFXTransitionHandler;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
            JFXErrorDialog error = new JFXErrorDialog((StackPane) anchorBackgroundSett.getParent().getParent().getParent(), anchorBackgroundSett.getParent().getParent(), e);
            error.showDialogPane();
        }

    }

    public void cancel(){



    }

    public void save(){



    }

}
