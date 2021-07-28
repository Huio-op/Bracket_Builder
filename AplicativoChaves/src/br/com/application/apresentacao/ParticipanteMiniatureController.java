package br.com.application.apresentacao;

import br.com.application.negocio.ChaveTorneio;
import br.com.application.negocio.PartPosition;
import br.com.application.negocio.Participante;
import br.com.application.persistencia.DBPartPosition;
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
    private AnchorPane anchorWin;

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

    @FXML
    private Label lblNomeWin;

    private final DBParticipante dbParticipante = new DBParticipante();
    private final DBPartPosition dbPartPosition = new DBPartPosition();
    public CreateBracketController bracketController;
    public StartEventoController startEventoController;
    private Participante participante;
    private int id;
    private int col;
    private int pontos;
    private int[] winPosition = new int[2];

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
                setPartConfig(newPart, 0);
            }
        });

    }

    public void setParticipante(Participante participante){

        this.participante = participante;
        this.lblNome.setText(participante.getNome());
        this.lblNome.setWrapText(true);
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
        bracketController.editParticipantTransistion(this);
    }

    public void setPartConfig(Participante participante, int pontos) {

        if (this.participante != null) {
            try {
                this.dbPartPosition.delete(this.participante.getPositionByMiniatureId(this.col, this.id));
                this.participante.removePosition(this.participante.getPositionByMiniatureId(this.col, this.id));
            } catch (DataBaseException e) {
                e.printStackTrace();
            }
        }



        this.participante = participante;
        participante.addPosition(new PartPosition(participante.getId(), this.col, this.id, 0));
        this.lblNomeConf.setText(participante.getNome());
        this.lblNomeConf.setWrapText(true);
        this.lblPontosConf.setText(String.valueOf(pontos));
        if (this.winPosition[0] < 0) {
            this.stackPart.getChildren().get(this.stackPart.getChildren().indexOf(anchorWin)).toFront();
            this.lblNomeWin.setText(participante.getNome());
        } else {
            this.stackPart.getChildren().get(this.stackPart.getChildren().indexOf(anchorPartConf)).toFront();
        }
    }

    public void fillComboBox(ArrayList<Participante> arrayParticipante) {
            this.comboPart.getItems().addAll(arrayParticipante);

    }

    public void setId(int id, int col) {

        this.id = id;
        this.col = col;

    }

    public void setWinPosition(int winPosCol, int winPosLin) {
        this.winPosition[0] = winPosCol;
        this.winPosition[1] = winPosLin;
    }

    public int[] getWinPosition() {
        return this.winPosition;
    }

    public int getId(){
        return this.id;
    }

    public int getCol() { return this.col; }

    public int getPontos() { return this.pontos; }

    public void createParticipanteTransition(){
        this.startEventoController.createParticipanteChaveTransition(this);
    }

    public void setStartEventoController(StartEventoController startEventoController) { this.startEventoController = startEventoController; }

    public void setBracketController(CreateBracketController bracketController){ this.bracketController = bracketController; }


}
