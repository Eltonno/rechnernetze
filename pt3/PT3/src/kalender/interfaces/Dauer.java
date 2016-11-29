package kalender.interfaces;

public interface Dauer extends Comparable<Dauer> {
	/*
	 * Gibt den Wert der Dauer in Minuten, Tagen, Stunden, Wochen wieder. Die
	 * Anteile in Tagen, Stunden und Wochen sind verlustbehaftet, wenn die
	 * Minuten nicht ein Vielfaches der jeweiligen Groesse sind.
	 */
	public int inMinuten();
	public int inStunden();
	public int inTagen();
	public int inWochen();
	/*
	 * Berechnet die Anteile für die Zeitgroessen an der Gesamtdauer. 
	 * Die Addition der Anteile muss den Wert der Dauer ergeben. 
	 */
	public int anteilMinuten();
	public int anteilStunden();
	public int anteilTage();
	public int anteilWochen();
}
