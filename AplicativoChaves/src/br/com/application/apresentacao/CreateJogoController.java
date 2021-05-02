package br.com.application.apresentacao;

import br.com.application.apresentacao.validators.SameJogoValidator;
import br.com.application.negocio.Jogo;
import br.com.application.persistencia.DBJogo;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.JFX.JFXInfoDialog;
import br.univates.system32.JFX.JFXTransitionHandler;
import br.univates.system32.JFX.JFXValidatorCreator;
import com.jfoenix.animation.alert.CenterTransition;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

public class CreateJogoController implements Initializable {

    @FXML
    private AnchorPane anchorRoot;

    @FXML
    private JFXTextField textNome;

    @FXML
    private JFXButton btnImage;

    @FXML
    private ImageView imgPlus;

    private Pane paneToBlur;
    private File image;
    private DBJogo db;
    private CreateEventoController eventoController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        db = new DBJogo();

        jogoValidator();

    }

    public void imageChooser(ActionEvent event){

        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG Files", "*.jpg"));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
        File f = chooser.showOpenDialog(null);

        if(f != null){

            this.image = f;
            this.imgPlus.setImage(new Image(this.image.toURI().toString()));

        }

    }

    public void show(Pane paneToBlur){

        this.paneToBlur = paneToBlur;

        BoxBlur blur = new BoxBlur(3, 3, 3);

        paneToBlur.setEffect(blur);
        anchorRoot.setEffect(null);


    }

    public void createJogo(ActionEvent event){

        if(textNome.validate() && this.image != null){

            try {

                Jogo jogo = new Jogo(textNome.getText(), this.image);
                db.save(jogo);

                JFXButton btnSuccess = new JFXButton("OK");
                btnSuccess.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {

                    textNome.setText(null);
                    this.image = null;

                    eventoController.refreshComboJogo();

                    close();

                });

                JFXInfoDialog dialogSuccess = new JFXInfoDialog((StackPane) anchorRoot.getParent(), anchorRoot, "Sucesso!",
                        "Jogo adicionado com sucesso!", Arrays.asList(btnSuccess));
                dialogSuccess.showDialogPane();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (DataBaseException e) {
                e.printStackTrace();
            }

        }else if( image == null){



        }

    }

    public void close(){


        JFXTransitionHandler.transitionFade(anchorRoot, JFXTransitionHandler.FADEOUT, 1);
        paneToBlur.setEffect(null);
        this.anchorRoot.toBack();

    }

    public AnchorPane getAnchorRoot(){

        return anchorRoot;

    }

    private void jogoValidator(){

        SameJogoValidator sjValidator = new SameJogoValidator();

        JFXValidatorCreator.createCustomFieldValidator(textNome, Arrays.asList(sjValidator), true, false);

    }

    public void setEventoController(CreateEventoController eventoController){

        this.eventoController = eventoController;

    }


}
