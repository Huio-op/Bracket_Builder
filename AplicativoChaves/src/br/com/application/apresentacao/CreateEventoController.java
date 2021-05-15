package br.com.application.apresentacao;

import br.com.application.negocio.ChaveTorneio;
import br.com.application.negocio.Evento;
import br.com.application.negocio.Jogo;
import br.com.application.negocio.TipoTorneio;
import br.com.application.persistencia.DBChaveTorneio;
import br.com.application.persistencia.DBEvento;
import br.com.application.persistencia.DBJogo;
import br.com.application.persistencia.DBTipoTorneio;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.JFX.JFXErrorDialog;
import br.univates.system32.JFX.JFXInfoDialog;
import br.univates.system32.JFX.JFXTransitionHandler;
import br.univates.system32.JFX.JFXValidatorCreator;
import br.univates.system32.util.CurrencyField;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.converter.CurrencyStringConverter;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class CreateEventoController implements Initializable {

    @FXML
    private AnchorPane anchorBackgroundEvt;

    @FXML
    private StackPane stackPane;

    @FXML
    private JFXButton btnVoltar;

    @FXML
    private JFXTextField textNome;

    @FXML
    private JFXButton btnCreateEvento;

    @FXML
    private JFXTextArea textDesc;

    @FXML
    private JFXComboBox<String> comboJogo;

    @FXML
    private JFXButton btnCreateGame;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label lblDate;

    @FXML
    private AnchorPane anchorCurrency;

    @FXML
    private JFXTextField textNumPart;

    @FXML
    private JFXComboBox<String> comboTipoTorneio;

    JFXTransitionHandler th = new JFXTransitionHandler();
    CreateJogoController jogoController;
    DBJogo dbJogo = new DBJogo();
    DBTipoTorneio dbTipos = new DBTipoTorneio();
    CurrencyField currencyField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/application/apresentacao/TelaCreateJogo.fxml"));
            AnchorPane tJogo = loader.load();
            this.stackPane.getChildren().add(tJogo);
            this.stackPane.getChildren().get(1).toBack();
            this.jogoController = loader.getController();
            this.jogoController.setEventoController(this);

        } catch (IOException e) {
            e.printStackTrace();
            JFXErrorDialog error = new JFXErrorDialog((StackPane) anchorBackgroundEvt.getParent().getParent(),
                    anchorBackgroundEvt.getParent(), e);
            error.showDialogPane();
        }

        this.currencyField = new CurrencyField(new Locale("pt","BR"));
        anchorCurrency.getChildren().add(currencyField);
        currencyField.setPromptText(":Digite o valor do prêmio");
        currencyField.setLabelFloat(true);
        currencyField.setPrefWidth(190);
        currencyField.setPrefHeight(25);

        refreshComboJogo();
        refreshComboTypes();

        createNomeValidator();
        createPremioValidator();
        createJogoValidator();
        createDescricaoValidator();
        createNumPartValidator();

    }

    public void refreshComboJogo(){

        this.comboJogo.getItems().removeAll();

        try {
            ArrayList<Jogo> array = dbJogo.loadAll();
            if(!array.isEmpty()){
                for (Jogo jogo: array) {
                    this.comboJogo.getItems().add(jogo.getIdJogo() +"-"+ jogo.getNome());
                }
            }

        } catch (DataBaseException e) {
            e.printStackTrace();
            JFXErrorDialog error = new JFXErrorDialog((StackPane) anchorBackgroundEvt.getParent().getParent(),
                    anchorBackgroundEvt.getParent(), e);
            error.showDialogPane();
        } catch (SQLException e) {
            e.printStackTrace();
            JFXErrorDialog error = new JFXErrorDialog((StackPane) anchorBackgroundEvt.getParent().getParent(),
                    anchorBackgroundEvt.getParent(), e);
            error.showDialogPane();
        }
    }

    public void refreshComboTypes(){

        this.comboTipoTorneio.getItems().removeAll();

        try {
            ArrayList<TipoTorneio> arrayType  = dbTipos.loadAll();

            if(!arrayType.isEmpty()){
                for(TipoTorneio tipoTorneio : arrayType){
                    comboTipoTorneio.getItems().add(tipoTorneio.getId() + " - " + tipoTorneio.getNome());
                }
            }

        } catch (DataBaseException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }



    }

    public void returnPage(ActionEvent event){

        try {
            th.transitionFadeFXML(anchorBackgroundEvt, "/br/com/application/apresentacao/TelaHome.fxml",
                    1);
        } catch (IOException e) {
            e.printStackTrace();
            JFXErrorDialog error = new JFXErrorDialog((StackPane) anchorBackgroundEvt.getParent().getParent(),
                    anchorBackgroundEvt.getParent(), e);
            error.showDialogPane();
        }

    }

    public void createJogoTransition(ActionEvent event){

        JFXTransitionHandler.transitionFade(jogoController.getAnchorRoot(), JFXTransitionHandler.FADEIN, 1);
        this.stackPane.getChildren().get(0).toFront();
        jogoController.show((Pane) this.stackPane.getChildren().get(0));

    }

    public void createEvento(ActionEvent event) {

        if(this.datePicker.getValue() == null){

            this.lblDate.setText("Escolha uma data!");

        }else{

            this.lblDate.setText("");

            if(textNome.validate() && comboJogo.validate() && currencyField.validate()){

                try {

                    int idJogo = Integer.parseInt(comboJogo.getValue().split("-")[0]);

                    Jogo jogo = dbJogo.load(String.valueOf(idJogo));

                    Evento evento = new Evento(textNome.getText(), jogo, HomeController.organizador.getCpf(), textDesc.getText(),
                            currencyField.getAmount(), datePicker.getValue());
                    DBEvento dbEvento = new DBEvento();
                    dbEvento.save(evento);

                    int idTipoTorneio = Integer.parseInt(comboTipoTorneio.getValue().split("-")[0]);
                    ChaveTorneio chaveTorneio = new ChaveTorneio(idTipoTorneio, Integer.parseInt(textNumPart.getText()),
                            dbEvento.load(String.valueOf(evento.getId())).getId());
                    DBChaveTorneio dbChaveTorneio = new DBChaveTorneio();
                    dbChaveTorneio.save(chaveTorneio);

                    JFXButton btnSuccess = new JFXButton("Voltar à Home Page.");
                    btnSuccess.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {

                        textNome.setText("");
                        comboJogo.setValue("");
                        textDesc.setText("");
                        currencyField.setText("");
                        datePicker.setValue(null);
                        textNumPart.setText("");
                        comboJogo.setValue("");

                        returnPage(event);

                    });

                    JFXInfoDialog dialogSuccess = new JFXInfoDialog((StackPane) anchorBackgroundEvt.getParent().getParent(),
                            anchorBackgroundEvt.getParent(), "Sucesso!",
                            "Evento cadastrado com sucesso!", Arrays.asList(btnSuccess));
                    dialogSuccess.showDialogPane();


                } catch (DataBaseException e) {
                    e.printStackTrace();
                    JFXErrorDialog error = new JFXErrorDialog((StackPane) anchorBackgroundEvt.getParent().getParent(),
                            anchorBackgroundEvt.getParent(), e);
                    error.showDialogPane();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    JFXErrorDialog error = new JFXErrorDialog((StackPane) anchorBackgroundEvt.getParent().getParent(),
                            anchorBackgroundEvt.getParent(), throwables);
                    error.showDialogPane();
                }
            }

        }

    }

    private void createNomeValidator() {

        JFXValidatorCreator.createRequiredFieldValidator(textNome);

    }

    private void createJogoValidator(){

        RequiredFieldValidator rfValidator = new RequiredFieldValidator();
        rfValidator.setMessage("Este campo é obrigatório!");
        comboJogo.getValidators().add(rfValidator);

    }

    private void createDescricaoValidator(){

        textDesc.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (textDesc.getText().length() >498) {
                    String s = textDesc.getText().substring(0, 498);
                    textDesc.setText(s);
                }

            }
        });

    }

    private void createPremioValidator(){

        JFXValidatorCreator.createRequiredFieldValidator(this.currencyField);

    }

    private void createNumPartValidator(){

        textNumPart.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (textNumPart.getText().length() > 2 ) {
                    String s = textNumPart.getText().substring(0, 2);
                    textNumPart.setText(s);
                }
                if (!newValue.matches("\\d*")) {
                    textNumPart.setText(newValue.replaceAll("[^\\d]", ""));
                }

            }
        });

    }


}
