package br.univates.system32.JFX;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Time;
import java.util.Stack;


public class JFXTransitionHandler {

	public final static int FADEIN = 1;
	public final static int FADEOUT = 2;
	
	/*
	 * Método para fazer a transição de mudança de tamanho e fade do painel. Para realizar esta transição
	 * é necessário passar o painel desejado que pertence à classe Pane do JavaFX, e as
	 * especificações necessária para a transição ser realizada. 
	 * 
	 * @param pane um painel do JavaFX que pertence à classe Pane
	 * 
	 * @param typeFade recebe uma variavel do tipo transitionType de fade, podendo
	 * ser FADEIN ou FADEOUT
	 * 
	 * @param duration recebe o tempo em segundos que para que a transição finalize
	 * 
	 * @param startingWidth dita a largura inicial do painel
	 * 
	 * @param startingHeight dita a altura inicial do painel
	 * 
	 * @param endWidth dita a largura final do painel quando a transição acabar
	 * 
	 * @param endHeight dita a altura final do painel quando a transição acabar
	 */
	public static void transitionFadeExpand(Pane pane, int typeFade, double duration,
			double startingWidth, double startingHeight, double endWidth, double endHeight) {

		Timeline timeline = new Timeline();

		timeline.getKeyFrames().addAll(

				new KeyFrame(Duration.ZERO, 
						new KeyValue(pane.maxHeightProperty(), startingHeight)),

				new KeyFrame(Duration.ZERO, 
						new KeyValue(pane.maxWidthProperty(), startingWidth)),

				new KeyFrame(Duration.seconds(duration), 
						new KeyValue(pane.maxHeightProperty(), endHeight)),

				new KeyFrame(Duration.seconds(duration), 
						new KeyValue(pane.maxWidthProperty(), endWidth))

		);

		if (typeFade == 2) {
			timeline.getKeyFrames().addAll(new KeyFrame(Duration.ZERO, 
					new KeyValue(pane.opacityProperty(), 1)),
					new KeyFrame(Duration.seconds(duration), 
							new KeyValue(pane.opacityProperty(), 0)));
		} else if (typeFade == 1) {
			timeline.getKeyFrames().addAll(new KeyFrame(Duration.ZERO, new KeyValue(pane.opacityProperty(), 0)),
					new KeyFrame(Duration.seconds(duration), 
							new KeyValue(pane.opacityProperty(), 1)));
		}

		timeline.play();

	}

	public void transitionFadeFXML(Pane pane, String fxmlPath, double duration) throws IOException {

		Timeline timeline = new Timeline();


			timeline.getKeyFrames().addAll(new KeyFrame(Duration.ZERO,
							new KeyValue(pane.opacityProperty(), 1)),
					new KeyFrame(Duration.seconds(duration),
							new KeyValue(pane.opacityProperty(), 0)));


		timeline.play();

		Timeline timeline2 = new Timeline();

		AnchorPane anchor = FXMLLoader.load(getClass().getResource(fxmlPath));

		timeline.getKeyFrames().addAll(new KeyFrame(Duration.seconds(duration), new KeyValue(pane.opacityProperty(), 0)),
				new KeyFrame(Duration.seconds(duration + duration), new KeyValue(pane.opacityProperty(), 1)));
		timeline2.play();


		pane.getChildren().setAll(anchor);
	}


	public void sceneTransition(String fxmlPath, Event event) throws IOException {

		Parent secondView = FXMLLoader.load(getClass().getResource(fxmlPath));

		Scene newScene = new Scene(secondView);

		Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		currentStage.setScene(newScene);
		currentStage.centerOnScreen();
		currentStage.show();


	}

}
