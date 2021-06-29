package br.com.application.apresentacao;

import br.com.application.negocio.ChaveTorneio;
import br.com.application.negocio.Participante;
import br.com.application.persistencia.DBParticipante;
import br.com.application.persistencia.filters.ParticipanteFilterBracket;
import br.univates.system32.DataBase.DataBaseException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ParticipanteMiniatureController implements Initializable {

    @FXML
    private StackPane stackPart;

    @FXML
    private JFXButton btnCriarParticipante;

    @FXML
    private AnchorPane anchorBlank;

    @FXML
    private AnchorPane anchorPart;

    @FXML
    private AnchorPane anchorButton;

    @FXML
    private Label lblNome;

    @FXML
    private Label lblPontos;

    @FXML
    private AnchorPane anchorCombo;

    @FXML
    private JFXComboBox<Participante> comboPart;

    @FXML
    private AnchorPane anchorPartConf;

    @FXML
    private Label lblNomeConf;

    @FXML
    private Label lblPontosConf;

    private final  DBParticipante dbParticipante = new DBParticipante();
    public CreateBracketController bracketController;
    public StartEventoController startEventoController;
    private Participante participante;
    private int id;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Callback<ListView<Participante>, ListCell<Participante>> factory = lv -> new ListCell<Participante>() {

            @Override
            protected void updateItem(Participante part, boolean empty) {
                super.updateItem(part, empty);
                setText(empty ? "" : part.getNome());
            }

        };

        this.comboPart.setCellFactory(factory);
        this.comboPart.setButtonCell(factory.call(null));
        this.comboPart.valueProperty().addListener(new ChangeListener<Participante>() {
            @Override
            public void changed(ObservableValue<? extends Participante> observableValue, Participante oldPart, Participante newPart) {
                setPartConfig(newPart);
            }
        });

    }

    public void setParticipante(Participante participante){

        this.participante = participante;
        this.lblNome.setText(participante.getNome());
        this.lblNome.setWrapText(true);
        this.lblPontos.setText(String.valueOf(participante.getPontos()));
        this.stackPart.getChildren().get(this.stackPart.getChildren().indexOf(anchorPart)).toFront();

    }

    public Participante getParticipante() { return this.participante; }

    public void setBlank() {
        this.stackPart.getChildren().get(this.stackPart.getChildren().indexOf(anchorBlank)).toFront();
    }

    public void setButton() {
        this.stackPart.getChildren().get(this.stackPart.getChildren().indexOf(anchorButton)).toFront();
    }

    public void setComboBox() {
        this.stackPart.getChildren().get(this.stackPart.getChildren().indexOf(anchorCombo)).toFront();
    }

    public void editPart() {
        bracketController.editParticipantTransistion(this.participante);
    }

    public void setPartConfig(Participante participante) {
        this.participante = participante;
        this.lblNomeConf.setText(participante.getNome());
        this.lblNomeConf.setWrapText(true);
        this.lblPontosConf.setText(String.valueOf(participante.getPontos()));
        this.stackPart.getChildren().get(this.stackPart.getChildren().indexOf(anchorPartConf)).toFront();
    }

    public void fillComboBox(ArrayList<Participante> arrayParticipante) {
            this.comboPart.getItems().addAll(arrayParticipante);

    }

    public void setId(int id) {

        this.id = id;

    }

    public int getId(){
        return this.id;
    }

    public void createParticipanteTransition(){
        this.startEventoController.createParticipanteChaveTransition(this);
    }

    public void setStartEventoController(StartEventoController startEventoController) { this.startEventoController = startEventoController; }

    public void setBracketController(CreateBracketController bracketController){ this.bracketController = bracketController; }


}
