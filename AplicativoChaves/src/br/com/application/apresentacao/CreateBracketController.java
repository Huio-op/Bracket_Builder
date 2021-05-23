package br.com.application.apresentacao;

import br.com.application.negocio.ChaveTorneio;
import br.com.application.negocio.Evento;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.JFX.JFXErrorDialog;
import br.univates.system32.JFX.JFXTransitionHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateBracketController implements Initializable {

    @FXML
    private AnchorPane anchorBackground;

    @FXML
    private StackPane stackPane;

    @FXML
    private Label lblNomeEvento;

    @FXML
    private ScrollPane scrollBracket;

    @FXML
    private AnchorPane anchorBracket;

    @FXML
    private HBox hBox;

    @FXML
    private AnchorPane anchorHeader;

//    @FXML
//    private VBox vBox;

    JFXTransitionHandler th = new JFXTransitionHandler();
    private VerEventosController verEventosController;
    private ChaveTorneio chaveTorneio;
    private Evento evento;
    private ParticipanteMiniatureController partController;
    private AnchorPane tPart;
    private CreateParticipanteController createParticipanteController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {

            FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/br/com/application/apresentacao/TelaCreateParticipante.fxml"));
            this.tPart = loaderStart.load();
            this.stackPane.getChildren().add(tPart);
            this.stackPane.getChildren().set(2,tPart);
            this.createParticipanteController = loaderStart.getController();
            this.createParticipanteController.setCreateBacketController(this);

        } catch (IOException e) {
            e.printStackTrace();
        }

        pullToFront(anchorHeader);
        pullToFront(scrollBracket);

    }

    public void returnPage(){

        try {
            th.transitionFadeFXML(anchorBackground, "/br/com/application/apresentacao/TelaVerEventos.fxml",
                    1);
        } catch (IOException e) {
            e.printStackTrace();
            JFXErrorDialog error = new JFXErrorDialog((StackPane) anchorBackground.getParent().getParent(),
                    anchorBackground.getParent(), e);
            error.showDialogPane();
        }

    }

    public void setVerEventosController(VerEventosController verEventosController){ this.verEventosController = verEventosController; }

    public void setChaveTorneio(ChaveTorneio chaveTorneio){

        this.chaveTorneio = chaveTorneio;
        double qtdeCols = ((Math.log(chaveTorneio.getQuantidadeParticipantes())/Math.log(2))*2)+1;
        for (int i = 0; i < qtdeCols; i++){

            VBox vBox = new VBox();
            vBox.setPrefWidth(210);
            vBox.setPrefHeight(580);
            vBox.setMaxWidth(Region.USE_PREF_SIZE);
            vBox.setMaxHeight(Region.USE_PREF_SIZE);

            if(i == 0 || i == qtdeCols-1){
                for(int j = 0; j < chaveTorneio.getQuantidadeParticipantes()/2 ; j++){
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/application/apresentacao/ParticipanteMiniature.fxml"));
                        AnchorPane pMiniature = loader.load();

                        this.partController = loader.getController();
                        partController.setBracketController(this);
                        vBox.getChildren().add(pMiniature);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            this.hBox.getChildren().add(vBox);

        }

    }

    public void createParticipanteTransition(ParticipanteMiniatureController miniature){

        JFXTransitionHandler.transitionFade(createParticipanteController.getAnchorRoot(), JFXTransitionHandler.FADEIN, 1);
        pullToFront(this.tPart);
        createParticipanteController.show((Pane) this.anchorHeader, (Pane) this.anchorBracket,this.stackPane, miniature);

    }

    public void pullToFront(Object object){
        int index = this.stackPane.getChildren().indexOf(object);
        this.stackPane.getChildren().get(index).toFront();
    }

    public void setEvento(Evento evento){
        this.evento = evento;
        this.lblNomeEvento.setText(evento.getNome());
    }

    public ChaveTorneio getChaveTorneio(){ return this.chaveTorneio; }


}
