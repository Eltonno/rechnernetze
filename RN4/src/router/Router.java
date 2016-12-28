package router;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Router {
	
	public Router() throws IOException{
		NetworkLayer nl = new NetworkLayer(5000);
		IpPacket ipp = nl.getPacket();
		System.out.println(ipp.getDestinationAddress());
		ipp.setNextPort(8000);
		nl.sendPacket(ipp);
		IpPacket retp = nl.getPacket();
		System.out.println(retp.getSourceAddress());
		nl.sendPacket(retp);
	}
	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(new FileReader("/Users/ditmarlange/git/rechnernetze/RN4/src/router/routen.txt"));
		while (in.hasNextLine()) {
			System.out.println(in.nextLine());
		}
		Router rt = new Router();
	}
	
}
