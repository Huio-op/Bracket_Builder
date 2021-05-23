package br.com.application.apresentacao;

import br.com.application.apresentacao.validators.EvenParticipantsValidator;
import br.com.application.negocio.ChaveTorneio;
import br.com.application.negocio.Evento;
import br.com.application.negocio.TipoTorneio;
import br.com.application.persistencia.DBChaveTorneio;
import br.com.application.persistencia.DBEvento;
import br.com.application.persistencia.DBTipoTorneio;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.JFX.JFXTransitionHandler;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StartEventoController implements Initializable {

    @FXML
    private AnchorPane anchorBack;

    @FXML
    private JFXComboBox<String> comboNumPart;

    @FXML
    private JFXComboBox<String> comboTipoTorneio;

    private DBTipoTorneio dbTipos = new DBTipoTorneio();
    private DBEvento dbEvento = new DBEvento();
    private Evento evento;
    VerEventosController verEventosController;
    private Pane paneToBlur;
    private Pane otherPaneToBlur;
    private StackPane stackPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        fillNumPart();
        refreshComboTypes();
//        createNumPartValidator();

    }

    public void startBracketCreation(ActionEvent event){

        if(comboNumPart != null && comboTipoTorneio != null){
            try {
                int qtdePart = Integer.parseInt(this.comboNumPart.getValue());
                int tipo = Integer.parseInt(this.comboTipoTorneio.getValue().split("-")[0]);
                ChaveTorneio chave = new ChaveTorneio(tipo, qtdePart, this.evento.getId());

                DBChaveTorneio dbChaveTorneio = new DBChaveTorneio();
                dbChaveTorneio.save(chave);

                verEventosController.createBracketTransition(chave, this.evento);
            } catch (DataBaseException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

//        if(textNumPart.validate() && comboTipoTorneio.getValue() != null){
//            try {
//                int qtdePart = Integer.parseInt(this.textNumPart.getText());
//                int tipo = Integer.parseInt(this.comboTipoTorneio.getValue().split("-")[0]);
//                ChaveTorneio chave = new ChaveTorneio(tipo, qtdePart, this.evento.getId());
//
//                DBChaveTorneio dbChaveTorneio = new DBChaveTorneio();
//                dbChaveTorneio.save(chave);
//
//                verEventosController.createBracketTransition(chave, this.evento);
//            } catch (DataBaseException e) {
//                e.printStackTrace();
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//        }

    }

    public void refreshComboTypes(){

        this.comboTipoTorneio.getItems().removeAll();

        try {
            ArrayList<TipoTorneio> arrayType  = dbTipos.loadAll();

            if(!arrayType.isEmpty()){
                for(TipoTorneio tipoTorneio : arrayType){
                    comboTipoTorneio.getItems().add(tipoTorneio.getId() + "-" + tipoTorneio.getNome());
                }
            }

        } catch (DataBaseException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void fillNumPart(){
        this.comboNumPart.getItems().add("2");
        this.comboNumPart.getItems().add("4");
        this.comboNumPart.getItems().add("8");
    }

    public AnchorPane getAnchorRoot(){

        return this.anchorBack;

    }

    public void setVerEventosController(VerEventosController verEventosController){

        this.verEventosController = verEventosController;

    }

    public void show(Pane paneToBlur, Pane otherPaneToBlur, StackPane stackPane, Evento evento){

        this.setEvento(evento);

        this.paneToBlur = paneToBlur;
        this.otherPaneToBlur = otherPaneToBlur;
        this.stackPane = stackPane;

        BoxBlur blur = new BoxBlur(3, 3, 3);

        paneToBlur.setEffect(blur);
        otherPaneToBlur.setEffect(blur);
        anchorBack.setEffect(null);


    }

    public void close(ActionEvent event){

        JFXTransitionHandler.transitionFade(anchorBack, JFXTransitionHandler.FADEOUT, 1);
        paneToBlur.setEffect(null);
        otherPaneToBlur.setEffect(null);
        stackPane.getChildren().set(3,this.anchorBack);

    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Evento getEvento(){
        return this.evento;
    }

//    private void createNumPartValidator(){
//
//        EvenParticipantsValidator evenParticipantsValidator = new EvenParticipantsValidator();
//        evenParticipantsValidator.setMessage("Digite um número par!");
//        textNumPart.getValidators().add(evenParticipantsValidator);
//
//        textNumPart.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
//                if (textNumPart.getText().length() > 2 ) {
//                    String s = textNumPart.getText().substring(0, 2);
//                    textNumPart.setText(s);
//                }
//                if (!newValue.matches("\\d*")) {
//                    textNumPart.setText(newValue.replaceAll("[^\\d]", ""));
//                }
//
//            }
//        });
//
//    }

}
