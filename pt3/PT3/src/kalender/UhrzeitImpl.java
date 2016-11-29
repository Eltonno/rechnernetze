package kalender;

import java.util.Calendar;

import kalender.interfaces.Uhrzeit;

public class UhrzeitImpl implements Uhrzeit {
	private Calendar intern;

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
	}

	public UhrzeitImpl(Uhrzeit o) {
		this(o.getStunde(), o.getMinuten());
	}

	@Override
	public int compareTo(Uhrzeit o) {
		if ((o.getStunde() > this.getStunde()) || (o.getStunde() == this.getStunde()) && (o.getMinuten() > this.getMinuten())) {
			return 1;
		} else if ((o.getStunde() == this.getStunde()) && (o.getMinuten() == this.getMinuten())) {
			return 0;
		} else {
			return -1;
		}
	}

	@Override
	public int getStunde() {
		return intern.get(Calendar.HOUR_OF_DAY);
	}

	@Override
	public int getMinuten() {
		return intern.get(Calendar.MINUTE);
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Uhrzeit) || (obj == null))
			return false;
		if ((((Uhrzeit) obj).getStunde() == this.getStunde()) && (((Uhrzeit) obj).getMinuten() == this.getMinuten())) {
			return true;
		} else {
			return false;
		}
	}
}
