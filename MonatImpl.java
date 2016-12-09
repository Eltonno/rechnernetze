package kalender;

import java.util.Calendar;

import kalender.interfaces.Datum;
import kalender.interfaces.Monat;

public class MonatImpl implements Monat {

	private Calendar intern;

	public MonatImpl(int jahr, int monat) {
		intern = Calendar.getInstance();
		intern.clear();
		intern.set(Calendar.YEAR, jahr);
		intern.set(Calendar.MONTH, monat);
	}

	@Override
	public Datum getStart() {
		return new DatumImpl(new TagImpl(intern.get(Calendar.YEAR), intern.get(Calendar.MONTH),
				intern.getActualMinimum(Calendar.DAY_OF_MONTH)));
	}

	@Override
	public Datum getEnde() {
		return new DatumImpl(new TagImpl(intern.get(Calendar.YEAR), intern.get(Calendar.MONTH),
				intern.getActualMaximum(Calendar.DAY_OF_MONTH)), new UhrzeitImpl(23, 59));
	}

	@Override
	public int getMonat() {
		return intern.get(Calendar.MONTH);
	}

	@Override
	public int getJahr() {
		return intern.get(Calendar.YEAR);
	}

	@Override
	public String toString() {
		return String.format("Monat %d,%d [" + getStart() + "," + getEnde() + "]", getMonat() + 1, getJahr());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((intern == null) ? 0 : intern.hashCode());
		return result;
	}

	

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Monat) || (obj == null))
			return false;
		return (this.getMonat() == ((Monat) obj).getMonat() 
				&& this.getJahr() == ((Monat) obj).getJahr());
	}
	
}
