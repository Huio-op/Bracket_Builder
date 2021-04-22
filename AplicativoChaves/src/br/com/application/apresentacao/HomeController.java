package br.com.application.apresentacao;

import br.com.application.negocio.Usuario;
import br.univates.system32.JFX.JFXErrorDialog;
import br.univates.system32.JFX.JFXTransitionHandler;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import javax.swing.event.ChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    public static Usuario user;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private StackPane rootStackPane;

    @FXML
    private AnchorPane paneAnchor;

    @FXML
    private AnchorPane anchorMainPage;

    @FXML
    private AnchorPane anchorProfile;

    @FXML
    private AnchorPane anchorFooter;

    @FXML
    private JFXButton btnNewBracket;

    @FXML
    private JFXButton btnSeeBrackets;

    private JFXTransitionHandler th = new JFXTransitionHandler();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            AnchorPane anchor  = FXMLLoader.load(getClass().getResource("/br/com/application/apresentacao/TelaHomePage.fxml"));
            anchorMainPage.getChildren().setAll(anchor);
        } catch (IOException e) {
            e.printStackTrace();
            JFXErrorDialog error = new JFXErrorDialog(rootStackPane, paneAnchor, e);
            error.showDialogPane();
        }


      try{
          if(!user.isOrganizador()){
              btnNewBracket.setOpacity(0);
              btnNewBracket.setDisable(true);
              btnSeeBrackets.setOpacity(0);
              btnSeeBrackets.setDisable(true);

          }else{
              btnNewBracket.setOpacity(1);
              btnNewBracket.setDisable(false);
              btnSeeBrackets.setOpacity(1);
              btnSeeBrackets.setDisable(false);

          }
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

    public void createOrganizadorTransition(ActionEvent event){

        try {
            th.transitionFadeFXML(anchorMainPage, "/br/com/application/apresentacao/TelaCreateOrganizador.fxml", 1);
        } catch (IOException e) {
            e.printStackTrace();
            JFXErrorDialog error = new JFXErrorDialog(rootStackPane, paneAnchor, e);
            error.showDialogPane();
        }

    }

    public void createBracketTransition(ActionEvent event){

        System.out.println("Criar bracket");

    }


}
