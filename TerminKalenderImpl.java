package kalender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import kalender.interfaces.Datum;
import kalender.interfaces.Monat;
import kalender.interfaces.Tag;
import kalender.interfaces.Termin;
import kalender.interfaces.TerminKalender;
import kalender.interfaces.Woche;

public class TerminKalenderImpl implements TerminKalender {

	List<Termin> termine = new ArrayList<Termin>();

	@Override
	public boolean eintragen(Termin termin) {
		termine.add(termin);
		System.out.println(termine);
		return termine.contains(termin);
	}

	@Override
	public void verschiebenAuf(Termin termin, Datum datum) {
		for (Termin ter : termine) {
			if (ter.equals(termin)) {
				ter.verschiebeAuf(datum);
			}
		}
	}

	@Override
	public boolean terminLoeschen(Termin termin) {
		return termine.remove(termin);
	}

	@Override
	public boolean enthaeltTermin(Termin termin) {

System.out.println(termine);
		return termine.contains(termin);
	}

	@Override
	public Map<Datum, List<Termin>> termineFuerTag(Tag tag) {
		Map<Datum, List<Termin>> map = Collections.emptyMap();
		map.put(new DatumImpl(tag),
				termine.stream().filter(e -> e.getDatum().equals(tag)).collect(Collectors.toList()));
		return map;
	}

	@Override
	public Map<Datum, List<Termin>> termineFuerWoche(Woche woche) {
		Map<Datum, List<Termin>> map = Collections.emptyMap();
		for (Termin ter : termine) {
			if ((woche.getStart().compareTo(ter.getDatum()) <= 0) && (ter.getDatum().compareTo(woche.getEnde()) <= 0)) {
				if (map.get(ter.getDatum()) == null) {
					map.put(ter.getDatum(), new ArrayList<Termin>());
				}
				map.get(ter.getDatum()).add(ter);
			}
		}
		return map;
	}

	@Override
	public Map<Datum, List<Termin>> termineFuerMonat(Monat monat) {
		Map<Datum, List<Termin>> map = Collections.emptyMap();
		for (Termin ter : termine) {
			if ((monat.getStart().compareTo(ter.getDatum()) >= 0) && (ter.getDatum().compareTo(monat.getEnde()) <= 0)) {
				if (map.get(ter.getDatum()) == null) {
					map.put(ter.getDatum(), new ArrayList<Termin>());
				}
				map.get(ter.getDatum()).add(ter);
			}
		}
		return map;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((termine == null) ? 0 : termine.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TerminKalenderImpl other = (TerminKalenderImpl) obj;
		if (termine == null) {
			if (other.termine != null)
				return false;
		} else if (!termine.equals(other.termine))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String erg = "";
		return termine.stream().map(Termin::toString).reduce(erg, (a, b) -> a + b);
	}

	// public boolean equals(Object obj) {
	// if (this == obj)
	// return true;
	// if (!(obj instanceof TerminKalender) || (obj == null))
	// return false;
	// return (termine.equals(((TerminKalender) obj)()));
	// }
}
