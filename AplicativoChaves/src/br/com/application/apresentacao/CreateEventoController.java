package br.com.application.apresentacao;

import br.com.application.negocio.Jogo;
import br.com.application.persistencia.DBJogo;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.JFX.JFXErrorDialog;
import br.univates.system32.JFX.JFXTransitionHandler;
import br.univates.system32.JFX.JFXValidatorCreator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateEventoController implements Initializable {

    @FXML
    private AnchorPane anchorBackgroundEvt;

    @FXML
    private StackPane stackPane;

    @FXML
    private JFXButton btnVoltar;

    @FXML
    private JFXTextField textNome;

    @FXML
    private JFXTextField textLocal;

    @FXML
    private JFXButton btnCreateEvento;

    @FXML
    private JFXTextArea textDesc;

    @FXML
    private JFXComboBox<String> comboJogo;

    @FXML
    private JFXButton btnCreateGame;

    JFXTransitionHandler th = new JFXTransitionHandler();
    CreateJogoController jogoController;
    DBJogo dbJogo = new DBJogo();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/application/apresentacao/TelaCreateJogo.fxml"));
            AnchorPane tJogo = loader.load();
            this.stackPane.getChildren().add(tJogo);
            this.stackPane.getChildren().get(1).toBack();
            this.jogoController = loader.getController();
            this.jogoController.setEventoController(this);

        } catch (IOException e) {
            e.printStackTrace();
            JFXErrorDialog error = new JFXErrorDialog((StackPane) anchorBackgroundEvt.getParent().getParent(),
                    anchorBackgroundEvt.getParent(), e);
            error.showDialogPane();
        }

        refreshComboJogo();

        createNomeValidator();

    }

    public void refreshComboJogo(){

        this.comboJogo.getItems().removeAll();

        try {
            ArrayList<Jogo> array = dbJogo.loadAll();
            if(!array.isEmpty()){
                for (Jogo jogo: array) {
                    this.comboJogo.getItems().add(jogo.getNome());
                }
            }

        } catch (DataBaseException e) {
            e.printStackTrace();
            JFXErrorDialog error = new JFXErrorDialog((StackPane) anchorBackgroundEvt.getParent(),
                    anchorBackgroundEvt.getParent(), e);
            error.showDialogPane();
        } catch (SQLException e) {
            e.printStackTrace();
            JFXErrorDialog error = new JFXErrorDialog((StackPane) anchorBackgroundEvt.getParent(),
                    anchorBackgroundEvt.getParent(), e);
            error.showDialogPane();
        }

    }

    public void returnPage(ActionEvent event){

        try {
            th.transitionFadeFXML(anchorBackgroundEvt, "/br/com/application/apresentacao/TelaHome.fxml",
                    1);
        } catch (IOException e) {
            e.printStackTrace();
            JFXErrorDialog error = new JFXErrorDialog((StackPane) anchorBackgroundEvt.getParent().getParent().getParent(),
                    anchorBackgroundEvt.getParent().getParent(), e);
            error.showDialogPane();
        }

    }

    public void createJogoTransition(ActionEvent event){

        this.stackPane.getChildren();
        JFXTransitionHandler.transitionFade(jogoController.getAnchorRoot(), JFXTransitionHandler.FADEIN, 1);
        this.stackPane.getChildren().get(0).toFront();
        jogoController.show((Pane) this.stackPane.getChildren().get(0));

    }

    public void createEvento(ActionEvent event) {



    }

    private void createNomeValidator() {

        JFXValidatorCreator.createRequiredFieldValidator(textNome);

    }


}
