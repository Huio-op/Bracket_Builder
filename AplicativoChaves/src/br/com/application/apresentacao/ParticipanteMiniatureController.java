package br.com.application.apresentacao;

import br.com.application.negocio.Participante;
import javafx.fxml.Initializable;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class ParticipanteMiniatureController implements Initializable {

    CreateBracketController bracketController;
    private Participante participante;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void createParticipanteTransition(){
        this.bracketController.createParticipanteTransition(1);
    }

    public void setBracketController(CreateBracketController bracketController){ this.bracketController = bracketController; }


}
