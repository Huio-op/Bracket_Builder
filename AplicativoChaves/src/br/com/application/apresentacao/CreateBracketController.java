package br.com.application.apresentacao;

import br.com.application.negocio.ChaveTorneio;
import br.com.application.negocio.Evento;
import br.univates.system32.JFX.JFXErrorDialog;
import br.univates.system32.JFX.JFXTransitionHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateBracketController implements Initializable {

    @FXML
    private AnchorPane anchorBackground;

    @FXML
    private StackPane stackPane;

    @FXML
    private Label lblNomeEvento;

    @FXML
    private HBox hBox;

    @FXML
    private VBox vBox;

    JFXTransitionHandler th = new JFXTransitionHandler();
    private VerEventosController verEventosController;
    private ChaveTorneio chaveTorneio;
    private Evento evento;
    private ParticipanteMiniatureController partController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



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
        int qtdeCols = (chaveTorneio.getQuantidadeParticipantes()/2)+1;
        for (int i = 0; i < qtdeCols; i++){

            VBox vBox = new VBox();
            vBox.setPrefWidth(210);
            vBox.setPrefHeight(580);

            if(i == 0 || i == qtdeCols-1){

                for(int j = 0; j < chaveTorneio.getQuantidadeParticipantes()/2 ; j++){
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/application/apresentacao/ParticipanteMiniature.fxml"));
                        AnchorPane pMiniature = loader.load();

                        this.partController = loader.getController();
                        this.vBox.getChildren().add(pMiniature);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            hBox.getChildren().add(vBox);

        }

    }

    public void setEvento(Evento evento){
        this.evento = evento;
        this.lblNomeEvento.setText(evento.getNome());
    }

}
