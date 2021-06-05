package br.com.application.apresentacao;

import br.com.application.negocio.Evento;
import br.com.application.negocio.Participante;
import br.com.application.persistencia.DBParticipante;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.JFX.JFXTransitionHandler;
import br.univates.system32.JFX.JFXValidatorCreator;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateParticipanteController implements Initializable {

    @FXML
    private AnchorPane anchorRoot;

    @FXML
    private JFXTextField textNome;

    private CreateBracketController createBracketController;
    private StartEventoController startEventoController;
    private Pane paneToBlur;
    private Pane otherPaneToBlur;
    private StackPane stack;
    private DBParticipante dbParticipante = new DBParticipante();
    private ParticipanteMiniatureController miniature;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        textNomeValidator();

    }

    public void setCreateBacketController(CreateBracketController createBracketController) {
        this.createBracketController = createBracketController;
    }

    public void setStartEventoController(StartEventoController startEventoController) {
        this.startEventoController = startEventoController;
    }

    public AnchorPane getAnchorRoot(){ return this.anchorRoot; }

    public void createParticipante(ActionEvent event){

        if(textNome.validate()){


            try {
                Participante p = new Participante(textNome.getText(), miniature.getId(),0,startEventoController.getChaveTorneio().getId());
                dbParticipante.save(p);

                this.miniature.setParticipante(p);
                this.textNome.setText("");
                closeCreatePart(event);

            } catch (DataBaseException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

    }

    public void show(Pane paneToBlur, Pane otherPaneToBlur, StackPane stackPane, ParticipanteMiniatureController miniature){

        this.paneToBlur = paneToBlur;
        this.otherPaneToBlur = otherPaneToBlur;
        this.stack = stackPane;
        this.miniature = miniature;

        BoxBlur blur = new BoxBlur(3, 3, 3);

        paneToBlur.setEffect(blur);
        otherPaneToBlur.setEffect(blur);
        anchorRoot.setEffect(null);
        anchorRoot.setOpacity(1);


    }

    public void closeCreatePart(ActionEvent event){

        JFXTransitionHandler.transitionFade(anchorRoot, JFXTransitionHandler.FADEOUT, 1);
        paneToBlur.setEffect(null);
        otherPaneToBlur.setEffect(null);
        stack.getChildren().get(stack.getChildren().indexOf(anchorRoot)).toBack();
        anchorRoot.setOpacity(0);

    }

    public void textNomeValidator(){

        JFXValidatorCreator.createRequiredFieldValidator(textNome);

    }


}
