package br.com.application.apresentacao;

import br.com.application.negocio.ChaveTorneio;
import br.com.application.negocio.Evento;
import br.com.application.negocio.Participante;
import br.com.application.persistencia.DBParticipante;
import br.com.application.persistencia.filters.ParticipanteFilterBracket;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.JFX.JFXErrorDialog;
import br.univates.system32.JFX.JFXTransitionHandler;
import com.jfoenix.controls.JFXTextArea;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    @FXML
    private JFXTextArea textDescricao;

    @FXML
    private Label lblPremio;

    @FXML
    private Label lblData;

//    @FXML
//    private VBox vBox;

    JFXTransitionHandler th = new JFXTransitionHandler();
    private VerEventosController verEventosController;
    private ChaveTorneio chaveTorneio;
    private Evento evento;
    private ParticipanteMiniatureController partController;
    private AnchorPane tPart;
    private CreateParticipanteController createParticipanteController;
    private DBParticipante dbParticipante = new DBParticipante();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
        int miniatureId = 1;
        int quantidadeColunaAtual = 0;
        int contador = 0;

        ArrayList<Participante> arrayPart = null;
        try {
            arrayPart = dbParticipante.loadFiltered(new ParticipanteFilterBracket(chaveTorneio));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (DataBaseException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < qtdeCols; i++){

            VBox vBox = new VBox();
            vBox.setPrefWidth(210);
            vBox.setPrefHeight(580);
            vBox.setMaxWidth(Region.USE_PREF_SIZE);
            vBox.setMaxHeight(Region.USE_PREF_SIZE);
            vBox.setFillWidth(true);
            if(i == 0){
                quantidadeColunaAtual = chaveTorneio.getQuantidadeParticipantes()/2;
            }else if(contador == 2){
                quantidadeColunaAtual = quantidadeColunaAtual*2;
            }else if(quantidadeColunaAtual != 1){
                quantidadeColunaAtual = quantidadeColunaAtual/2;
            }else{
                quantidadeColunaAtual = quantidadeColunaAtual;
                contador++;
            }

            for(int j = 0; j < quantidadeColunaAtual ; j++){
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/application/apresentacao/ParticipanteMiniature.fxml"));
                    AnchorPane pMiniature = loader.load();

                    this.partController = loader.getController();
                    partController.setId(miniatureId);
                    for(Participante participante : arrayPart){
                        if(miniatureId == participante.getPosicao()){
                            partController.setParticipante(participante);
                        }
                    }
                    if((i != 0 && i == qtdeCols-1) && partController.getParticipante() == null){
                        partController.setButton();
                    }else if((i == 0 && i != qtdeCols-1) && partController.getParticipante() == null){
                        partController.setButton();
                    }else if(partController.getParticipante() == null){
                        partController.setBlank();
                    }
                    miniatureId++;
                    partController.setBracketController(this);

                    vBox.getChildren().add(pMiniature);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

//                for(int j = 0; j < chaveTorneio.getQuantidadeParticipantes()/2 ; j++){
//                    try {
//                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/application/apresentacao/ParticipanteMiniature.fxml"));
//                        AnchorPane pMiniature = loader.load();
//
//                        this.partController = loader.getController();
//                        partController.setId(miniatureId);
//                        miniatureId++;
//                        partController.setBracketController(this);
//                        vBox.getChildren().add(pMiniature);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }

            this.hBox.getChildren().add(vBox);

        }

    }

    public void pullToFront(Object object){
        int index = this.stackPane.getChildren().indexOf(object);
        this.stackPane.getChildren().get(index).toFront();
    }

    public void setEvento(Evento evento){
        this.evento = evento;
        this.lblNomeEvento.setText(evento.getNome());
        this.textDescricao.setText(evento.getDescricao());
        this.lblPremio.setText("R$" + evento.getPremio().toString());
        this.lblData.setText(evento.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    public ChaveTorneio getChaveTorneio(){ return this.chaveTorneio; }


}
