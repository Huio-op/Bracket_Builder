/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.univates.system32.MyComponents;

import java.text.ParseException;
import java.util.Objects;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Felipe
 */
public class JMyPhoneNumberField extends JFormattedTextField
{
    public JMyPhoneNumberField() throws ParseException {
        setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("(##)9#### ####")));
    }
    
    public static boolean checkNumber(String number){
        boolean isNumber = false;
        
       number = number.replace(" ", "");
       number = number.replace("(", "");
       number = number.replace(")", "");
       
       
       if(number.length() == 11){
           isNumber = true;
       }
       
       return isNumber;
       
    }
    
}
