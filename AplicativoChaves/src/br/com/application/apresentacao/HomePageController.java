package br.com.application.apresentacao;

import br.univates.system32.JFX.JFXErrorDialog;
import br.univates.system32.JFX.JFXTransitionHandler;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

public class HomePageController implements Initializable {

    @FXML
    private AnchorPane anchorMainPage;

    @FXML
    private AnchorPane anchorProfile;

    @FXML
    private Label lblUsrName;

    @FXML
    private JFXButton btnCreateOrganizer;

    private JFXTransitionHandler th = new JFXTransitionHandler();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.lblUsrName.setText(HomeController.user.getFirstName());
        if(!HomeController.user.isOrganizador()) {
            btnCreateOrganizer.setOpacity(1);
            btnCreateOrganizer.setDisable(false);
        }else {
            btnCreateOrganizer.setOpacity(0);
            btnCreateOrganizer.setDisable(true);
        }
    }

    public void createOrganizadorTransition(ActionEvent event){

        try {
            th.transitionFadeFXML(anchorMainPage, "/br/com/application/apresentacao/TelaCreateOrganizador.fxml", 1);
        } catch (IOException e) {
            e.printStackTrace();
            JFXErrorDialog error = new JFXErrorDialog((StackPane) anchorMainPage.getParent().getParent().getParent(), anchorMainPage.getParent().getParent(), e);
            error.showDialogPane();
        }

    }


}
