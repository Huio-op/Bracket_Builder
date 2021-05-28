package br.com.application.apresentacao;

import br.com.application.negocio.ChaveTorneio;
import br.com.application.negocio.Evento;
import br.com.application.persistencia.DBEvento;
import br.com.application.persistencia.DBJogo;
import br.com.application.persistencia.filters.EventoFilterJogo;
import br.com.application.persistencia.filters.EventoFilterOwner;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.DataBase.Filter;
import br.univates.system32.JFX.JFXErrorDialog;
import br.univates.system32.JFX.JFXTransitionHandler;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VerEventosController implements Initializable {

    @FXML
    private AnchorPane annchorBackgroundSeeEvt;

    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane anchorHeader;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private AnchorPane anchorScroll;

    @FXML
    private VBox vBox;

    @FXML
    private JFXComboBox<String> comboJogoFilter;

    JFXTransitionHandler th = new JFXTransitionHandler();
    DBEvento dbEvento;
    EventoMiniatureController eventoMiniatureController;
    EditEventoController editEventoController;
    StartEventoController startEventoController;
    CreateBracketController createBracketController;
    AnchorPane tJogo;
    AnchorPane tStart;
    AnchorPane tBracket;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dbEvento = new DBEvento();
        renderEventos();

        try {
            FXMLLoader loaderEdit = new FXMLLoader(getClass().getResource("/br/com/application/apresentacao/TelaEditEvento.fxml"));
            this.tJogo = loaderEdit.load();
            this.stackPane.getChildren().add(tJogo);
            this.stackPane.getChildren().set(2,tJogo);
            this.editEventoController = loaderEdit.getController();
            this.editEventoController.setVerEventosController(this);

            FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/br/com/application/apresentacao/TelaStartEvento.fxml"));
            this.tStart = loaderStart.load();
            this.stackPane.getChildren().add(tStart);
            this.stackPane.getChildren().set(3,tStart);
            this.startEventoController = loaderStart.getController();
            this.startEventoController.setVerEventosController(this);

            DBJogo dbJogo = new DBJogo();
            ArrayList<String> arrayJogos = dbJogo.loadAllNameAndId();
            for(String jogo : arrayJogos){
                comboJogoFilter.getItems().add(jogo);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (DataBaseException e) {
            e.printStackTrace();
        }

        pullToFront(this.anchorHeader);
        pullToFront(this.scrollPane);

    }

    public void pullToFront(Object object){
        int index = this.stackPane.getChildren().indexOf(object);
        this.stackPane.getChildren().get(index).toFront();
    }

    public void returnPage(){

        try {
            th.transitionFadeFXML(annchorBackgroundSeeEvt, "/br/com/application/apresentacao/TelaHome.fxml",
                    1);
        } catch (IOException e) {
            e.printStackTrace();
            JFXErrorDialog error = new JFXErrorDialog((StackPane) annchorBackgroundSeeEvt.getParent().getParent(),
                    annchorBackgroundSeeEvt.getParent(), e);
            error.showDialogPane();
        }

    }

    public void applyFilter(ActionEvent event){

        ArrayList<Filter> filterArray = new ArrayList<Filter>();
        filterArray.add(new EventoFilterOwner(HomeController.organizador));
        String idJogo = comboJogoFilter.getValue();
        if(idJogo != null){
            idJogo = idJogo.split("-")[0];
            filterArray.add(new EventoFilterJogo(idJogo));
        }

        renderEventosFiltered(filterArray);

    }

    public void renderEventosFiltered(ArrayList<Filter> filterArray){

        this.vBox.getChildren().clear();

        try {
            ArrayList<Evento> allFilteredEventos = dbEvento.loadMultiFiltered(filterArray);
            if(!allFilteredEventos.isEmpty()){

                for (Evento evento: allFilteredEventos) {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/application/apresentacao/EventoMiniature.fxml"));
                    AnchorPane tMiniature = loader.load();
                    this.eventoMiniatureController = loader.getController();
                    this.eventoMiniatureController.setEvento(evento);
                    this.eventoMiniatureController.setVerEventosController(this);
                    this.vBox.getChildren().add(tMiniature);

                }
            }
        } catch (DataBaseException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void renderEventos(){

        this.vBox.getChildren().clear();

        try {
            ArrayList<Evento> allEventos = dbEvento.loadFiltered(new EventoFilterOwner(HomeController.organizador));

            if(!allEventos.isEmpty()){

                for (Evento evento: allEventos) {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/application/apresentacao/EventoMiniature.fxml"));
                    AnchorPane tMiniature = loader.load();
                    this.eventoMiniatureController = loader.getController();
                    this.eventoMiniatureController.setEvento(evento);
                    this.eventoMiniatureController.setVerEventosController(this);
                    this.vBox.getChildren().add(tMiniature);

                }
            }
        } catch (DataBaseException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editEventoTransition(Evento evento){

        this.stackPane.getChildren();
        JFXTransitionHandler.transitionFade(editEventoController.getAnchorRoot(), JFXTransitionHandler.FADEIN, 1);
        pullToFront(this.tJogo);
        editEventoController.show((Pane) this.anchorHeader, (Pane) this.anchorScroll,this.stackPane, evento);

    }

    public void startEventoTransition(Evento evento){

        try {
            if(dbEvento.hasBracket(evento)){
                createBracketTransition(dbEvento.getBracket(evento), evento);
            }else{
                this.stackPane.getChildren();
                JFXTransitionHandler.transitionFade(startEventoController.getAnchorRoot(), JFXTransitionHandler.FADEIN, 1);
                pullToFront(this.tStart);
                startEventoController.show((Pane) this.anchorHeader, (Pane) this.anchorScroll,this.stackPane, evento);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
    }

    public void createBracketTransition(ChaveTorneio chaveTorneio, Evento evento){

        if(this.tBracket == null) {

            try {
                FXMLLoader loaderEdit = new FXMLLoader(getClass().getResource("/br/com/application/apresentacao/TelaCreateBracket.fxml"));
                this.tBracket = loaderEdit.load();
                this.stackPane.getChildren().add(tBracket);
                this.stackPane.getChildren().set(4, tBracket);
                this.createBracketController = loaderEdit.getController();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.createBracketController.setVerEventosController(this);
        this.createBracketController.setChaveTorneio(chaveTorneio);
        this.createBracketController.setEvento(evento);
        pullToFront(this.tBracket);

    }

    public void deleteAndRefresh(Evento evento){

        try {

            dbEvento.delete(evento);
            renderEventos();

        } catch (DataBaseException e) {
            e.printStackTrace();
        }

    }

}
