package br.com.application.apresentacao;

import br.com.application.negocio.Evento;
import br.com.application.negocio.Participante;
import br.univates.system32.JFX.JFXTransitionHandler;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class EditParticipanteController implements Initializable {

    @FXML
    private AnchorPane editPartBackAnchor;

    @FXML
    private JFXTextField textPartName;

    @FXML
    private JFXTextField textPartPoints;

    private CreateBracketController bracketController;
    private Participante participante;
    private Pane paneToBlur;
    private Pane otherPaneToBlur;
    private StackPane stackPaneFather;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void save() {

    }

    public void setParticipante(Participante participante) {
        this.participante = participante;

        this.textPartName.setPromptText(participante.getNome());
        this.textPartPoints.setPromptText(String.valueOf(participante.getPontos()));

    }

    public void setBracketController(CreateBracketController bracketController) {
        this.bracketController = bracketController;
    }

    public void show(Pane paneToBlur, Pane otherPaneToBlur, StackPane stackPane, Participante participate) {

        this.setParticipante(participate);

        this.paneToBlur = paneToBlur;
        this.otherPaneToBlur = otherPaneToBlur;
        this.stackPaneFather = stackPane;

        BoxBlur blur = new BoxBlur(3, 3, 3);

        paneToBlur.setEffect(blur);
        otherPaneToBlur.setEffect(blur);
        editPartBackAnchor.setEffect(null);


    }

    public void closeEditParticipante(ActionEvent event){

        JFXTransitionHandler.transitionFade(editPartBackAnchor, JFXTransitionHandler.FADEOUT, 1);
        paneToBlur.setEffect(null);
        otherPaneToBlur.setEffect(null);
        this.stackPaneFather.getChildren().set(2,this.editPartBackAnchor);

    }

    public AnchorPane getAnchorRoot() {
        return this.editPartBackAnchor;
    }

}
