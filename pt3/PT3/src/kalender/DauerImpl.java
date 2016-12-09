package kalender;

import kalender.interfaces.Datum;
import kalender.interfaces.Dauer;

public class DauerImpl implements Dauer {

	private int minuten;
	final static int mToS = 60;
	final static int sToD = 24;
	final static int dToW = 7;

	public DauerImpl(Datum d1, Datum d2) {
		this(d1.abstand(d2).inMinuten());
	}

	public DauerImpl(int minuten) {
		this.minuten = minuten;
	}

	public DauerImpl(int stunden, int minuten) {
		this(stunden * mToS + minuten);
	}

	public DauerImpl(int tage, int stunden, int minuten) {
		this(tage * sToD + stunden, minuten);
	}

	@Override
	public int compareTo(Dauer o) {
		if (o.inMinuten() > this.inMinuten()) {
			return 1;
		} else if (o.inMinuten() == this.inMinuten()) {
			return 0;
		} else {
			return -1;
		}
	}

	@Override
	public int inMinuten() {
		return minuten;
	}

	@Override
	public int inStunden() {
		return (minuten / mToS);
	}

	@Override
	public int inTagen() {
		return (inStunden() / sToD);
	}

	@Override
	public int inWochen() {
		return (inTagen() / dToW);
	}

	@Override
	public int anteilMinuten() {
		return minuten - (anteilStunden());
	}

	@Override
	public int anteilStunden() {
		return inStunden() * (anteilTage());
	}

	@Override
	public int anteilTage() {
		return (inTagen() * sToD * mToS) - (anteilWochen());
	}

	@Override
	public int anteilWochen() {
		return inWochen() * dToW * sToD * mToS;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Dauer) || (obj == null))
			return false;
		return (this.compareTo((Dauer) obj) == 0);
	}

	
	@Override
	public String toString() {
		return String.format("Dauer: %d Wochen, %d Tage, %d Stunden, %d Minuten", inWochen(),anteilTage()/24/60, anteilStunden()/60/24/7,anteilMinuten());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + minuten;
		return result;
	}



	
}
