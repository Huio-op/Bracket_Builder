package br.com.application.apresentacao;

import br.com.application.negocio.Participante;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class ParticipanteMiniatureController implements Initializable {

    @FXML
    private StackPane stackPart;

    @FXML
    private JFXButton btnCriarParticipante;

    @FXML
    private AnchorPane anchorPart;

    @FXML
    private Label lblNome;

    @FXML
    private Label lblPontos;

    CreateBracketController bracketController;
    private Participante participante;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setParticipante(Participante participante){

        this.participante = participante;
        this.lblNome.setText(participante.getNome());
        this.lblPontos.setText(String.valueOf(participante.getPontos()));
        this.stackPart.getChildren().get(0).toFront();

    }

    public void createParticipanteTransition(){
        this.bracketController.createParticipanteTransition(this);
    }

    public void setBracketController(CreateBracketController bracketController){ this.bracketController = bracketController; }


}
