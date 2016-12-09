package kalender;

import java.util.concurrent.TimeUnit;

import kalender.interfaces.*;

public class Ausprobieren {

	public static void main(String[] args) throws InterruptedException {
		TerminKalender tk = new TerminKalenderImpl();
		TerminMitWiederholung tmw = new TerminMitWiederholungImpl("TEST!", new DatumImpl(new TagImpl(2017, 350), new UhrzeitImpl(12, 35)), new DauerImpl(24, 0, 0), WiederholungType.TAEGLICH, 50, 3);
		Tag t = new TagImpl(2005, 9, 23);
		Uhrzeit u = new UhrzeitImpl(18, 42);
		System.out.println("Uhrzeit test: " + u);
		System.out.println("Tag test: " +  t);
		Datum d = new DatumImpl(t, u);
		System.out.println("Datum test: " + d);
		Dauer da = new DauerImpl(5, 3, 28);
		System.out.println("Dauer test: " + da);
		Monat m = new MonatImpl(2018, 3);
		System.out.println("Monat test:" + m);
		Termin te = new TerminImpl("Normaler Testtermin", d, da);
		System.out.println("Termin test: " + te);
		Woche w = new WocheImpl(2016, 4, 2);
		System.out.println("Woche test: " + w);
		System.out.println(tmw.termineFuer(m));
//		System.out.println(tk.eintragen(tmw));
//		TimeUnit.SECONDS.sleep(1);
//		System.out.println(tk.enthaeltTermin(tmw));
//		tk.eintragen(new TerminImpl("Test", new DatumImpl(new TagImpl(2016,9,29)),new DauerImpl(3,0)));
		//System.out.println(tk.toString());	}

}}
