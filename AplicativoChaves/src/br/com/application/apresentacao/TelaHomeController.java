package br.com.application.apresentacao;

import br.com.application.negocio.Usuario;
import br.univates.system32.JFX.JFXErrorDialog;
import br.univates.system32.JFX.JFXTransitionHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TelaHomeController implements Initializable {

    public static Usuario user;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private StackPane rootStackPane;

    @FXML
    private AnchorPane paneAnchor;

    @FXML
    private Label lblUsrName;

    private JFXTransitionHandler th = new JFXTransitionHandler();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

      try{
          this.lblUsrName.setText(user.getFirstName());
      }catch(NullPointerException e){
          e.printStackTrace();
          JFXErrorDialog error = new JFXErrorDialog(rootStackPane, paneAnchor, e);
          error.showDialogPane();
      }

    }

    public static void setUser(Usuario userToLogIn){

        user = userToLogIn;

    }

    public void doLogOut(ActionEvent event){

        try {
            th.sceneTransition("/br/com/application/apresentacao/TelaLogin.fxml", event);
            user = null;
        } catch (IOException e) {
            e.printStackTrace();
            JFXErrorDialog error = new JFXErrorDialog(rootStackPane, paneAnchor, e);
            error.showDialogPane();
        }

    }


}
