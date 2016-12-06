package kalender.interfaces;

import java.util.Map;

public interface TerminMitWiederholung extends Termin, Iterable<Termin> {
	/*
	 * Liefert ein Objekt von Typ Wiederholung zurück. Die Wiederholung kennt
	 * die Anzahl der Wiederholungen, den Typ der Wiederholung und den Zyklus
	 * der Wiederholung. Die Wiederholung wird beim Erzeugen des Termins
	 * übergeben.
	 */
	public Wiederholung getWdh();
	/*
	 * Ein Iterator, der in einem vorgebenen Intervall das Datum der
	 * Wiederholungen des Termins, die in dem Intervall liegen, zur�ckliefert.
	 * 
	 * von ist der erste Intervallindex (inkl.), bis letzte Intervallindex
	 * (inkl.).
	 * 
	 * Zu Details siehe Erl�uterung zur Implementierung in der Klasse
	 * TerminMitWiederholungImpl.
	 */
	public IntervallIterator<Datum> intervallIterator(int von, int bis);
	/*
	 * Liefert alle Wiederholung, die innerhalb einer DatumsGroesse (Tag, Woche,
	 * Monat) liegen, also z.B. alle Wiederholungen, die in einer Woche liegen.
	 * 
	 * Diese Methode lässt sich generisch implementieren, zu Details siehe die
	 * Erläuterungen zur Implementierung in der Klasse
	 * TerminMitWiederholungImpl.
	 */
	public Map<Datum, Termin> termineFuer(DatumsGroesse groesse);

}
