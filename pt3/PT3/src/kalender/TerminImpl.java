package kalender;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import kalender.interfaces.Datum;
import kalender.interfaces.Dauer;
import kalender.interfaces.Monat;
import kalender.interfaces.Tag;
import kalender.interfaces.Termin;
import kalender.interfaces.Woche;

public class TerminImpl implements Termin {

	String beschr;
	Datum dat;
	Dauer dau;

	public TerminImpl(String beschreibung, Datum datum, Dauer dauer) {
		beschr = beschreibung;
		dat = datum;
		dau = dauer;
	}

	@Override
	public int compareTo(Termin o) {
		return dat.compareTo(o.getDatum());
	}

	@Override
	public String getBeschreibung() {
		return beschr;
	}

	@Override
	public Datum getDatum() {
		return dat;
	}

	@Override
	public Dauer getDauer() {
		return dau;
	}

	@Override
	public Termin verschiebeAuf(Datum datum) {
		dat = datum;
		return this;
	}

	@Override
	public Map<Datum, Termin> termineIn(Monat monat) {
		Map<Datum, Termin> map = Collections.emptyMap();
		if (dat.compareTo(monat.getStart()) == 1) {
			if (dat.compareTo(monat.getEnde()) == -1)
				map.put(dat, new TerminImpl(beschr, dat, dau));
		}
		return map;
	}

	@Override
	public Map<Datum, Termin> termineIn(Woche woche) {
		Map<Datum, Termin> map = Collections.emptyMap();
		if (dat.compareTo(woche.getStart()) == 1) {
			if (dat.compareTo(woche.getEnde()) == -1)
				map.put(dat, new TerminImpl(beschr, dat, dau));
		}
		return map;
	}

	@Override
	public Map<Datum, Termin> termineAn(Tag tag) {
		Map<Datum, Termin> map = Collections.emptyMap();
		if(dat.getTag().compareTo(tag) == 0)
			map.put(dat, new TerminImpl(beschr, dat, dau));
		return map;
	}

}
