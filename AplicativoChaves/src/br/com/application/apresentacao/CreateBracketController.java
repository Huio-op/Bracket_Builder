package br.com.application.apresentacao;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateBracketController implements Initializable {


    private VerEventosController verEventosController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setVerEventosController(VerEventosController verEventosController){
        this.verEventosController = verEventosController;
    }

}
