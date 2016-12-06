package kalender.interfaces;

import java.util.Calendar;

/**
 * @author Birgit Wendholt
 * 
 *         Tag repraesentiert einen eindeutigen Tag im Kalender. Eindeutig wird
 *         ein Tag im Kalender durch Kombination von Jahr, Monat, Tag im Monat
 *         oder durch Kombination von Jahr und Tag im Jahr. Eine dieser
 *         Kombinationen muss bei der Erzeugung von Tagen übergeben werden.
 * 
 *         Eine Implementierung sollte intern ein Calendar-Objekt für die
 *         Transformationen und Umrechnungen verwenden.
 * 
 *         Tage lassen sich über die Transformation/Darstellung als
 *         Calendar-Objekt vergleichen.
 */
public interface Tag extends DatumsGroesse, Comparable<Tag> {
	/*
	 * berechnet Jahr, Monat, TagImJahr, TagImMonat als int. verwendet geeignete
	 * get Methoden von Calendar
	 */
	public int getJahr();

	public int getMonat();

	public int getTagImJahr();

	public int getTagImMonat();

	/*
	 * Berechnet die Differenz von Tagen (this-other). Dazu werden die Tage in
	 * die Calendar-Basis umgerechnet und die Differenz der
	 * Millisekundendarstellung in Anzahl Tage umgerechnet.
	 */
	public long differenzInTagen(Tag other);

	/*
	 * Transformiert Tag in ein Calendar-Objekt. Lässt sich durch Clonen der
	 * internen Darstellung erreichen.
	 */
	public Calendar inBasis();

}
