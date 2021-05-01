package br.com.application.apresentacao;

import br.com.application.apresentacao.validators.SameJogoValidator;
import br.univates.system32.JFX.JFXTransitionHandler;
import br.univates.system32.JFX.JFXValidatorCreator;
import com.jfoenix.animation.alert.CenterTransition;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class CreateJogoController implements Initializable {

    @FXML
    private AnchorPane anchorRoot;

    @FXML
    private JFXTextField textNome;

    private Pane paneToBlur;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        jogoValidator();

    }

    public void show(Pane paneToBlur){

        this.paneToBlur = paneToBlur;

        BoxBlur blur = new BoxBlur(3, 3, 3);

        paneToBlur.setEffect(blur);
        anchorRoot.setEffect(null);


    }

    public void close(){


        JFXTransitionHandler.transitionFade(anchorRoot, JFXTransitionHandler.FADEOUT, 1);
        paneToBlur.setEffect(null);
        this.anchorRoot.toBack();

    }

    public AnchorPane getAnchorRoot(){

        return anchorRoot;

    }

    private void jogoValidator(){

        SameJogoValidator sjValidator = new SameJogoValidator();

        JFXValidatorCreator.createCustomFieldValidator(textNome, Arrays.asList(sjValidator), true, false);

    }


}
