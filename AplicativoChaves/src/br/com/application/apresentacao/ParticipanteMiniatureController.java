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
    private AnchorPane anchorBlank;

    @FXML
    private AnchorPane anchorPart;

    @FXML
    private AnchorPane anchorButton;

    @FXML
    private Label lblNome;

    @FXML
    private Label lblPontos;

    CreateBracketController bracketController;
    private Participante participante;
    private int id;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setParticipante(Participante participante){

        this.participante = participante;
        this.lblNome.setText(participante.getNome());
        this.lblPontos.setText(String.valueOf(participante.getPontos()));
        this.stackPart.getChildren().get(this.stackPart.getChildren().indexOf(anchorPart)).toFront();

    }

    public Participante getParticipante(){ return this.participante; }

    public void setBlank(){
        this.stackPart.getChildren().get(this.stackPart.getChildren().indexOf(anchorBlank)).toFront();
    }

    public void setButton(){
        this.stackPart.getChildren().get(this.stackPart.getChildren().indexOf(anchorButton)).toFront();
    }

    public void setId(int id){

        this.id = id;

    }

    public int getId(){
        return this.id;
    }

    public void createParticipanteTransition(){
        this.bracketController.createParticipanteTransition(this);
    }

    public void setBracketController(CreateBracketController bracketController){ this.bracketController = bracketController; }


}
