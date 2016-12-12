package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
//import kalender.TerminKalenderImpl;
import kalender.DatumImpl;
import kalender.DauerImpl;
import kalender.TagImpl;
import kalender.TerminImpl;
import kalender.TerminKalenderImpl;
import kalender.UhrzeitImpl;
import kalender.interfaces.TerminKalender;

public class Main extends Application {

	public Stage thestage;

	/*
	 * Elements for start screen
	 */
	public Scene scene1;
	public GridPane pane1;
	public Button btnAppORep;
	public Button btnAppMRep;
	public TerminKalender kalender;

	/*
	 * Elements for the adding of normal appointments
	 */
	public Scene appScene;
	public GridPane pane2;
	public GridPane pane3;
	public GridPane btnPane;
	public Button btnok = new Button("OK");
	public Button btncancel = new Button("Cancel");
	final TextArea beschreibung = new TextArea();
	final TextField hour = new NumberTextField();
	final TextField minute = new NumberTextField();
	final TextField dauer = new NumberTextField();
	final DatePicker date = new DatePicker();

	/*
	 * Elements for the adding of repetition appointments
	 */
	public Scene repScene;
	public GridPane appRep;
	public GridPane timePane;
	public GridPane btnPaneRep;
	public Button btnokRep = new Button("OK");
	public Button btncancelRep = new Button("Cancel");
	final TextArea beschreibungRep = new TextArea();
	final TextField hourRep = new NumberTextField();
	final TextField minuteRep = new NumberTextField();
	final TextField dauerRep = new NumberTextField();
	final DatePicker dateRep = new DatePicker();
	final TextField zyklus = new NumberTextField();
	final TextField anzahlWdh = new NumberTextField();
	final TextField wdhType = new TextField();

	/*
	 * Elements for the error dialog
	 */
	public Button okbtn = new Button("OK");
	public Stage secStage = new Stage();
	public Stage trdStage = new Stage();

