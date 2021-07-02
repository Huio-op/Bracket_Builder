package br.com.application.apresentacao;

import br.univates.system32.JFX.JFXTransitionHandler;
import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmController implements Initializable {

    @FXML
    private AnchorPane anchorBackgroundConfirm;

    @FXML
    private Label lblConfirmMessage;

    @FXML
    private JFXButton btnConfirm;

    private Pane paneToBlur;
    private Pane otherPaneToBlur;
    private StackPane stack;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void show(Pane paneToBlur, Pane otherPaneToBlur, StackPane stackPane, String message, EventHandler<MouseEvent> confirmEvent) {
        this.paneToBlur = paneToBlur;
        this.otherPaneToBlur = otherPaneToBlur;
        this.stack = stackPane;

        this.lblConfirmMessage.setText(message);
        this.btnConfirm.addEventHandler(MouseEvent.MOUSE_CLICKED, confirmEvent);

        BoxBlur blur = new BoxBlur(3, 3, 3);

        paneToBlur.setEffect(blur);
        otherPaneToBlur.setEffect(blur);
        anchorBackgroundConfirm.setEffect(null);
        anchorBackgroundConfirm.setOpacity(1);

    }

    public AnchorPane getAnchorBack() {
        return this.anchorBackgroundConfirm;
    }

    public void close() {
        JFXTransitionHandler.transitionFade(anchorBackgroundConfirm, JFXTransitionHandler.FADEOUT, 1);
        paneToBlur.setEffect(null);
        otherPaneToBlur.setEffect(null);
        stack.getChildren().get(stack.getChildren().indexOf(anchorBackgroundConfirm)).toBack();
        anchorBackgroundConfirm.setOpacity(0);
    }

}
