package br.com.application.apresentacao;

import br.univates.system32.JFX.JFXValidatorCreator;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateParticipanteController implements Initializable {

    @FXML
    private JFXTextField textNome;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        textNomeValidator();

    }

    public void createParticipante(ActionEvent event){

    }

    public void close(ActionEvent event){

    }

    public void textNomeValidator(){

        JFXValidatorCreator.createRequiredFieldValidator(textNome);

    }


}
