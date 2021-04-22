package br.com.application.apresentacao;

import br.com.application.apresentacao.validators.CPFValidator;
import br.com.application.apresentacao.validators.SameCPFValidator;
import br.com.application.apresentacao.validators.SameEmailValidator;
import br.com.application.negocio.Organizador;
import br.com.application.persistencia.DBOrganizador;
import br.univates.system32.CPF;
import br.univates.system32.DataBase.DataBaseException;
import br.univates.system32.JFX.JFXErrorDialog;
import br.univates.system32.JFX.JFXInfoDialog;
import br.univates.system32.JFX.JFXTransitionHandler;
import br.univates.system32.JFX.JFXValidatorCreator;
import br.univates.system32.util.MaskFieldUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class CreateOrganizadorController implements Initializable {

    @FXML
    private JFXCheckBox checkTerms;

    @FXML
    private AnchorPane anchorBackgroundOrg;

    @FXML
    private JFXTextField textCPF;

    @FXML
    private JFXComboBox<String> boxNationality;


    private JFXTransitionHandler th = new JFXTransitionHandler();
    private DBOrganizador dbo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Hyperlink link = new Hyperlink("Concordo com os Termos de Serviço");

        //checkTerms.setText(link);

        String[] countryCodes = Locale.getISOCountries();
        for (String countryCode : countryCodes) {

            Locale obj = new Locale("", countryCode);
            boxNationality.getItems().add(obj.getDisplayName());

        }


        CPFValidator();

        try {
            this.dbo = new DBOrganizador();
        } catch (DataBaseException e) {
            e.printStackTrace();
            JFXErrorDialog error = new JFXErrorDialog((StackPane) anchorBackgroundOrg.getParent().getParent().getParent(), anchorBackgroundOrg.getParent().getParent(), e);
            error.showDialogPane();
        }

    }

    public void createOrganizador(ActionEvent event){

        if(textCPF.validate() && checkTerms.isSelected() && boxNationality.getValue()!=null){
            boxNationality.setUnFocusColor(Color.rgb(77,77,77));

            try {
                CPF cpf = new CPF();
                cpf.setCPF(textCPF.getText());
                String nacionalidade = boxNationality.getValue();
                Organizador org = HomeController.user.turnOrganizador(cpf,nacionalidade);
                dbo.save(org);

                JFXButton btnSuccess = new JFXButton("Voltar à tela de Principal");
                btnSuccess.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {

                    returnPage(event);

                });

                JFXInfoDialog dialogSuccess = new JFXInfoDialog((StackPane) anchorBackgroundOrg.getParent().getParent().getParent().getParent(),
                        anchorBackgroundOrg.getParent().getParent().getParent(), "Sucesso!",
                        "Organizador criado com sucesso!", Arrays.asList(btnSuccess));
                dialogSuccess.showDialogPane();

//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/application/apresentacao/TelaHome.fxml"));
//                Parent root = (Parent) loader.load();
//                HomeController homeController = loader.getController();
//                homeController.refresh();
//
//                Stage stage = new Stage();
//                stage.setScene(new Scene(root));
//                stage.show();

            } catch (DataBaseException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }else if(boxNationality.getValue()==null){

            boxNationality.setUnFocusColor(Color.RED);

        }

    }

    public void returnPage(ActionEvent event){

        try {
            th.transitionFadeFXML((Pane)anchorBackgroundOrg.getParent(), "/br/com/application/apresentacao/TelaHomePage.fxml", 1);
        } catch (IOException e) {
            e.printStackTrace();
            JFXErrorDialog error = new JFXErrorDialog((StackPane) anchorBackgroundOrg.getParent().getParent().getParent(), anchorBackgroundOrg.getParent().getParent(), e);
            error.showDialogPane();
        }

    }

    private void CPFValidator(){

        // Valida se o texto colocado consiste em apenas números válido de acordo com o padrão regex
        // apresentado abaixo
        RegexValidator rxValidator = new RegexValidator();
        rxValidator.setRegexPattern(
                "^[0-9]*$");

        CPFValidator cpfValidator = new CPFValidator();
        cpfValidator.setMessage("Digite um CPF válido!");

        rxValidator.setMessage("Digite um CPF válido!");

        SameCPFValidator scValidator = new SameCPFValidator(false);

        scValidator.setMessage("CPF já cadastrado!");

        JFXValidatorCreator.createCustomFieldValidator(textCPF, Arrays.asList(rxValidator, cpfValidator, scValidator)
                , true, false);


        textCPF.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                    if (textCPF.getText().length() > 11) {
                        String s = textCPF.getText().substring(0, 11);
                        textCPF.setText(s);
                    }
                    if (!newValue.matches("\\d*")) {
                        textCPF.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });


    }

}
