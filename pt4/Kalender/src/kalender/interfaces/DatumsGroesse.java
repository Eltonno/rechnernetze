package kalender.interfaces;

public interface DatumsGroesse {
	/*
	 * Gibt das ersten Datum einer DatumsGroesse zurück.
	 * Das erste Datum hat immer die Uhrzeit 00:00.
	 */
	public Datum getStart();
	/*
	 * Gibt das letzte Datum einer DatumsGroesse zurück.
	 * Das letzte Datum hat immmer die  Uhrzeit 23:59.
	 */
	public Datum getEnde();
}
