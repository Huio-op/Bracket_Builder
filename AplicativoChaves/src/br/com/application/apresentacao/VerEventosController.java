package br.com.application.apresentacao;

import br.com.application.negocio.Evento;
import br.com.application.persistencia.DBEvento;
import br.com.application.persistencia.filters.EventoFilterOwner;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.JFX.JFXErrorDialog;
import br.univates.system32.JFX.JFXTransitionHandler;
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

    JFXTransitionHandler th = new JFXTransitionHandler();
    DBEvento dbEvento;
    EventoMiniatureController eventoMiniatureController;
    EditEventoController editEventoController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dbEvento = new DBEvento();
        renderEventos();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/application/apresentacao/TelaEditEvento.fxml"));
            AnchorPane tJogo = loader.load();
            this.stackPane.getChildren().add(tJogo);
            this.stackPane.getChildren().get(2).toBack();
            this.editEventoController = loader.getController();
            this.editEventoController.setVerEventosController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        this.stackPane.getChildren().get(0).toFront();
        editEventoController.show((Pane) this.anchorHeader, (Pane) this.anchorScroll, evento);

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
