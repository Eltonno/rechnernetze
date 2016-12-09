package kalender;

import java.util.concurrent.TimeUnit;

import kalender.interfaces.*;

public class Ausprobieren {

	public static void main(String[] args) throws InterruptedException {
		TerminKalender tk = new TerminKalenderImpl();
		TerminMitWiederholung tmw = new TerminMitWiederholungImpl("TEST!", new DatumImpl(new TagImpl(2017, 350)), new DauerImpl(24, 0, 0), WiederholungType.WOECHENTLICH, 100, 1);
		System.out.println(tmw.termineFuer(new MonatImpl(2018, 3)));
//		System.out.println(tk.eintragen(tmw));
//		TimeUnit.SECONDS.sleep(1);
//		System.out.println(tk.enthaeltTermin(tmw));
//		tk.eintragen(new TerminImpl("Test", new DatumImpl(new TagImpl(2016,9,29)),new DauerImpl(3,0)));
		//System.out.println(tk.toString());	}

}}
