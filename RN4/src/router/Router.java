package router;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class Router extends Thread {
	static Map<String, String[]> routtable = new HashMap<String, String[]>();
	static IpPacket ipp;
	static NetworkLayer nl;

	public Router() throws IOException {
		int i = 0;
		while (i < 2) {
			System.out.println("Vor getpacket");
			ipp = nl.getPacket();
			System.out.println(ipp.getDestinationAddress());
			System.out.println(ipp.getSourceAddress());
			ipp.setHopLimit(ipp.getHopLimit()-1);
			if(ipp.getHopLimit()<1){
				ControlPacket.Type controlType = ControlPacket.Type.valueOf("TimeExceeded");
				ControlPacket controlPacket = new ControlPacket(controlType, new byte[0]);
				ipp.setControlPayload(controlPacket.getBytes());
				// ipp.setDestinationAddress(ipp.getSourceAddress());
				// ipp.setNextHopIp(ipp.getDestinationAddress());
				nl.sendPacket(ipp);
			} else if (!routtable.containsKey(String.valueOf(ipp.getDestinationAddress()))) {
				System.out.println("HIER");
				ControlPacket.Type controlType = ControlPacket.Type.valueOf("DestinationUnreachable");
				ControlPacket controlPacket = new ControlPacket(controlType, new byte[0]);
				ipp.setControlPayload(controlPacket.getBytes());
				// ipp.setDestinationAddress(ipp.getSourceAddress());
				// ipp.setNextHopIp(ipp.getDestinationAddress());
				nl.sendPacket(ipp);
			} else {

				ipp.setNextPort(Integer.valueOf(routtable.get(String.valueOf(ipp.getDestinationAddress()))[1]));

				Inet6Address nexthopip = (Inet6Address) InetAddress
						.getByName((routtable.get(String.valueOf(ipp.getDestinationAddress()))[0]));
				ipp.setNextHopIp(nexthopip);
				System.out.println("Nexthopip: " + nexthopip + " Nextport: " + ipp.getNextHopPort());
			System.out.println(Arrays.toString(nexthopip.getAddress()));
				nl.sendPacket(ipp);
				// IpPacket retp;
				// retp = nl.getPacket();
				// System.out.println(retp.getDestinationAddress());
				// retp.setNextPort(5000);
				// nl.sendPacket(retp);

			}
		}
		i++;
	}

	public static void main(String[] args) throws IOException {
		nl = new NetworkLayer(5000);
		Scanner in = new Scanner(new FileReader("/Users/ditmarlange/git/rechnernetze/RN4/src/router/routen.txt"));
		while (in.hasNextLine()) {
			String line = in.nextLine();
			String[] stary = line.split(";");
			//System.out.println(line);
			String[] stary2 = { stary[1], stary[2] };
			routtable.put(stary[0], stary2);
		}
		//System.out.println(routtable.keySet());
		Router rt = new Router();
	}

	// @Override
	// public void run() {
	// }
}
