package kalender;

import kalender.interfaces.Tag;
import kalender.interfaces.TerminKalender;

public class Ausprobieren {

	public static void main(String[] args) {
		TerminKalender tk = new TerminKalenderImpl();
		Tag tag = new TagImpl(2016, 300);
		
//		tk.eintragen(new TerminImpl("Test", new DatumImpl(new TagImpl(2016,9,29)),new DauerImpl(3,0)));
		System.out.println(tag.toString());	}

}