	@Override
	public void start(Stage primaryStage) {
		try {
			/*
			 * init
			 */
			kalender = new TerminKalenderImpl();
			thestage = primaryStage;
			pane1 = new GridPane();
			btnAppORep = new Button("Normalen Termin eintragen");
			btnAppORep.setOnAction(e -> ButtonClicked(e));
			btnAppORep.setMinWidth(250);
			btnAppMRep = new Button("Termin mit Wiederholung eintragen");
			btnAppMRep.setOnAction(e -> ButtonClicked(e));
			btnAppMRep.setMinWidth(250);

			pane1.setVgap(5);

			pane1.getChildren().addAll(btnAppORep, btnAppMRep);
			GridPane.setConstraints(btnAppORep, 0, 0);
			GridPane.setConstraints(btnAppMRep, 0, 1);

			/*
			 * appointment
			 */
			pane2 = new GridPane();
			pane3 = new GridPane();
			btnPane = new GridPane();
			date.setEditable(false);
			hour.setPromptText("hh");
			minute.setPromptText("mm");
			dauer.setPromptText("Enter the duration.");
			beschreibung.setPromptText("Enter the comment.");

			pane2.setPadding(new Insets(10, 10, 10, 10));

			GridPane.setConstraints(date, 0, 0);
			GridPane.setConstraints(hour, 0, 1);
			GridPane.setConstraints(pane3, 0, 1);
			GridPane.setConstraints(minute, 1, 1);
			GridPane.setConstraints(dauer, 0, 2);
			GridPane.setConstraints(beschreibung, 0, 3);
			GridPane.setConstraints(btnPane, 0, 4);
			GridPane.setConstraints(btnok, 0, 0);
			GridPane.setConstraints(btncancel, 1, 0);

			btnok.setOnAction(e -> ButtonClicked(e));
			btncancel.setOnAction(e -> ButtonClicked(e));

			pane2.setVgap(5);
			pane2.setHgap(5);

			pane2.getChildren().addAll(date, dauer, beschreibung, pane3, btnPane);
			pane3.getChildren().addAll(hour, minute);
			btnPane.getChildren().addAll(btnok, btncancel);
			btnPane.setAlignment(Pos.CENTER);

			/*
			 * appointment with repetitions
			 */
			appRep = new GridPane();
			timePane = new GridPane();
			btnPaneRep = new GridPane();
			dateRep.setEditable(false);
			hourRep.setPromptText("hh");
			minuteRep.setPromptText("mm");
			dauerRep.setPromptText("Enter the duration.");
			beschreibungRep.setPromptText("Enter the comment.");
			zyklus.setPromptText("Alle wie viel Zeiteinheiten...?");
			wdhType.setPromptText("taeglich o. woechentlich");
			anzahlWdh.setPromptText("Wie oft soll wiederholt werden?");

			appRep.setPadding(new Insets(10, 10, 10, 10));

			GridPane.setConstraints(zyklus, 0, 1);
			GridPane.setConstraints(anzahlWdh, 1, 1);
			GridPane.setConstraints(wdhType, 2, 1);
			GridPane.setConstraints(dateRep, 0, 0);
			GridPane.setConstraints(hourRep, 0, 0);
			GridPane.setConstraints(timePane, 0, 1);
			GridPane.setConstraints(minuteRep, 1, 0);
			GridPane.setConstraints(dauerRep, 0, 2);
			GridPane.setConstraints(beschreibungRep, 0, 4);
			GridPane.setConstraints(btnPaneRep, 0, 5);
			GridPane.setConstraints(btnokRep, 0, 0);
			GridPane.setConstraints(btncancelRep, 1, 0);

			btnokRep.setOnAction(e -> ButtonClicked(e));
			btncancelRep.setOnAction(e -> ButtonClicked(e));

			appRep.setVgap(5);
			appRep.setHgap(5);

			appRep.getChildren().addAll(dateRep, dauerRep, beschreibungRep, timePane, btnPaneRep);
			timePane.getChildren().addAll(hourRep, minuteRep, zyklus, anzahlWdh, wdhType);
			btnPaneRep.getChildren().addAll(btnokRep, btncancelRep);
			btnPaneRep.setAlignment(Pos.CENTER);

			/*
			 * mainpart
			 */
			scene1 = new Scene(pane1);
			appScene = new Scene(pane2);
			repScene = new Scene(appRep);

			primaryStage.setTitle("Terminkalender");
			primaryStage.setScene(scene1);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ButtonClicked(ActionEvent e) {
		if (e.getSource() == btnAppMRep)
			thestage.setScene(repScene);
		else if (e.getSource() == btnAppORep)
			thestage.setScene(appScene);
		else if (e.getSource() == btnok) {
			try {
				if (date == null || Integer.parseInt(hour.getText()) > 23 || hour.getText() == ""
						|| Integer.parseInt(minute.getText()) > 59 || minute.getText() == "") {
					throw new Exception();
				}
				kalender.eintragen(new TerminImpl(beschreibung.getText(),
						new DatumImpl(
								new TagImpl(date.getValue().getYear(), date.getValue().getMonthValue(),
										date.getValue().getDayOfMonth()),
								new UhrzeitImpl(Integer.parseInt(hour.getText()), Integer.parseInt(minute.getText()))),
						new DauerImpl(Integer.parseInt(dauer.getText()))));
				thestage.setScene(scene1);
			} catch (Exception exc) {
				GridPane gp = new GridPane();

				Label text = new Label("Bitte überprüfe die eingaben,\ndenn irgendetwas ist schief gelaufen.");
				text.setWrapText(true);
				text.setGraphicTextGap(20);
				text.snapshot(null, null);
				int width = (int) text.getLayoutBounds().getWidth() + 60;
				if (width < 400)
					width = 400;

				int height = 120;

				secStage.setWidth(width);
				secStage.setHeight(height);
				secStage.setX(thestage.getX() + (thestage.getWidth() / 2 - secStage.getWidth() / 2));
				secStage.setY(thestage.getY() + (thestage.getHeight() / 2 - secStage.getHeight() / 2));

				gp.getChildren().addAll(okbtn, text);
				GridPane.setConstraints(text, 0, 0);
				GridPane.setColumnSpan(text, 3);
				GridPane.setConstraints(okbtn, 1, 1);
				secStage.initOwner(thestage);
				secStage.initModality(Modality.APPLICATION_MODAL);
				secStage.setTitle("Fehlerhafte eingabe.");
				secStage.setScene(new Scene(gp));
				secStage.show();
				okbtn.setOnAction(e2 -> ButtonClicked(e2));
			}
		} else if (e.getSource() == btnokRep) {
			try {
				if (date == null || Integer.parseInt(hour.getText()) > 23 || hour.getText() == ""
						|| Integer.parseInt(minute.getText()) > 59 || minute.getText() == ""
						|| (wdhType.getText() != "woechentlich" && wdhType.getText() != "taeglich") || anzahlWdh.getText() == "" || zyklus.getText() == "" || wdhType.getText() == "") {
					throw new Exception();
				}
				kalender.eintragen(new TerminImpl(beschreibung.getText(),
						new DatumImpl(
								new TagImpl(date.getValue().getYear(), date.getValue().getMonthValue(),
										date.getValue().getDayOfMonth()),
								new UhrzeitImpl(Integer.parseInt(hour.getText()), Integer.parseInt(minute.getText()))),
						new DauerImpl(Integer.parseInt(dauer.getText()))));
				thestage.setScene(scene1);
			} catch (Exception exc) {
				GridPane gp = new GridPane();

				Label text = new Label("Bitte überprüfe die eingaben,\ndenn irgendetwas ist schief gelaufen.");
				text.setWrapText(true);
				text.setGraphicTextGap(20);
				text.setAlignment(Pos.CENTER);
				gp.setAlignment(Pos.CENTER);
				int width = (int) text.getLayoutBounds().getWidth() + 60;
				if (width < 400)
					width = 400;

				int height = 120;

				trdStage.setWidth(width);
				trdStage.setHeight(height);
				trdStage.setX(thestage.getX() + (thestage.getWidth() / 2 - trdStage.getWidth() / 2));
				trdStage.setY(thestage.getY() + (thestage.getHeight() / 2 - trdStage.getHeight() / 2));

				gp.getChildren().addAll(okbtn, text);
				GridPane.setConstraints(text, 0, 0);
				GridPane.setColumnSpan(text, 3);
				GridPane.setConstraints(okbtn, 1, 1);
				trdStage.initOwner(thestage);
				trdStage.initModality(Modality.APPLICATION_MODAL);
				trdStage.setTitle("Fehlerhafte eingabe.");
				trdStage.setScene(new Scene(gp));
				trdStage.show();
				okbtn.setOnAction(e2 -> ButtonClicked(e2));
			}
		} else if (e.getSource() == okbtn) {
			secStage.close();
			trdStage.close();
		} else
			thestage.setScene(scene1);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	public class NumberTextField extends TextField {

		@Override
		public void replaceText(int start, int end, String text) {
			if (text.matches("[0-9]") || text == "") {
				super.replaceText(start, end, text);
			}
		}

		@Override
		public void replaceSelection(String text) {
			if (text.matches("[0-9]") || text == "") {
				super.replaceSelection(text);
			}
		}
	}
}

/*
 * Eintragen eines neuen Termines
 *
 * tk.eintragen(new TerminImpl("sadasz", new DatumImpl(new TagImpl(2016, 134),
 * new UhrzeitImpl()), new DauerImpl(10)))
 * 
 */
