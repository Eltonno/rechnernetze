package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import kalender.TerminKalenderImpl;
import kalender.interfaces.TerminKalender;

public class Main extends Application {
	public Scene scene1, scene2;
	public Pane pane1, pane2;
	public Button btnscene1, btnscene2;
	public Label lblscene1, lblscene2;
	public Stage thestage;

	@Override
	public void start(Stage primaryStage) {
		try {
			TerminKalender tk = new TerminKalenderImpl();
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 400, 400);
			Button neuerTermin = new Button();
			// neuerTermin.setText("Termin hinzufügen");
			// neuerTermin.setOnAction();

			btnscene1 = new Button("Click to go to Other Scene");
			btnscene2 = new Button("Click to go back to First Scene");
			btnscene1.setOnAction(e -> ButtonClicked(e));
			btnscene2.setOnAction(e -> ButtonClicked(e));
			lblscene1 = new Label("Scene 1");
			lblscene2 = new Label("Scene 2");

			pane1 = new FlowPane();
			pane2 = new FlowPane();
			((FlowPane) pane1).setVgap(10);
			((FlowPane) pane2).setVgap(10);
			// set background color of each Pane
			pane1.setStyle("-fx-background-color: tan;-fx-padding: 10px;");
			pane2.setStyle("-fx-background-color: red;-fx-padding: 10px;");

			// add everything to panes
			pane1.getChildren().addAll(lblscene1, btnscene1);
			pane2.getChildren().addAll(lblscene2, btnscene2);

			scene1 = new Scene(pane1, 200, 100);
			scene2 = new Scene(pane2, 200, 100);

			primaryStage.setTitle("Hello World!");
			primaryStage.setScene(scene1);
			primaryStage.show();

			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			// primaryStage.setScene(scene);
			// primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ButtonClicked(ActionEvent e) {
		if (e.getSource() == btnscene1)
			thestage.setScene(scene2);
		else
			thestage.setScene(scene1);
	}

	public static void main(String[] args) {
		launch(args);
	}
}

/*
 * Eintragen eines neuen Termines
 *
 * tk.eintragen(new TerminImpl("sadasz", new DatumImpl(new TagImpl(2016, 134),
 * new UhrzeitImpl()), new DauerImpl(10)))
 * 
 */