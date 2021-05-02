package br.com.application.apresentacao;

import br.com.application.negocio.Organizador;
import br.com.application.negocio.Usuario;
import br.com.application.persistencia.DBOrganizador;
import br.com.application.persistencia.DBUsuarios;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.JFX.JFXErrorDialog;
import br.univates.system32.JFX.JFXTransitionHandler;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import javax.swing.event.ChangeListener;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private StackPane rootStackPane;

    @FXML
    private AnchorPane paneAnchor;

    @FXML
    private AnchorPane anchorMain;

    @FXML
    private AnchorPane anchorProfile;

    @FXML
    private AnchorPane anchorFooter;

    @FXML
    private JFXButton btnNewEvent;

    @FXML
    private JFXButton btnSeeEvents;

    private JFXTransitionHandler th = new JFXTransitionHandler();
    public static Usuario user;
    public static Organizador organizador;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/application/apresentacao/TelaHomePage.fxml"));
            AnchorPane anchorHomePage = loader.load();
            HomePageController orgController = loader.getController();
            orgController.setHomeController(this);

            anchorMain.getChildren().setAll(anchorHomePage);

        } catch (IOException e) {
            e.printStackTrace();
            JFXErrorDialog error = new JFXErrorDialog(rootStackPane, paneAnchor, e);
            error.showDialogPane();
        }


      try{
          if(!user.isOrganizador()){
              btnNewEvent.setOpacity(0);
              btnNewEvent.setDisable(true);
              btnSeeEvents.setOpacity(0);
              btnSeeEvents.setDisable(true);

          }else{
              btnNewEvent.setOpacity(1);
              btnNewEvent.setDisable(false);
              btnSeeEvents.setOpacity(1);
              btnSeeEvents.setDisable(false);
              DBOrganizador dbOrg = new DBOrganizador();
              setOrganizador(dbOrg.loadFromEmail(user.getEmail()));
          }
      }catch(NullPointerException e){
          e.printStackTrace();
          JFXErrorDialog error = new JFXErrorDialog(rootStackPane, paneAnchor, e);
          error.showDialogPane();
      } catch (SQLException throwables) {
          throwables.printStackTrace();
      } catch (DataBaseException e) {
          e.printStackTrace();
      }

    }

    public static void setUser(Usuario userToLogIn){

        user = userToLogIn;

    }

    public static void setOrganizador(Organizador user_org){

        organizador = user_org;

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

    public void refreshFooter(){

        try{
            if(!user.isOrganizador()){
                btnNewEvent.setOpacity(0);
                btnNewEvent.setDisable(true);
                btnSeeEvents.setOpacity(0);
                btnSeeEvents.setDisable(true);

            }else{
                btnNewEvent.setOpacity(1);
                btnNewEvent.setDisable(false);
                btnSeeEvents.setOpacity(1);
                btnSeeEvents.setDisable(false);

            }
        }catch(NullPointerException e){
            e.printStackTrace();
            JFXErrorDialog error = new JFXErrorDialog(rootStackPane, paneAnchor, e);
            error.showDialogPane();
        }

    }

    public void createBracketTransition(ActionEvent event){

        try {
            th.transitionFadeFXML(anchorMain, "/br/com/application/apresentacao/TelaCreateEvento.fxml", 1);
        } catch (IOException e) {
            e.printStackTrace();
            JFXErrorDialog error = new JFXErrorDialog(rootStackPane, paneAnchor, e);
            error.showDialogPane();
        }

    }

}
