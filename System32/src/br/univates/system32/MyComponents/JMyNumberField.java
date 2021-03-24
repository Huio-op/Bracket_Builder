package br.univates.system32.MyComponents;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import br.univates.system32.util.NumericDocumentFilter;

public class JMyNumberField extends JTextField{

	public JMyNumberField(int maxNumbers) {
		
		super(maxNumbers);
		((AbstractDocument) this.getDocument()).setDocumentFilter(new NumericDocumentFilter(maxNumbers));
		setHorizontalAlignment(CENTER);
		
	}
	
	
}


