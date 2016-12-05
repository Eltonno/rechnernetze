package filecopy;

/* FileCopyClient.java
 Version 0.1 - Muss ergaenzt werden!!
 Praktikum 3 Rechnernetze BAI4 HAW Hamburg
 Autoren:
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class FileCopyClient extends Thread {

	// -------- Constants
	public final static boolean TEST_OUTPUT_MODE = false;

	public final int SERVER_PORT = 23000;

	public final static int UDP_PACKET_SIZE = 1008;

	// -------- Public parms
	public String servername;

	public String sourcePath;

	public String destPath;
	
	static public InetAddress address;

	public int windowSize;

	public long serverErrorRate;

	// -------- Variables
	// current default timeout in nanoseconds
	private long timeoutValue = 100000000L;
	
	public long timestamp;

	private byte[] receiveData;
	
	private long rtt;
	
	private long jitter = 0L;

	FileInputStream inputstream;

	static Map<Long, FCpacket> sendbuffer;

	FCpacket pinit;

	static DatagramSocket clientSocket;

	long sendbase = 0;
	// ... ToDo

	// Constructor
	public FileCopyClient(String serverArg, String sourcePathArg, String destPathArg, String windowSizeArg,
			String errorRateArg) {
		servername = serverArg;
		sourcePath = sourcePathArg;
		destPath = destPathArg;
		windowSize = Integer.parseInt(windowSizeArg);
		serverErrorRate = Long.parseLong(errorRateArg);

	}

	public void runFileCopyClient() throws IOException {
		inputstream = new FileInputStream(sourcePath);
		// clientSocket = new DatagramSocket();
		long nextSeqNum = 1;
		byte[] buf = new byte[1000];

		pinit = makeControlPacket();

		DatagramPacket packet = new DatagramPacket(pinit.getSeqNumBytesAndData(), pinit.getLen() + 8, address,
				SERVER_PORT);
		clientSocket.send(packet);
		while (true) {
			while (inputstream.available() != 0 && sendbuffer.size() < windowSize) {
				inputstream.read(buf);
				pinit = new FCpacket(nextSeqNum, buf, buf.length);
				packet = new DatagramPacket(pinit.getSeqNumBytesAndData(), pinit.getLen() + 8, address, SERVER_PORT);
				pinit.setTimestamp(System.nanoTime());
				clientSocket.send(packet);
				startTimer(pinit);
				sendbuffer.put(nextSeqNum, pinit);
				nextSeqNum += 1;
			}
		}
	}

	/**
	 *
	 * Timer Operations
	 */
	public void startTimer(FCpacket packet) {
		/* Create, save and start timer for the given FCpacket */
		FC_Timer timer = new FC_Timer(timeoutValue, this, packet.getSeqNum());
		packet.setTimer(timer);
		timer.start();
	}

	public void cancelTimer(FCpacket packet) {
		/* Cancel timer for the given FCpacket */
		testOut("Cancel Timer for packet" + packet.getSeqNum());

		if (packet.getTimer() != null) {
			packet.getTimer().interrupt();
		}
	}

	/**
	 * Implementation specific task performed at timeout
	 * @throws IOException 
	 */
	public void timeoutTask(long seqNum) throws IOException {
		FCpacket fcp = sendbuffer.get(seqNum);
		DatagramPacket pack = new DatagramPacket(fcp.getSeqNumBytesAndData(), fcp.getLen() + 8, address, SERVER_PORT);
		clientSocket.send(pack);
		fcp.setTimestamp(System.nanoTime());
		startTimer(fcp);
	}

	/**
	 *
	 * Computes the current timeout value (in nanoseconds)
	 */
	public void computeTimeoutValue(long sampleRTT) {
		int timeoutsec = (int) (timeoutValue/1000000);
	    double x = 0.25;
	    double y = x/2;
	    long expRTT = (long) ((1-y) *sampleRTT + y*timeoutValue);
	    long absolut = Math.abs(sampleRTT - rtt);
	    long newjitter = (long) ((1-x) * jitter + x *  absolut);
	    rtt = sampleRTT;
	    timeoutValue = expRTT + 4*newjitter;
	    System.out.println("new timeout: " + timeoutsec + "micro s");
	  }

	/**
	 *
	 * Return value: FCPacket with (0 destPath;windowSize;errorRate)
	 */
	public FCpacket makeControlPacket() {
		/*
		 * Create first packet with seq num 0. Return value: FCPacket with (0
		 * destPath ; windowSize ; errorRate)
		 */
		String sendString = destPath + ";" + windowSize + ";" + serverErrorRate;
		byte[] sendData = null;
		try {
			sendData = sendString.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new FCpacket(0, sendData, sendData.length);
	}

	public void testOut(String out) {
		if (TEST_OUTPUT_MODE) {
			System.err.printf("%,d %s: %s\n", System.nanoTime(), Thread.currentThread().getName(), out);
		}
	}

	public static void main(String argv[]) throws Exception {
		address = InetAddress.getByName("localhost");
		FileCopyClient myClient = new FileCopyClient(argv[0], argv[1], argv[2], argv[3], argv[4]);
		clientSocket = new DatagramSocket();
		sendbuffer = new HashMap<Long, FCpacket>();
		(new FileCopyClient(argv[0], argv[1], argv[2], argv[3], argv[4])).start();
		myClient.runFileCopyClient();
	}

	@Override
	public void run() {
		try {
			byte[] data = new byte[FileCopyClient.UDP_PACKET_SIZE];
			while (true) {
				DatagramPacket udpReceivePacket = new DatagramPacket(data, data.length);
				// Wait for data packet
				clientSocket.receive(udpReceivePacket);
			    rtt = System.nanoTime() - timestamp + 10000000L;
				FCpacket ackpack = new FCpacket(udpReceivePacket.getData(), udpReceivePacket.getLength());
				long receivedSeqNumber = ackpack.getSeqNum();
				if (sendbuffer.containsKey(receivedSeqNumber)) {
					cancelTimer(sendbuffer.get(receivedSeqNumber));
					sendbuffer.remove(receivedSeqNumber);
					long packrtt = System.nanoTime() - ackpack.getTimestamp();
					computeTimeoutValue(packrtt);
//					if(sendbuffer.size() >= 0)
//					sendbase = Collections.min(sendbuffer.keySet());
					System.out.println(sendbase);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
