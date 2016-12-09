package kalender;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import kalender.interfaces.Datum;
import kalender.interfaces.Dauer;
import kalender.interfaces.Monat;
import kalender.interfaces.Tag;
import kalender.interfaces.Uhrzeit;
import kalender.interfaces.Woche;

public class DatumImpl implements Datum {

	private Calendar intern;

	public DatumImpl(Tag tag) {
		this(tag, new UhrzeitImpl());
	}

	public DatumImpl(Tag tag, Uhrzeit uhrzeit) {
		intern = Calendar.getInstance();
		intern.set(tag.getJahr(), tag.getMonat(), tag.getTagImMonat(), uhrzeit.getStunde(), uhrzeit.getMinuten());
	}

	public DatumImpl(Datum d) {
		this(d.getTag(), d.getUhrzeit());
	}

	private DatumImpl(Calendar intern) {
		intern = Calendar.getInstance();
		this.intern = intern;
	}

	@Override
	public int compareTo(Datum o) {
		return (o.inBasis().compareTo(this.inBasis()));
	}

	@Override
	public Tag getTag() {
		return new TagImpl(intern.get(Calendar.YEAR), intern.get(Calendar.MONTH), intern.get(Calendar.DAY_OF_MONTH));
	}

	@Override
	public Woche getWoche() {
		return new WocheImpl(intern.get(Calendar.YEAR), intern.get(Calendar.MONTH), intern.get(Calendar.WEEK_OF_MONTH));
	}

	@Override
	public Monat getMonat() {
		return new MonatImpl(intern.get(Calendar.YEAR), intern.get(Calendar.MONTH));
	}

	@Override
	public Uhrzeit getUhrzeit() {
		return new UhrzeitImpl(intern.get(Calendar.HOUR_OF_DAY), intern.get(Calendar.MINUTE));
	}

	@Override
	public int getJahr() {
		return intern.get(Calendar.YEAR);
	}

	@Override
	public int getTagImMonat() {
		return intern.get(Calendar.DAY_OF_MONTH);
	}

	@Override
	public int getTagImJahr() {
		return intern.get(Calendar.DAY_OF_YEAR);
	}

	@Override
	public int getWocheImMonat() {
		return intern.get(Calendar.WEEK_OF_MONTH);
	}

	@Override
	public int getWocheImJahr() {
		return intern.get(Calendar.WEEK_OF_YEAR);
	}

	@Override
	public int getMonatImJahr() {
		return intern.get(Calendar.MONTH);
	}

	@Override
	public Datum add(Dauer dauer) {
		intern.add(Calendar.MINUTE, dauer.inMinuten());
		return this;
	}

	@Override
	public Datum sub(Dauer dauer) {
		intern.add(Calendar.MINUTE, -(dauer.inMinuten()));
		return this;
	}

	@Override
	public Dauer abstand(Datum d) {
		return new DauerImpl((int) TimeUnit.MILLISECONDS.toMinutes(Math.abs(intern.getTimeInMillis()
				- (new GregorianCalendar(d.getJahr(), d.getMonatImJahr(), d.getTagImMonat())).getTimeInMillis())));
	}

	@Override
	public long differenzInTagen(Datum d) {
		return this.getTag().differenzInTagen(d.getTag());
	}

	@Override
	public int inMinuten() {
		return ((int) (intern.getTimeInMillis() / 1000 / 60));
	}

	@Override
	public Calendar inBasis() {
		return (Calendar) intern.clone();
	}
	
	@Override
	public String toString(){
		return String.format(this.getTag().toString() + this.getUhrzeit().toString());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((intern == null) ? 0 : intern.hashCode());
		return result;
	}
}
