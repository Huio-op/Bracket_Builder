package br.com.application.apresentacao;

import br.com.application.negocio.ChaveTorneio;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateBracketController implements Initializable {

    @FXML
    private WebView webView;


    private VerEventosController verEventosController;
    private ChaveTorneio chaveTorneio;
    private WebEngine engine;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.engine = webView.getEngine();

    }

    public void setVerEventosController(VerEventosController verEventosController){
        this.verEventosController = verEventosController;
    }

    public void setChaveTorneio(ChaveTorneio chaveTorneio){
        this.chaveTorneio = chaveTorneio;
    }

}
