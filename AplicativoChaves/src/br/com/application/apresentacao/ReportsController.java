package br.com.application.apresentacao;

import br.com.application.negocio.Evento;
import br.com.application.negocio.TipoTorneio;
import br.com.application.persistencia.DBEvento;
import br.com.application.persistencia.DBJogo;
import br.com.application.persistencia.DBOrganizador;
import br.com.application.persistencia.DBTipoTorneio;
import br.com.application.resources.reports.BracketBuilderReports;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.JFX.JFXTransitionHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    @FXML
    private AnchorPane anchorBack;

    @FXML
    private JFXButton btnVoltar;

    @FXML
    private JFXComboBox<String> comboEvento;

    @FXML
    private JFXTextField textFiltroNome;

    @FXML
    private JFXComboBox<String> comboOrganizador;

    @FXML
    private JFXComboBox<String> comboTipoTorneio;

    @FXML
    private JFXComboBox<String> comboJogo;

    private Pane paneToBlur;
    private StackPane stackPaneFather;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try {

            DBEvento dbEvento = new DBEvento();
            ArrayList<String> arrayEvento = dbEvento.loadAllNameAndId();
            for (String selecao: arrayEvento) {
                comboEvento.getItems().add(selecao);
            }

            DBJogo dbJogo = new DBJogo();
            ArrayList<String> arrayJogo = dbJogo.loadAllNameAndId();
            for (String selecao: arrayJogo) {
                comboJogo.getItems().add(selecao);
            }

            DBOrganizador dbOrganizador = new DBOrganizador();
            ArrayList<String> arrayOrganizador = dbOrganizador.loadAllName();
            for (String selecao: arrayOrganizador) {
                comboOrganizador.getItems().add(selecao);
            }

            DBTipoTorneio dbTipoTorneio = new DBTipoTorneio();
            ArrayList<TipoTorneio> arrayType  = dbTipoTorneio.loadAll();
            for(TipoTorneio tipoTorneio : arrayType){
                comboTipoTorneio.getItems().add(tipoTorneio.getId() + "- " + tipoTorneio.getNome());
            }

        } catch (DataBaseException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void geEventoListing(ActionEvent event){

        BracketBuilderReports.generateReport(BracketBuilderReports.ReportType.EVENTO_REPORT);

    }

    public void getParticipanteListing(ActionEvent event){

        BracketBuilderReports.generateReport(BracketBuilderReports.ReportType.PARTICIPANTE_REPORT);

    }

    public void show(Pane paneToBlur, StackPane stackPane){

        this.paneToBlur = paneToBlur;
        this.stackPaneFather = stackPane;

        BoxBlur blur = new BoxBlur(3, 3, 3);

        paneToBlur.setEffect(blur);
        anchorBack.setEffect(null);

    }

    public void close(ActionEvent event){

        JFXTransitionHandler.transitionFade(anchorBack, JFXTransitionHandler.FADEOUT, 1);
        paneToBlur.setEffect(null);
        this.stackPaneFather.getChildren().get(this.stackPaneFather.getChildren().indexOf(anchorBack)).toBack();

    }

}
