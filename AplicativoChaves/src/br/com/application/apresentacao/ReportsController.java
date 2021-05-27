package br.com.application.apresentacao;

import br.com.application.negocio.Evento;
import br.com.application.resources.reports.BracketBuilderReports;
import br.univates.system32.JFX.JFXTransitionHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    @FXML
    private AnchorPane anchorBack;

    private Pane paneToBlur;
    private StackPane stackPaneFather;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void geEventoReport(ActionEvent event){

        BracketBuilderReports.generateReport(BracketBuilderReports.ReportType.EVENTO_REPORT);

    }

    public void getParticipanteReport(ActionEvent event){

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
        this.stackPaneFather.getChildren().set(1,this.anchorBack);

    }

}
