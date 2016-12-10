package kalender;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import kalender.interfaces.Datum;
import kalender.interfaces.Tag;

public class TagImpl implements Tag {

	private Calendar intern;
	private int tagImMonat;
	private int monat;
	private int jahr;

	public TagImpl(int jahr, int tagImJahr) {
		intern = Calendar.getInstance();
		intern.clear();
		intern.set(Calendar.YEAR, jahr);
		intern.set(Calendar.DAY_OF_YEAR, tagImJahr);
		tagImMonat = intern.get(Calendar.DAY_OF_MONTH);
		monat = intern.get(Calendar.MONTH);
		this.jahr = intern.get(Calendar.YEAR);
	}

	public TagImpl(int jahr, int monat, int tagImMonat) {
		intern = Calendar.getInstance();
		intern.clear();
		intern.set(jahr, monat, tagImMonat);
		this.tagImMonat = intern.get(Calendar.DAY_OF_MONTH);
		this.monat = intern.get(Calendar.MONTH);
		this.jahr = intern.get(Calendar.YEAR);
	}

	public TagImpl(Tag tag) {
		this(tag.getJahr(), tag.getMonat(), tag.getTagImMonat());
	}

	@Override
	public Datum getStart() {
		return new DatumImpl(this, new UhrzeitImpl());
	}

	@Override
	public Datum getEnde() {
		return new DatumImpl(this, new UhrzeitImpl(23, 59));
	}

	@Override
	public int compareTo(Tag o) {
		return (o.inBasis().compareTo(this.inBasis()));
	}

	@Override
	public int getJahr() {
		return jahr;
	}

	@Override
	public int getMonat() {
		return monat;
	}

	@Override
	public int getTagImJahr() {
		return intern.get(Calendar.DAY_OF_YEAR);
	}

	@Override
	public int getTagImMonat() {
		return tagImMonat;
	}

	@Override
	public long differenzInTagen(Tag other) {
		return TimeUnit.MILLISECONDS.toDays(Math.abs(intern.getTimeInMillis()
				- (new GregorianCalendar(other.getJahr(), other.getMonat(), other.getTagImMonat()).getTimeInMillis())));
	}

	@Override
	public Calendar inBasis() {
		return (Calendar) intern.clone();
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Tag) || (obj == null))
			return false;
		return (this.inBasis().compareTo(((Tag) obj).inBasis())==0);
	}
	

	@Override
	public String toString() {
 		return String.format("Tag %d,%d.%d", tagImMonat, monat + 1, jahr);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((intern == null) ? 0 : intern.hashCode());
		return result;
	}

	
}
