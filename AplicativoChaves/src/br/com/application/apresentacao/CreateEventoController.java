package br.com.application.apresentacao;

import br.univates.system32.JFX.JFXErrorDialog;
import br.univates.system32.JFX.JFXTransitionHandler;
import br.univates.system32.JFX.JFXValidatorCreator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
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

    @FXML
    private JFXButton btnVoltar;

    @FXML
    private JFXTextField textNome;

    @FXML
    private JFXTextField textLocal;

    @FXML
    private JFXButton btnCreateEvento;

    @FXML
    private JFXTextArea textDesc;

    @FXML
    private JFXComboBox<?> comboJogo;

    @FXML
    private JFXButton btnCreateGame;

    JFXTransitionHandler th = new JFXTransitionHandler();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        createNomeValidator();

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

    public void createEvento(ActionEvent event) {



    }

    private void createNomeValidator() {

        JFXValidatorCreator.createRequiredFieldValidator(textNome);

    }


}
