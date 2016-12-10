package kalender;

import java.util.Calendar;

import kalender.interfaces.Uhrzeit;

public class UhrzeitImpl implements Uhrzeit {
	private Calendar intern;
	private int stunde;
	private int minuten;

	public UhrzeitImpl() {
		this(0, 0);
	}

	public UhrzeitImpl(int stunde, int minute) {
		if (stunde < 0 || stunde > 24 || (stunde == 24 && minute != 0) || minute < 0 || minute > 59) {
			throw new IllegalArgumentException("Intervall fuer Stunde [0,24[, fuer Minute [0,59] oder 24:00");
		}
		intern = Calendar.getInstance();
		intern.clear();
		intern.set(Calendar.HOUR_OF_DAY, stunde);
		intern.set(Calendar.MINUTE, minute);
		this.stunde = intern.get(Calendar.HOUR_OF_DAY);
		this.minuten = intern.get(Calendar.MINUTE);
	}

	public UhrzeitImpl(Uhrzeit o) {
		this(o.getStunde(), o.getMinuten());
	}

	@Override
	public int compareTo(Uhrzeit o) {
		if ((o.getStunde() > this.getStunde())
				|| (o.getStunde() == this.getStunde()) && (o.getMinuten() > this.getMinuten())) {
			return 1;
		} else if ((o.getStunde() == this.getStunde()) && (o.getMinuten() == this.getMinuten())) {
			return 0;
		} else {
			return -1;
		}
	}

	@Override
	public int getStunde() {
		return stunde;
	}

	@Override
	public int getMinuten() {
		return minuten;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Uhrzeit) || (obj == null))
			return false;
		return (this.compareTo((Uhrzeit) obj) == 0);
	}
	
	
	@Override
	public String toString() {
		return String.format("Uhrzeit: %2d:%2d", stunde, minuten);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((intern == null) ? 0 : intern.hashCode());
		return result;
	}

	
}
