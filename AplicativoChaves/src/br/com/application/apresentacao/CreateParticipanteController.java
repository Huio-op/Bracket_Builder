package br.com.application.apresentacao;

import br.com.application.negocio.Evento;
import br.univates.system32.JFX.JFXTransitionHandler;
import br.univates.system32.JFX.JFXValidatorCreator;
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

public class CreateParticipanteController implements Initializable {

    @FXML
    private AnchorPane anchorRoot;

    @FXML
    private JFXTextField textNome;

    private CreateBracketController createBracketController;
    private Pane paneToBlur;
    private Pane otherPaneToBlur;
    private StackPane stack;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        textNomeValidator();

    }

    public void setCreateBacketController(CreateBracketController createBracketController){
        this.createBracketController = createBracketController;
    }

    public AnchorPane getAnchorRoot(){ return this.anchorRoot; }

    public void createParticipante(ActionEvent event){

    }

    public void show(Pane paneToBlur, Pane otherPaneToBlur, StackPane stackPane){

        this.paneToBlur = paneToBlur;
        this.otherPaneToBlur = otherPaneToBlur;
        this.stack = stackPane;

        BoxBlur blur = new BoxBlur(3, 3, 3);

        paneToBlur.setEffect(blur);
        otherPaneToBlur.setEffect(blur);
        anchorRoot.setEffect(null);


    }

    public void close(ActionEvent event){

        JFXTransitionHandler.transitionFade(anchorRoot, JFXTransitionHandler.FADEOUT, 1);
        paneToBlur.setEffect(null);
        otherPaneToBlur.setEffect(null);
        stack.getChildren().set(2,this.anchorRoot);

    }

    public void textNomeValidator(){

        JFXValidatorCreator.createRequiredFieldValidator(textNome);

    }


}
