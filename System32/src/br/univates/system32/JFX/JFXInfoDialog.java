package br.univates.system32.JFX;

import java.util.List;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.events.JFXDialogEvent;

import javafx.scene.input.MouseEvent;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class JFXInfoDialog {

	protected StackPane panel;
	protected String header;
	protected String body;
	protected List<JFXButton> controls;
	protected Node blurPane;

	public JFXInfoDialog(StackPane stackpane, Node paneToBlur, String header, String body, List<JFXButton> controls) {

		this.panel = stackpane;
		this.header = header;
		this.body = body;
		this.controls = controls;
		this.blurPane = paneToBlur;
	}

	public JFXInfoDialog(String header, String body, List<JFXButton> controls) {

		this.header = header;
		this.body = body;
		this.controls = controls;
	}

	public JFXDialog crateDialogPane() {

		JFXDialogLayout layout = new JFXDialogLayout();

		Text textHeader;
		if (header.isBlank()) {
			textHeader = new Text("Sucesso!");
		} else {
			textHeader = new Text(this.header);
		}

		textHeader.setFill(Color.WHITE);

		layout.setHeading(textHeader);

		Text textBody;
		if (header.isBlank()) {
			textBody = new Text("Operação Concluída com sucesso!");
		} else {
			textBody = new Text(this.body);
		}
		textBody.setFill(Color.WHITE);
		layout.setBody(textBody);

		layout.getStyleClass().add("dialog-layout");

		JFXDialog dialogPane = new JFXDialog(panel, layout, JFXDialog.DialogTransition.CENTER);

		controls.forEach(controlButton -> {

			controlButton.getStyleClass().add("control-button");
			controlButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {

				dialogPane.close();

			});

		});

		layout.setActions(controls);

		return dialogPane;

	}

	public void showDialogPane() {

		BoxBlur blur = new BoxBlur(3, 3, 3);

		JFXDialog dialogPane = crateDialogPane();
		dialogPane.show();

		dialogPane.setOnDialogClosed((JFXDialogEvent e) -> {

			blurPane.setEffect(null);

		});

		blurPane.setEffect(blur);
		dialogPane.setEffect(null);

	}

}
