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
	String deststr = "";

	public Router() throws IOException {
		int i = 0;
		while (true) {
			System.out.println("Vor getpacket");
			ipp = nl.getPacket();
			String findest = "";
			Scanner destsc = new Scanner(ipp.getDestinationAddress().toString());
			// System.out.println(ipp.getDestinationAddress().toString());
			destsc.useDelimiter(":");
			while (destsc.hasNext()) {
				String next = destsc.next();
				if (next.charAt(0) == '/') {
					next = next.substring(1);
				}
				if (next.length() == 1) {
					findest += "000000000000";
				} else if (next.length() == 2) {
					findest += "00000000";
				} else if (next.length() == 3) {
					findest += "0000";
				}
				for (i = 0; i < next.length(); i++) {
					char strch = next.charAt(i);
					switch (strch) {
					case '0':
						findest += "0000";
						break;
					case '1':
						findest += "0001";
						break;
					case '2':
						findest += "0010";
						break;
					case '3':
						findest += "0011";
						break;
					case '4':
						findest += "0100";
						break;
					case '5':
						findest += "0101";
						break;
					case '6':
						findest += "0110";
						break;
					case '7':
						findest += "0111";
						break;
					case '8':
						findest += "1000";
						break;
					case '9':
						findest += "1001";
						break;
					case 'a':
						findest += "1010";
						break;
					case 'b':
						findest += "1011";
						break;
					case 'c':
						findest += "1100";
						break;
					case 'd':
						findest += "1101";
						break;
					case 'e':
						findest += "1110";
						break;
					case 'f':
						findest += "1111";
						break;
					}
				}
			}

			// System.out.println(findest);
			System.out.println(ipp.getDestinationAddress());
			System.out.println(ipp.getSourceAddress());
			ipp.setHopLimit(ipp.getHopLimit() - 1);
			if (ipp.getHopLimit() < 1) {
				ControlPacket.Type controlType = ControlPacket.Type.valueOf("TimeExceeded");
				ControlPacket controlPacket = new ControlPacket(controlType, new byte[0]);
				ipp.setControlPayload(controlPacket.getBytes());
				// ipp.setDestinationAddress(ipp.getSourceAddress());
				// ipp.setNextHopIp(ipp.getDestinationAddress());
				nl.sendPacket(ipp);
			} else {
				int index = 0;
				int longestindex = 0;
				for (String s : routtable.keySet()) {
					for (char c : findest.toCharArray()) {
						if (index == s.length())
							break;
						if (c != s.charAt(index))
							break;
						index++;
					}
					if (index > longestindex) {
						longestindex = index;
						deststr = s;
					}
					if (index == s.length()) {
						break;
					}
				}
				if (index == 0) {
					System.out.println("no common prefix");
					ControlPacket.Type controlType = ControlPacket.Type.valueOf("DestinationUnreachable");
					ControlPacket controlPacket = new ControlPacket(controlType, new byte[0]);
					ipp.setControlPayload(controlPacket.getBytes());
					// ipp.setDestinationAddress(ipp.getSourceAddress());
					// ipp.setNextHopIp(ipp.getDestinationAddress());
					nl.sendPacket(ipp);
					deststr = "-1";
				} else
					System.out.println("Longest prefix sagt: " + deststr);
			}
			if (!deststr.matches("-1")) {
				ipp.setNextPort(Integer.valueOf(routtable.get(deststr)[1]));

				Inet6Address nexthopip = (Inet6Address) InetAddress.getByName((routtable.get(deststr)[0]));
				ipp.setNextHopIp(nexthopip);
				System.out.println("Nexthopip: " + nexthopip + " Nextport: " + ipp.getNextHopPort());
				nl.sendPacket(ipp);
			}
		}
		// IpPacket retp;
		// retp = nl.getPacket();
		// System.out.println(retp.getDestinationAddress());
		// retp.setNextPort(5000);
		// nl.sendPacket(retp);

	}

	public static void main(String[] args) throws IOException {
		nl = new NetworkLayer(5000);
		Scanner in = new Scanner(new FileReader(argv[0]));
		while (in.hasNextLine()) {
			String line = in.nextLine();
			String[] stary = line.split(";");
			// System.out.println(line);
			String[] stary2 = { stary[1], stary[2] };
			routtable.put(stary[0], stary2);
		}
		// System.out.println(routtable.keySet());
		Router rt = new Router();
	}

	// @Override
	// public void run() {
	// }
}
