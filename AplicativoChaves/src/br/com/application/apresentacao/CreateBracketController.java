package br.com.application.apresentacao;

import br.com.application.negocio.ChaveTorneio;
import br.com.application.negocio.Evento;
import br.com.application.negocio.PartPosition;
import br.com.application.negocio.Participante;
import br.com.application.persistencia.DBParticipante;
import br.com.application.persistencia.filters.ParticipanteFilterBracket;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.JFX.JFXErrorDialog;
import br.univates.system32.JFX.JFXTransitionHandler;
import com.jfoenix.controls.JFXTextArea;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import java.util.Vector;

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
    private AnchorPane tPart;
    private CreateParticipanteController createParticipanteController;
    private DBParticipante dbParticipante = new DBParticipante();
    private Vector<Vector<ParticipanteMiniatureController>> partControllerMatrix = new Vector<Vector<ParticipanteMiniatureController>>();
    private AnchorPane tEditPart;
    private EditParticipanteController editParticipanteController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            FXMLLoader loaderEdit = new FXMLLoader(getClass().getResource("/br/com/application/apresentacao/TelaEditParticipante.fxml"));
            this.tEditPart = loaderEdit.load();
            this.stackPane.getChildren().add(tEditPart);
            this.stackPane.getChildren().set(2,tEditPart);
            this.editParticipanteController = loaderEdit.getController();
            this.editParticipanteController.setBracketController(this);
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
        renderParticipantes();

    }

    public void renderParticipantes() {

        this.hBox.getChildren().clear();
        this.partControllerMatrix.clear();

        double qtdeCols = ((Math.log(chaveTorneio.getQuantidadeParticipantes())/Math.log(2))*2)+1;
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

            int miniatureId = 1;

            VBox vBox = new VBox();
            vBox.setPrefWidth(260);
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

            Vector<ParticipanteMiniatureController> partControllerVector= new Vector<ParticipanteMiniatureController>();

            for(int j = 0; j < quantidadeColunaAtual ; j++){
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/application/apresentacao/ParticipanteMiniature.fxml"));
                    AnchorPane pMiniature = loader.load();

                    partControllerVector.add(loader.getController());

                    partControllerVector.get(j).setId(miniatureId, i);
                    if (contador == 2) {
                        partControllerVector.get(j).setWinPosition(i - 1, ((int) Math.ceil(((double) miniatureId) / 2)));
                    } else if (contador == 1) {
                        partControllerVector.get(j).setWinPosition(-1, -1);
                    } else {
                        partControllerVector.get(j).setWinPosition(i + 1, ((int) Math.ceil(((double) miniatureId) / 2)));
                    }

                    for(Participante participante : arrayPart){
                        for (PartPosition partPos : participante.getPositions()) {
                            if(i == partPos.getPosCol() && miniatureId == partPos.getPosLin()){
                                partControllerVector.get(j).setPartConfig(participante, partPos.getPontos());
                            }
                        }
                    }
                    if((i != 0 && i == qtdeCols-1) && partControllerVector.get(j).getParticipante() == null){
                        partControllerVector.get(j).setComboBox();
                        partControllerVector.get(j).fillComboBox(arrayPart);
                    }else if((i == 0 && i != qtdeCols-1) && partControllerVector.get(j).getParticipante() == null){
                        partControllerVector.get(j).setComboBox();
                        partControllerVector.get(j).fillComboBox(arrayPart);
                    }else if(partControllerVector.get(j).getParticipante() == null){
                        partControllerVector.get(j).setBlank();
                    }
                    miniatureId++;
                    partControllerVector.get(j).setBracketController(this);

                    vBox.getChildren().add(pMiniature);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            this.partControllerMatrix.add(partControllerVector);

            this.hBox.getChildren().add(vBox);

        }
    }

    public void saveBracket() {

        for ( int i = 0; i < this.hBox.getChildren().size(); i++ ) {

            final VBox vBox = (VBox) this.hBox.getChildren().get(i);

            for (int j = 0; j < vBox.getChildren().size(); j++) {

                ParticipanteMiniatureController partController = this.partControllerMatrix.get(i).get(j);

                if(partController.getParticipante() != null) {
                    try {
                        Participante part = partController.getParticipante();
                        part.addPosition(new PartPosition(part.getId(), i, j + 1, partController.getPontos()));
                        dbParticipante.edit(partController.getParticipante());
                    } catch (DataBaseException e) {
                        e.printStackTrace();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        }

        renderParticipantes();

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

    public void editParticipantTransistion(ParticipanteMiniatureController partController) {
        this.stackPane.getChildren();
        JFXTransitionHandler.transitionFade(editParticipanteController.getAnchorRoot(), JFXTransitionHandler.FADEIN, 1);
        pullToFront(this.tEditPart);
        editParticipanteController.show((Pane) this.anchorHeader, (Pane) this.anchorBracket,this.stackPane, partController);
    }

    public ChaveTorneio getChaveTorneio(){ return this.chaveTorneio; }


}
