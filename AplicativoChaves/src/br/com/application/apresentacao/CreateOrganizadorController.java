package br.com.application.apresentacao;

import br.com.application.apresentacao.validators.CPFValidator;
import br.com.application.apresentacao.validators.SameCPFValidator;
import br.com.application.apresentacao.validators.SameEmailValidator;
import br.univates.system32.CPF;
import br.univates.system32.JFX.JFXValidatorCreator;
import br.univates.system32.util.MaskFieldUtil;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class CreateOrganizadorController implements Initializable {

    @FXML
    private JFXCheckBox checkTerms;

    @FXML
    private JFXTextField textCPF;

    @FXML
    private JFXComboBox<String> boxNationality;

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

    }

    public void createOrganizador(ActionEvent event){

        if(textCPF.validate() && checkTerms.isSelected() && boxNationality.getValue()!=null){
            boxNationality.setUnFocusColor(Color.rgb(77,77,77));
            System.out.println("YAY");

        }else if(boxNationality.getValue()==null){

            boxNationality.setUnFocusColor(Color.RED);

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

        scValidator.setMessage("Email não cadastrado!");

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
