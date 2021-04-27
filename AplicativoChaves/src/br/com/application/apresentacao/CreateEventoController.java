package br.com.application.apresentacao;

import br.univates.system32.JFX.JFXErrorDialog;
import br.univates.system32.JFX.JFXTransitionHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateEventoController implements Initializable {

    @FXML
    private AnchorPane anchorBackgroundEvt;

    JFXTransitionHandler th = new JFXTransitionHandler();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }


    public void returnPage(ActionEvent event){

        try {
            th.transitionFadeFXML(anchorBackgroundEvt, "/br/com/application/apresentacao/TelaHome.fxml",
                    1);
        } catch (IOException e) {
            e.printStackTrace();
            JFXErrorDialog error = new JFXErrorDialog((StackPane) anchorBackgroundEvt.getParent().getParent().getParent(),
                    anchorBackgroundEvt.getParent().getParent(), e);
            error.showDialogPane();
        }

    }

}
