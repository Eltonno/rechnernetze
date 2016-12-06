package kalender.interfaces;

import java.util.Map;

public interface Termin extends Comparable<Termin> {
	/*
	 * Liefert die Beschreibung eines Termins, die bei der Erzeugung übergeben
	 * wird.
	 */
	public String getBeschreibung();
	/*
	 * Liefert das Datum eines Termins, das bei der Erzeugung übergeben wird.
	 */
	public Datum getDatum();
	/*
	 * Liefert die Dauer eines Termins, die bei der Erzeugung übergeben wird.
	 */
	public Dauer getDauer();
	/*
	 * Verschiebt den Termin auf das übergebene Datum.
	 */
	public Termin verschiebeAuf(Datum datum);
	/*
	 * Liefert Termine in einem Monat. Um einfache Termine und Termine mit
	 * Wiederholungen konsistent zu behandeln, liefert auch ein einfacher Termin
	 * eine Map mit Datum und Termin.
	 * 
	 * Für einfache Termine enthält die Map maximal ein Element, den Termin
	 * selbst, wenn dieser im geforderten Monat liegt.
	 * 
	 * Für Termine mit Wiederholungen kann die Map mehrere Einträge enthalten- 
	 * alle Wiederholungen, die in dem Monat liegen. Dabei soll für verschiedene
	 * Datumsangaben nur die Referenz auf den Termin mit Wiederholungen
	 * eingetragen werden.
	 * 
	 * Zur weiteren Erläuterung siehe auch termineFuerMonat in TerminKalender.
	 */
	public Map<Datum, Termin> termineIn(Monat monat);
	/*
	 * Liefert die Termine einer Woche.
	 * 
	 * Zur weiteren Erläuterung siehe termineIn(Monat) sowie termineFuerWoche in
	 * TerminKalender.
	 */
	public Map<Datum, Termin> termineIn(Woche woche);
	/*
	 * Liefert die Termine eines Tages.
	 * 
	 * Zur weiteren Erläuterung siehe termineIn(Monat) sowie termineFuerTag in
	 * TerminKalender.
	 */
	public Map<Datum, Termin> termineAn(Tag tag);
}
