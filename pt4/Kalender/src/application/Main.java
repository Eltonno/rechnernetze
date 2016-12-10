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
//import kalender.TerminKalenderImpl;
import kalender.DatumImpl;
import kalender.DauerImpl;
import kalender.TagImpl;
import kalender.TerminImpl;
import kalender.TerminKalenderImpl;
import kalender.UhrzeitImpl;
import kalender.interfaces.TerminKalender;

public class Main extends Application {
	public Scene scene1, scene2;
	public GridPane pane1, pane2, pane3;
	public Button btnscene1;
	public Button btnok = new Button("OK");
	public Button 	btncancel = new Button("Cancel");
	public Stage thestage;
	final TextField year = new TextField();
	final TextField month = new TextField();
	final TextField beschreibung = new TextField();
	final TextField	tag = new TextField();
	final TextField hour = new NumberTextField();
	final TextField minute = new TextField();
	final TextField dauer = new TextField();
	final DatePicker date = new DatePicker();
	public TerminKalender kalender;

	@Override
	public void start(Stage primaryStage) {
		try {
			kalender = new TerminKalenderImpl();

			date.setOnAction(event -> {
				LocalDate ldate = date.getValue();
				System.out.println("Selected date: " + ldate);
			});

			thestage = primaryStage;

			pane1 = new GridPane();
			pane2 = new GridPane();
			pane3 = new GridPane();

			date.setEditable(false);
			year.setPromptText("Enter the year.");
			month.setPromptText("Enter the month.");
			tag.setPromptText("Enter the day.");
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
			GridPane.setConstraints(btnok, 0, 4);
			GridPane.setConstraints(btncancel, 0, 5);

			btnscene1 = new Button("Termin eintragen");
			btnscene1.setOnAction(e -> ButtonClicked(e));
			btnok.setOnAction(e -> ButtonClicked(e));
			btncancel.setOnAction(e -> ButtonClicked(e));

			pane1.setVgap(5);
			pane2.setVgap(5);
			pane2.setHgap(5);
			// set background color of each Pane
			// pane1.setStyle("-fx-background-color: white;-fx-padding: 10px;");
			// pane2.setStyle("-fx-background-color: white;-fx-padding: 10px;");

			// add everything to panes
			pane1.getChildren().addAll(btnscene1);
			pane2.getChildren().addAll(date, dauer, beschreibung, btnok, btncancel, pane3);
			pane3.getChildren().addAll(hour, minute);

			scene1 = new Scene(pane1);
			scene2 = new Scene(pane2);

			primaryStage.setTitle("Terminkalender");
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
		else if (e.getSource() == btnok) {
			kalender.eintragen(new TerminImpl(beschreibung.getText(), new DatumImpl(new TagImpl(date.getValue().getYear(), date.getValue().getMonthValue(), date.getValue().getDayOfMonth()), new UhrzeitImpl(Integer.parseInt(hour.getText()), Integer.parseInt(minute.getText()))), new DauerImpl(Integer.parseInt(dauer.getText()))));
			thestage.setScene(scene1);
		} else
			thestage.setScene(scene1);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
	
	public class NumberTextField extends TextField {
	    
	    @Override public void replaceText(int start, int end, String text) {
	           if (text.matches("[0-9]") || text == "") {
	               super.replaceText(start, end, text);
	           }
	       }
	     
	       @Override public void replaceSelection(String text) {
	           if (text.matches("[0-9]") || text == "") {
	               super.replaceSelection(text);
	           }
	       }
	       
	       public int getInt() {
	    	   return 0;
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

