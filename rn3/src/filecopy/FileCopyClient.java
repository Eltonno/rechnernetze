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

	public int windowSize;

	public long serverErrorRate;

	// -------- Variables
	// current default timeout in nanoseconds
	private long timeoutValue = 100000000L;

	private byte[] receiveData;

	FileInputStream inputstream;

	Map<Integer, FCpacket> sendbuffer;

	FCpacket pinit;

	static DatagramSocket clientSocket;
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

	public void runFileCopyClient() throws IOException, InterruptedException {
		inputstream = new FileInputStream(sourcePath);
		sendbuffer = new HashMap<Integer, FCpacket>();
//		clientSocket = new DatagramSocket();
		int sendbase = 0;
		int nextSeqNum = 1;
		byte[] buf = new byte[1000];

		pinit = makeControlPacket();

		InetAddress address = InetAddress.getByName("localhost");
		DatagramPacket packet = new DatagramPacket(pinit.getSeqNumBytesAndData(), pinit.getLen() + 8, address,
				SERVER_PORT);
		clientSocket.send(packet);
		while (true) {
				TimeUnit.SECONDS.sleep(1);
			while (inputstream.available() != 0 && sendbuffer.size() <= windowSize) {
				System.out.println(sendbuffer.size());
				inputstream.read(buf);
				pinit = new FCpacket(nextSeqNum, buf, buf.length);
				packet = new DatagramPacket(pinit.getSeqNumBytesAndData(), pinit.getLen() + 8, address, SERVER_PORT);
				clientSocket.send(packet);
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
	 */
	public void timeoutTask(long seqNum) {
		// ToDo
	}

	/**
	 *
	 * Computes the current timeout value (in nanoseconds)
	 */
	public void computeTimeoutValue(long sampleRTT) {

		// ToDo
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
		FileCopyClient myClient = new FileCopyClient(argv[0], argv[1], argv[2], argv[3], argv[4]);
		clientSocket = new DatagramSocket();
		(new FileCopyClient(argv[0], argv[1], argv[2], argv[3], argv[4])).start();
		myClient.runFileCopyClient();
	}

	@Override
	public void run() {
		try {
			System.out.println("Hello from Thread.");
			byte[] data = new byte[FileCopyClient.UDP_PACKET_SIZE];
			while (true) {
				DatagramPacket udpReceivePacket = new DatagramPacket(data, data.length);
				// Wait for data packet
				System.out.println(clientSocket.getLocalPort());
				clientSocket.receive(udpReceivePacket);
				System.out.println("testing");
						FCpacket ackpack = new FCpacket(udpReceivePacket.getData(), udpReceivePacket.getLength());
						long receivedSeqNumber = ackpack.getSeqNum();
				if (sendbuffer.containsKey(receivedSeqNumber)) {
					sendbuffer.remove(receivedSeqNumber);
				}
				sleep(1);
			}
		} catch (Exception e) {

		}
	}
}
