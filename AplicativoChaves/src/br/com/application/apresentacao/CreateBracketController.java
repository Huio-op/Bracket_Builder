package br.com.application.apresentacao;

import br.com.application.negocio.ChaveTorneio;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateBracketController implements Initializable {


    private VerEventosController verEventosController;
    private ChaveTorneio chaveTorneio;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setVerEventosController(VerEventosController verEventosController){
        this.verEventosController = verEventosController;
    }

    public void setChaveTorneio(ChaveTorneio chaveTorneio){
        this.chaveTorneio = chaveTorneio;
    }

}
