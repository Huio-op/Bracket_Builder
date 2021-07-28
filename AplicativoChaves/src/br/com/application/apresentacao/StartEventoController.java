package br.com.application.apresentacao;

import br.com.application.negocio.ChaveTorneio;
import br.com.application.negocio.Evento;
import br.com.application.negocio.Participante;
import br.com.application.negocio.TipoTorneio;
import br.com.application.persistencia.DBChaveTorneio;
import br.com.application.persistencia.DBEvento;
import br.com.application.persistencia.DBParticipante;
import br.com.application.persistencia.DBTipoTorneio;
import br.com.application.persistencia.filters.ChaveFilterEvento;
import br.com.application.persistencia.filters.ParticipanteFilterBracket;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.JFX.JFXTransitionHandler;
import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StartEventoController implements Initializable {

    @FXML
    private AnchorPane anchorBack;

    @FXML
    private StackPane stackPane;

    @FXML
    private AnchorPane anchorParticipants;

    @FXML
    private AnchorPane anchorBracket;

    @FXML
    private JFXComboBox<String> comboNumPart;

    @FXML
    private JFXComboBox<String> comboTipoTorneio;

    @FXML
    private VBox boxParticipantes;

    private final DBTipoTorneio dbTipos = new DBTipoTorneio();
    private final DBChaveTorneio dbChaveTorneio = new DBChaveTorneio();
    private final DBEvento dbEvento = new DBEvento();
    private final DBParticipante dbParticipante = new DBParticipante();
    private final JFXTransitionHandler th = new JFXTransitionHandler();
    private Evento evento;
    private VerEventosController verEventosController;
    private AnchorPane tPart;
    private CreateParticipanteController createParticipanteController;
    private ParticipanteMiniatureController participanteMiniatureController;
    private Pane paneToBlur;
    private Pane otherPaneToBlur;
    private StackPane parentStackPane;
    private ChaveTorneio chaveTorneio;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {

            FXMLLoader loaderStart = new FXMLLoader(getClass().getResource("/br/com/application/apresentacao/TelaCreateParticipante.fxml"));
            this.tPart = loaderStart.load();
            this.stackPane.getChildren().add(tPart);
            this.stackPane.getChildren().set(2,tPart);
            this.createParticipanteController = loaderStart.getController();
            this.createParticipanteController.setStartEventoController(this);
            this.tPart.setOpacity(0);

            pullToFront(anchorBracket);

        } catch (IOException e) {
            e.printStackTrace();
        }

        fillNumPart();
        refreshComboTypes();
//        createNumPartValidator();

    }

    public void startBracketCreation(ActionEvent event){

        try {
            if(comboNumPart.getValue() != null && comboTipoTorneio.getValue() != null){
                int qtdePart = Integer.parseInt(this.comboNumPart.getValue());
                int tipo = Integer.parseInt(this.comboTipoTorneio.getValue().split("-")[0]);

                if(this.chaveTorneio == null){
                    ChaveTorneio chave = new ChaveTorneio(tipo, qtdePart, this.evento.getId());
                    dbChaveTorneio.save(chave);

                    this.chaveTorneio = dbChaveTorneio.loadFiltered(new ChaveFilterEvento(this.evento.getId())).get(0);

                }else{
                    this.chaveTorneio = dbChaveTorneio.loadFiltered(new ChaveFilterEvento(this.evento.getId())).get(0);
                    dbChaveTorneio.edit(this.chaveTorneio);
                }

                transitionCreateParticipants(this.chaveTorneio.getQuantidadeParticipantes());

//                verEventosController.renderEventos();
//                this.close(event);
            }

//                verEventosController.renderEventos();
//                this.close(event);
        } catch (DataBaseException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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

    public void createBracket(ActionEvent event) {

        try {
            if(dbChaveTorneio.isFilled(chaveTorneio)) {
                this.chaveTorneio.setComecou(true);
                dbChaveTorneio.edit(chaveTorneio);
                close(event);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (DataBaseException e) {
            e.printStackTrace();
        }

    }

    public ChaveTorneio getChaveTorneio() {
        return this.chaveTorneio;
    }

    public void transitionCreateParticipants(int numeroParticipantes) {
        try {

            ArrayList<ChaveTorneio> arrayChave = dbChaveTorneio.loadFiltered(new ChaveFilterEvento(this.evento.getId()));

            if (arrayChave.isEmpty()){
                this.chaveTorneio = arrayChave.get(0);
            }

            ArrayList<Participante> arrayPart = dbParticipante.loadFiltered(new ParticipanteFilterBracket(this.chaveTorneio));
            for(int i = 0; i < numeroParticipantes; i++) {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/application/apresentacao/ParticipanteMiniature.fxml"));
                    AnchorPane pMiniature = loader.load();
                    this.participanteMiniatureController = loader.getController();
                    participanteMiniatureController.setStartEventoController(this);
                    participanteMiniatureController.setButton();
                    boxParticipantes.getChildren().add(pMiniature);

                    if(!arrayPart.isEmpty() && i < arrayPart.size()) {
                        participanteMiniatureController.setParticipante(arrayPart.get(i));
                    }

            }

            th.transitionFadeExpand(anchorBracket, JFXTransitionHandler.FADEOUT,
                    0.5, anchorBracket.getPrefWidth(), anchorBracket.getPrefHeight(),
                    anchorParticipants.getPrefWidth(), anchorParticipants.getPrefHeight());

            anchorParticipants.toFront();

            th.transitionFadeExpand(anchorParticipants, JFXTransitionHandler.FADEIN,
                    0.5, anchorBracket.getPrefWidth(), anchorBracket.getPrefHeight(),
                    anchorParticipants.getPrefWidth(), anchorParticipants.getPrefHeight());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
    }

    public void transitionCreateBracket(ActionEvent event) {

        this.boxParticipantes.getChildren().clear();

        th.transitionFadeExpand(anchorParticipants, JFXTransitionHandler.FADEOUT,
                0.5, anchorParticipants.getPrefWidth(), anchorParticipants.getPrefHeight(),
                anchorBracket.getPrefWidth(), anchorBracket.getPrefHeight());

        anchorBracket.toFront();

        th.transitionFadeExpand(anchorBracket, JFXTransitionHandler.FADEIN,
                0.5, anchorParticipants.getPrefWidth(), anchorParticipants.getPrefHeight(),
                anchorBracket.getPrefWidth(), anchorBracket.getPrefHeight());

    }

    public void createParticipanteChaveTransition(ParticipanteMiniatureController miniature){

        JFXTransitionHandler.transitionFade(createParticipanteController.getAnchorRoot(), JFXTransitionHandler.FADEIN, 1);
        pullToFront(this.tPart);
        createParticipanteController.show((Pane) this.anchorParticipants, (Pane) this.anchorBracket, this.stackPane, miniature);

    }

    public void pullToFront(Object object){
        int index = this.stackPane.getChildren().indexOf(object);
        this.stackPane.getChildren().get(index).toFront();
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
        this.comboNumPart.getItems().add("16");
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
        this.parentStackPane = stackPane;

        BoxBlur blur = new BoxBlur(3, 3, 3);

        paneToBlur.setEffect(blur);
        otherPaneToBlur.setEffect(blur);
        anchorBack.setEffect(null);

    }

    public void showStarted(Pane paneToBlur, Pane otherPaneToBlur, StackPane stackPane, Evento evento, ChaveTorneio chave) {

        show(paneToBlur, otherPaneToBlur, stackPane, evento);

        this.chaveTorneio = chave;

        transitionCreateParticipants(chave.getQuantidadeParticipantes());

    }

    public void close(ActionEvent event){

        JFXTransitionHandler.transitionFade(anchorBack, JFXTransitionHandler.FADEOUT, 1);
        paneToBlur.setEffect(null);
        otherPaneToBlur.setEffect(null);
        parentStackPane.getChildren().set(3,this.anchorBack);

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
