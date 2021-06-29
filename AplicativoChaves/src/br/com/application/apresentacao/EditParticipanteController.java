package br.com.application.apresentacao;

import br.com.application.negocio.Evento;
import br.com.application.negocio.PartPosition;
import br.com.application.negocio.Participante;
import br.com.application.persistencia.DBParticipante;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.JFX.JFXInfoDialog;
import br.univates.system32.JFX.JFXTransitionHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
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
    final private DBParticipante dbParticipante = new DBParticipante();
    ParticipanteMiniatureController partController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createPointsValidator();
    }

    public void save(ActionEvent event) {

        try {
            if (!textPartName.getText().isEmpty()) {
                participante.setNome(textPartName.getText());
            }
            if (!textPartPoints.getText().isEmpty()) {
                participante.getPositionByMiniatureId(partController.getId()).setPontos(Integer.valueOf(textPartPoints.getText()));
            } else {
                participante.getPositionByMiniatureId(partController.getId()).setPontos(0);
            }

            dbParticipante.edit(participante);

            JFXButton btnSuccess = new JFXButton("Fechar.");
            btnSuccess.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {

                textPartName.setText("");
                textPartPoints.setText("");

                bracketController.renderParticipantes();
                closeEditParticipante(event);

            });

            JFXInfoDialog dialogSuccess = new JFXInfoDialog((StackPane) editPartBackAnchor.getParent(),
                    editPartBackAnchor, "Sucesso!",
                    "Participante editado com sucesso!", Arrays.asList(btnSuccess));
            dialogSuccess.showDialogPane();

        } catch (DataBaseException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void advanceParticipante(ActionEvent event) {

        this.participante.addPosition(new PartPosition(this.participante.getId(), this.partController.getWinPosition(), 0));
        this.save(event);
        closeEditParticipante(event);

    }

    public void setParticipante(Participante participante) {
        this.participante = participante;

        this.textPartName.setText(participante.getNome());
        this.textPartPoints.setText(String.valueOf(participante.getPositionByMiniatureId(partController.getId()).getPontos()));

    }

    public void setBracketController(CreateBracketController bracketController) {
        this.bracketController = bracketController;
    }

    public void show(Pane paneToBlur, Pane otherPaneToBlur, StackPane stackPane, ParticipanteMiniatureController partController) {

        this.partController = partController;
        this.setParticipante(partController.getParticipante());

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
        int index = this.stackPaneFather.getChildren().indexOf(this.editPartBackAnchor);
        this.stackPaneFather.getChildren().get(index).toBack();

    }

    public AnchorPane getAnchorRoot() {
        return this.editPartBackAnchor;
    }

    public void cleanPosition(ActionEvent event) {

        try {
            this.participante.setPosicao(0);
            dbParticipante.edit(participante);
            bracketController.renderParticipantes();
        } catch (DataBaseException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        closeEditParticipante(event);
    }

    public void createPointsValidator() {
        textPartPoints.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (textPartPoints.getText().length() > 4) {
                    String s = textPartPoints.getText().substring(0, 4);
                    textPartPoints.setText(s);
                }
                if (!newValue.matches("\\d*")) {
                    textPartPoints.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

}
