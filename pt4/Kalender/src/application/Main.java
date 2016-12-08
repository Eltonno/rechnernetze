package application;

import java.time.LocalDate;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
	public Scene scene1, scene2;
	public GridPane pane1, pane2;
	public Button btnscene1, btnscene2_1 = new Button("OK"), btnscene2_2 = new Button("Cancel");
	public Stage thestage;
	final TextField year = new TextField(), month = new TextField(), beschreibung = new TextField(), tag = new TextField(), uhrzeit = new TextField(), dauer = new TextField();
	final DatePicker dp = new DatePicker();


	@Override
	public void start(Stage primaryStage) {
		try {
//			TerminKalender tk = new TerminKalenderImpl();
//			BorderPane root = new BorderPane();
//			Scene scene = new Scene(root, 400, 400);
//			Button neuerTermin = new Button();
			// neuerTermin.setText("Termin hinzufügen");
			// neuerTermin.setOnAction();
			
			dp.setOnAction(event ->{
				LocalDate date = dp.getValue();
				System.out.println("Selected date: " + date);
			});
			
			thestage = primaryStage;
			
			pane1 = new GridPane();
			pane2 = new GridPane();
			
			dp.setEditable(false);
			year.setPromptText("Enter the year.");
			month.setPromptText("Enter the month.");
			tag.setPromptText("Enter the day.");
			uhrzeit.setPromptText("Enter the time.");
			dauer.setPromptText("Enter the duration.");
			beschreibung.setPromptText("Enter the comment.");
			
			pane2.setPadding(new Insets(10, 10, 10, 10));
			
			GridPane.setConstraints(dp, 0, 0);
			GridPane.setConstraints(uhrzeit, 0, 1);
			GridPane.setConstraints(dauer, 0, 2);
			GridPane.setConstraints(beschreibung, 0, 3);
			GridPane.setConstraints(btnscene2_1, 0, 4);
			GridPane.setConstraints(btnscene2_2, 0, 5);			
			
			
			btnscene1 = new Button("Termin eintragen");
			btnscene1.setOnAction(e -> ButtonClicked(e));
			btnscene2_1.setOnAction(e -> ButtonClicked(e));
			btnscene2_2.setOnAction(e -> ButtonClicked(e));

			
			pane1.setVgap(5);
			pane2.setVgap(5);
			pane2.setHgap(5);
			// set background color of each Pane
//			pane1.setStyle("-fx-background-color: white;-fx-padding: 10px;");
//			pane2.setStyle("-fx-background-color: white;-fx-padding: 10px;");

			// add everything to panes
			pane1.getChildren().addAll(btnscene1);
			pane2.getChildren().addAll(dp, uhrzeit, dauer, beschreibung, btnscene2_1, btnscene2_2);

			scene1 = new Scene(pane1);
			scene2 = new Scene(pane2);

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
		else if(e.getSource() == btnscene2_1)
			thestage.setScene(scene1);
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