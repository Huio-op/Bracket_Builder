package br.com.application.apresentacao;

import br.com.application.negocio.Evento;
import br.com.application.negocio.Jogo;
import br.com.application.persistencia.DBEvento;
import br.com.application.persistencia.DBJogo;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class EditEventoController implements Initializable {

    @FXML
    private AnchorPane anchorBackgroundEditEvt;

    @FXML
    private StackPane stackPane;

    @FXML
    private JFXTextField textNome;

    @FXML
    private JFXComboBox<String> comboJogo;

    @FXML
    private JFXTextArea textDesc;

    @FXML
    private AnchorPane anchorPremio;

    @FXML
    private DatePicker datePicker;

    @FXML
    private JFXButton btnSave;

    @FXML
    private Label lblDate;

    private CurrencyField currencyField;

    private Evento evento;
    private DBJogo dbJogo = new DBJogo();
    JFXTransitionHandler th = new JFXTransitionHandler();
    private Pane paneToBlur;
    private Pane otherPaneToBlur;
    private VerEventosController verEventosController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.comboJogo.getItems().removeAll();

        try {
            ArrayList<Jogo> array = dbJogo.loadAll();
            if(!array.isEmpty()){
                for (Jogo jogo: array) {
                    this.comboJogo.getItems().add(jogo.getIdJogo() +"-"+ jogo.getNome());
                }
            }

        }catch (DataBaseException e) {
            e.printStackTrace();
            JFXErrorDialog error = new JFXErrorDialog((StackPane) anchorBackgroundEditEvt.getParent().getParent(),
                    anchorBackgroundEditEvt.getParent(), e);
            error.showDialogPane();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            JFXErrorDialog error = new JFXErrorDialog((StackPane) anchorBackgroundEditEvt.getParent().getParent(),
                    anchorBackgroundEditEvt.getParent(), throwables);
            error.showDialogPane();
        }

        this.currencyField = new CurrencyField(new Locale("pt","BR"));
        anchorPremio.getChildren().add(currencyField);
        currencyField.setPrefWidth(190);
        currencyField.setPrefHeight(25);

        createNomeValidator();
        createJogoValidator();
        createDescricaoValidator();
        createPremioValidator();

    }

    public void setEvento(Evento evento){

        this.evento = evento;
        this.textNome.setText(this.evento.getNome());
        String evtJogo = this.evento.getJogo().getIdJogo() + "-" + this.evento.getJogo().getNome();
        this.comboJogo.setValue(evtJogo);
        this.textDesc.setText(this.evento.getDescricao());
        this.currencyField.setText(this.evento.getPremio().toString());
        this.datePicker.setValue(this.evento.getData());

    }

    public void save(ActionEvent event){

        if(this.datePicker.getValue() == null){

            this.lblDate.setText("Escolha uma data!");

        }else{

            this.lblDate.setText("");

            if(textNome.validate() && comboJogo.validate() && currencyField.validate()){

                try {

                    int idJogo = Integer.parseInt(comboJogo.getValue().split("-")[0]);

                    Jogo jogo = dbJogo.load(String.valueOf(idJogo));

                    Evento evento = new Evento(this.evento.getId(), textNome.getText(), jogo, HomeController.organizador.getCpf(), textDesc.getText(),
                            currencyField.getAmount(), datePicker.getValue());
                    DBEvento dbEvento = new DBEvento();

                    dbEvento.edit(evento);

                    JFXButton btnSuccess = new JFXButton("Fechar.");
                    btnSuccess.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {

                        textNome.setText("");
                        comboJogo.setValue("");
                        textDesc.setText("");
                        currencyField.setText("");
                        datePicker.setValue(null);

                        verEventosController.renderEventos();
                        close(event);

                    });

                    JFXInfoDialog dialogSuccess = new JFXInfoDialog((StackPane) anchorBackgroundEditEvt.getParent(),
                            anchorBackgroundEditEvt, "Sucesso!",
                            "Evento editado com sucesso!", Arrays.asList(btnSuccess));
                    dialogSuccess.showDialogPane();


                } catch (DataBaseException e) {
                    e.printStackTrace();
                    JFXErrorDialog error = new JFXErrorDialog((StackPane) anchorBackgroundEditEvt.getParent(),
                            anchorBackgroundEditEvt, e);
                    error.showDialogPane();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    JFXErrorDialog error = new JFXErrorDialog((StackPane) anchorBackgroundEditEvt.getParent(),
                            anchorBackgroundEditEvt, throwables);
                    error.showDialogPane();
                }
            }

        }

    }

    public AnchorPane getAnchorRoot(){

        return this.anchorBackgroundEditEvt;

    }

    public void setVerEventosController(VerEventosController verEventosController){

        this.verEventosController = verEventosController;

    }

    public void show(Pane paneToBlur, Pane otherPaneToBlur, Evento evento){

        this.setEvento(evento);

        this.paneToBlur = paneToBlur;
        this.otherPaneToBlur = otherPaneToBlur;

        BoxBlur blur = new BoxBlur(3, 3, 3);

        paneToBlur.setEffect(blur);
        otherPaneToBlur.setEffect(blur);
        anchorBackgroundEditEvt.setEffect(null);


    }

    public void close(ActionEvent event){

        JFXTransitionHandler.transitionFade(anchorBackgroundEditEvt, JFXTransitionHandler.FADEOUT, 1);
        paneToBlur.setEffect(null);
        otherPaneToBlur.setEffect(null);
        this.anchorBackgroundEditEvt.toBack();

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

}
