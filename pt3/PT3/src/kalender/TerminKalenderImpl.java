package kalender;

import java.nio.file.DirectoryStream.Filter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import kalender.interfaces.Datum;
import kalender.interfaces.Monat;
import kalender.interfaces.Tag;
import kalender.interfaces.Termin;
import kalender.interfaces.TerminKalender;
import kalender.interfaces.Woche;

public class TerminKalenderImpl implements TerminKalender {

	ArrayList<Termin> termine;

	@Override
	public boolean eintragen(Termin termin) {
		termine.add(termin);
		return false;
	}

	@Override
	public void verschiebenAuf(Termin termin, Datum datum) {
		if (termine.contains(termin)) {
			for (Termin ter : termine) {
				if (ter.equals(termin)) {
					ter.verschiebeAuf(datum);
				}
			}
		}
	}

	@Override
	public boolean terminLoeschen(Termin termin) {
		if (termine.contains(termin)) {
			termine.remove(termin);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean enthaeltTermin(Termin termin) {
		if (termine.contains(termin)) {
			return true;
		} else {
			return false;
		}
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
		map.putAll(termineFuerTag(woche.getStart().getTag()));
//		map.put(new DatumImpl(tag),
//				termine.stream().filter(e -> e.getDatum().equals(tag)).collect(Collectors.toList()));
		termine.stream().filter(e -> ((woche.getStart().compareTo(e.getDatum())==1) && (e.getDatum().compareTo(woche.getEnde())==-1))).collect(Collectors.toMap(, valueMapper));
		return map;
	}

	@Override
	public Map<Datum, List<Termin>> termineFuerMonat(Monat monat) {
		Map<Datum, List<Termin>> map = Collections.emptyMap();
//		map.put(new DatumImpl(tag),
//				termine.stream().filter(e -> e.getDatum().equals(tag)).collect(Collectors.toList()));
		return map;
	}

}
