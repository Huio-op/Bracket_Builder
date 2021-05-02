package br.com.application.apresentacao;

import br.com.application.negocio.Evento;
import br.com.application.negocio.Jogo;
import br.com.application.persistencia.DBEvento;
import br.com.application.persistencia.DBJogo;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.JFX.JFXErrorDialog;
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

    JFXTransitionHandler th = new JFXTransitionHandler();
    CreateJogoController jogoController;
    DBJogo dbJogo = new DBJogo();
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

        createNomeValidator();
        createPremioValidator();
        createJogoValidator();

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

        this.stackPane.getChildren();
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

                    Jogo jogo = dbJogo.load(comboJogo.getValue().split("-")[0]);

                    Evento evento = new Evento(textNome.getText(), jogo, HomeController.organizador.getCpf(), textDesc.getText(),
                            currencyField.getAmount(), datePicker.getValue());
                    DBEvento dbEvento = new DBEvento();

                    dbEvento.save(evento);

                    System.out.println("Salvou");


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

    private void createPremioValidator(){

        JFXValidatorCreator.createRequiredFieldValidator(this.currencyField);

    }


}
