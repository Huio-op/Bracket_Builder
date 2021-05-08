package br.com.application.apresentacao;

import br.com.application.negocio.Evento;
import br.com.application.negocio.Jogo;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class EventoMiniatureController implements Initializable {

    @FXML
    private Label lblNome;

    @FXML
    private Label lblJogo;

    @FXML
    private Label lblData;

    @FXML
    private JFXTextArea textDescricao;

    private Evento evento;
    private Jogo jogo;
    private VerEventosController verEventosController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }

    public void setEvento(Evento evento){

        this.evento = evento;
        this.lblNome.setText(evento.getNome());
        this.lblJogo.setText("Jogo: "+ evento.getJogo().getNome());
        this.lblData.setText("Data: "+ evento.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.textDescricao.setText(evento.getDetalhes());
    }

    public void setVerEventosController(VerEventosController verEventosController){

        this.verEventosController = verEventosController;

    }

    public void createBracket(ActionEvent event){



    }

    public void edit(ActionEvent event){


    }

    public void delete(ActionEvent event){

        verEventosController.deleteAndRefresh(this.evento);

    }

}
