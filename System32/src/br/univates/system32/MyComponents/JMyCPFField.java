package br.univates.system32.MyComponents;

import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

public class JMyCPFField extends JFormattedTextField{

	public JMyCPFField() throws ParseException {
		setFormatterFactory(new DefaultFormatterFactory(new MaskFormatter("###.###.###-##")));
		setCaretPosition(0);
	}
	
}
