package br.univates.system32.JFX;

import java.util.Arrays;
import java.util.List;

import com.jfoenix.controls.JFXButton;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class JFXErrorDialog extends JFXInfoDialog{
	
	public JFXErrorDialog(StackPane stackpane, Node paneToBlur, Exception exception) {
		
		super(stackpane, paneToBlur, "ERRO!", exception.getMessage(), Arrays.asList(new JFXButton("Fechar")));
		
		this.controls.get(0).addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {

			System.exit(0);

		});
	}
	
//	public JFXErrorDialog(Exception exception) {
//		
//		super(stackpane, paneToBlur, "ERRO!", exception.getMessage(), Arrays.asList(new JFXButton("Fechar")));
//		
//		this.controls.get(0).addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
//
//			System.exit(0);
//
//		});
//	}
//	
}
