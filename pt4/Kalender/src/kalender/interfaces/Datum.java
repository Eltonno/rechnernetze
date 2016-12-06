package kalender.interfaces;

import java.util.Calendar;

/*
 * Datum kapselt die Darstellung von Datums und Zeitangaben als Calendar-Objekt 
 * in Java. 
 * Ein Datum besteht aus Datumsangabe (eindeutig durch Tag, Woche, Monat und Jahr) 
 * und einer Zeitangabe dargestellt als Uhrzeit.
 * 
 * Intern verwendet Datum eine Calendar-Objekt für die Berechnungen und 
 * Umrechnungen.
 */
public interface Datum extends Comparable<Datum> {
	/*
	 * Liefern den Tag, Woche, Monat, Uhrzeit des Datums
	 */
	public Tag getTag();
	public Woche getWoche();
	public Monat getMonat();
	public Uhrzeit getUhrzeit();
	/*
	 * Zugriff auf z.T. nicht eindeutige Datumsanteile. Wrapper um die get
	 * Methoden von Calendar.
	 */
	public int getJahr();
	public int getTagImMonat();
	public int getTagImJahr();
	public int getWocheImMonat();
	public int getWocheImJahr();
	public int getMonatImJahr();
	/*
	 * Addieren und Subtrahieren einer Dauer. Addiert und Subtrahiert werden die
	 * Minuten einer Dauer. Addiert/subtrahiert wird unter Verwendung der
	 * add-Methode von Calendar. (--> inBasis())
	 */
	public Datum add(Dauer dauer);
	public Datum sub(Dauer dauer);
	/*
	 * Berechnet den Abstand zwischen zwei Datums-objekten als Dauer durch
	 * Umrechnung beider Datumsobjekte in Minuten. Darf negativ werden.
	 */
	public Dauer abstand(Datum d);
	/*
	 * Berechnet die zeitliche Differenz zwischen zwei Datums-objekten in Anzahl
	 * Tagen. Nutzt dazu die Methode differenzInTagen von Tag.
	 */
	public long differenzInTagen(Datum d);
	/*
	 * Rechnet das Datums-objekt in Minuten seit dem 1.1.1970 00:00:00 um.
	 * Verwendet dazu die Methode getTimeMillis() von Calendar.
	 */
	public int inMinuten();
	/*
	 * Transformiert ein Datums-objekt und erzeugt ein neues Calendar Objekt,
	 * bzw. cloned das interne Calendar Objekt.
	 */
	public Calendar inBasis();
}
