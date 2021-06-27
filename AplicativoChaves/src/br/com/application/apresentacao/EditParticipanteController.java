package br.com.application.apresentacao;

import br.com.application.negocio.Evento;
import br.com.application.negocio.Participante;
import br.univates.system32.JFX.JFXTransitionHandler;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class EditParticipanteController implements Initializable {


    private CreateBracketController bracketController;
    private Participante participante;
    private Pane paneToBlur;
    private Pane otherPaneToBlur;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void save() {

    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

    public void setBracketController(CreateBracketController bracketController) {
        this.bracketController = bracketController;
    }

    public void show(Pane paneToBlur, Pane otherPaneToBlur, StackPane stackPane, Participante participate) {

        this.setParticipante(participante);

        this.paneToBlur = paneToBlur;
        this.otherPaneToBlur = otherPaneToBlur;
        this.stackPaneFather = stackPane;

        BoxBlur blur = new BoxBlur(3, 3, 3);

        paneToBlur.setEffect(blur);
        otherPaneToBlur.setEffect(blur);
        anchorBackgroundEditEvt.setEffect(null);


    }

    public void closeEditParticipante(ActionEvent event){

        JFXTransitionHandler.transitionFade(anchorBackgroundEditEvt, JFXTransitionHandler.FADEOUT, 1);
        paneToBlur.setEffect(null);
        otherPaneToBlur.setEffect(null);
        this.stackPaneFather.getChildren().set(3,this.anchorBackgroundEditEvt);

    }

}
