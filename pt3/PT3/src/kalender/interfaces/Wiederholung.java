package kalender.interfaces;
import kalender.WiederholungType;

/**
 * 
 * @author Birgit Wendholt Das Interface Wiederholung repräsentiert alle für
 *         eine Terminwiederholung relevanten Aspekte (Typ, Zyklus, Anzahl)
 * 
 *         und spezifiziert Methoden, um
 * 
 *         abhängige Größen wie Intervalllänge und maxIntervalIndex zu berechnen
 *         Wiederholungsintervalle zu berechnen das Datum der naechsten (n)
 *         Wiederholung(en) zu berechnen den Wiederholungszähler zu
 *         inkrementieren / dekrementieren
 *         
 *         Eine Implemetierung findet sich als innere Klasse in der Klasse
 *         TerminMitWiederholungImpl
 */
public interface Wiederholung {
	/*
	 * Eigenschaften einer Wiederholung, die bei Erzeugen übergeben werden
	 * müssen
	 * 
	 * Typ: tägliche / wöchentliche Wiederholung Zyklus: Wiederholungszyklus
	 * (z.B. alle 2 Wochen) Anzahl: Anzahl der Wiederholungen
	 */
	public WiederholungType getType();
	public int getZyklus();
	public int anzahl();
	/*
	 * Der Abstand zwischen zwei Wiederholungen in Anzahl von Tagen Produkt aus
	 * Anzahl der Tage des WiederholungsTyps und dem Zyklus
	 */
	public int intervallLaenge();
	/*
	 * Ist gleich der Anzahl der Wiederholungen. Bezeichnet das letzte
	 * Intervall, in dem noch eine Wiederholung des Termins liegt.
	 */
	public int maxIntervallIndex();

	/*
	 * Zu einem beliebigen Datum wird bestimmt, in welchem Intervall des Termins
	 * relativ zum Datum eine Wiederholung liegt. Das Ergebnis ist ein
	 * Intervall-Index für eine Wiederholung, die nach dem Datum liegt. Mit
	 * Hilfe dieses Index lassen die die Wiederholungen zu einem Termin mit
	 * naechstesDatum direkt berechnen.
	 */
	public int naechstesIntervall(Datum datum);
	/*
	 * Analog naechstesIntervall nur dass hier der Intervallindex vor dem Datum
	 * berechnet wird.
	 */
	public int vorherigesIntervall(Datum datum);
	/*
	 * Das Datum der nachfolgenden Wiederholung. siehe auch die "naive" Iterator
	 * Implementierung in TerminMitWiederholungImpl
	 */
	public Datum naechstesDatum();
	/*
	 * Das Datum der k-ten Wiederholung des Termin, k ist auch der
	 * Intervall-Index. Die Methoden naechstesIntervall/vorigesIntervall
	 * berechnen diesen Index.
	 */
	public Datum naechstesDatum(int k);
	/*
	 * Addition und Subtraktion eines wdhCount mit/von der Anzahl der
	 * Wiederholungen
	 */
	public Wiederholung sub(int wdhCount);
	public Wiederholung add(int wdhCount);
}
