package kalender;

import kalender.interfaces.Datum;
import kalender.interfaces.Dauer;
import kalender.interfaces.Tag;

public class DauerImpl implements Dauer {

	private int minuten;

	public DauerImpl(Datum d1, Datum d2) {
	}

	public DauerImpl(int minuten) {
		this.minuten = minuten;
	}

	public DauerImpl(int stunden, int minuten) {
		this(stunden * 60 + minuten);
	}

	public DauerImpl(int tage, int stunden, int minuten) {
		this(tage * 24 + stunden, minuten);
	}

	@Override
	public int compareTo(Dauer o) {
		if(o.inMinuten()>this.inMinuten()){
			return 1;
		}else if(o.inMinuten() == this.inMinuten()){
			return 0;
		}else {
			return -1;
		}
	}

	@Override
	public int inMinuten() {
		return minuten;
	}

	@Override
	public int inStunden() {
		return (minuten/60);
	}

	@Override
	public int inTagen() {
		return (inStunden()/24);
	}

	@Override
	public int inWochen() {
		return (inTagen()/7);
	}

	@Override
	public int anteilMinuten() {
		return minuten;
	}

	@Override
	public int anteilStunden() {
		return inStunden()*60;
	}

	@Override
	public int anteilTage() {
		return inTagen()*24*60;
	}

	@Override
	public int anteilWochen() {
		return inWochen()*7*24*60;
	}
	
	public boolean equals(Object obj){
		if (!(obj instanceof Dauer) || (obj == null))
			return false;
		if (minuten == ((Dauer) obj).inMinuten()) {
			return true;
		} else {
			return false;
		}
		
	}
}
