package br.univates.system32.MyComponents;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

public class JMyCurrencyField extends JFormattedTextField{

	public JMyCurrencyField() {
		
		setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("R$ #,##0.00"))));
		NumberFormatter nf = (NumberFormatter) this.getFormatter();
		nf.setAllowsInvalid(false);
	}
	
}
