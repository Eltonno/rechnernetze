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

	private long retransmit_packs;

	public static long received_acks;

	private static long total_rtt;

	private long jitter = 0L;

	FileInputStream inputstream;

	static Map<Long, FCpacket> sendbuffer;

	FCpacket pinit;

	static DatagramSocket clientSocket;

	static long sendbase = 0L;
	// ... ToDo

	// Constructor
	public FileCopyClient(String serverArg, String sourcePathArg, String destPathArg, String windowSizeArg,
			String errorRateArg) {
		servername = serverArg;
		sourcePath = sourcePathArg;
		destPath = destPathArg;
		windowSize = Integer.parseInt(windowSizeArg);
		serverErrorRate = Long.parseLong(errorRateArg);
		total_rtt = 0L;
		received_acks = 0L;
		retransmit_packs = 0L;

	}

	public void runFileCopyClient() throws IOException, InterruptedException {
		inputstream = new FileInputStream(sourcePath);
		// clientSocket = new DatagramSocket();
		long nextSeqNum = 1;
		byte[] buf = new byte[1000];
		long starttime = System.nanoTime();

		pinit = makeControlPacket();

		DatagramPacket packet = new DatagramPacket(pinit.getSeqNumBytesAndData(), pinit.getLen() + 8, address,
				SERVER_PORT);
		clientSocket.send(packet);
		sendbuffer.put(0L, pinit);
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
			if (inputstream.available() == 0) {
				break;
			}
		}
		TimeUnit.SECONDS.sleep(3);
		long total_time = System.nanoTime() - starttime;
		System.out.println("-------------------- END OF DATA TRANSFER ------------------");
		System.out.println("Total_Time: " + total_time + "ns");
		System.out.println("Number of Retransmit: " + retransmit_packs);
		System.out.println("Number of Received Acks: " + received_acks);
		System.out.println("Total rtt: " + total_rtt);
		long average_rtt = total_rtt / received_acks;
		System.out.println("Average RTT: " + average_rtt + " ns");
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
	 * 
	 * @throws IOException
	 */
	public void timeoutTask(long seqNum) throws IOException {
		FCpacket fcp = sendbuffer.get(seqNum);
		DatagramPacket pack = new DatagramPacket(fcp.getSeqNumBytesAndData(), fcp.getLen() + 8, address, SERVER_PORT);
		clientSocket.send(pack);
		retransmit_packs++;
		sendbuffer.get(seqNum).setTimestamp(System.nanoTime());
		startTimer(sendbuffer.get(seqNum));
	}

	/**
	 *
	 * Computes the current timeout value (in nanoseconds)
	 */
	public void computeTimeoutValue(long sampleRTT) {
		int timeoutsec = (int) (timeoutValue / 1000000);
		double x = 0.25;
		double y = x / 2;
		long expRTT = (long) ((1 - y) * sampleRTT + y * timeoutValue);
		long absolut = Math.abs(sampleRTT - rtt);
		long newjitter = (long) ((1 - x) * jitter + x * absolut);
		rtt = sampleRTT;
		timeoutValue = expRTT + 4 * newjitter;
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

	public static void ack_plus() {
		received_acks++;
	}

	public static void rtt_plus(long rtt) {
		total_rtt = total_rtt + rtt;
	}

	public static void sendbase_plus() {
		sendbase++;
	}

	@Override
	public void run() {
		try {
			byte[] data = new byte[FileCopyClient.UDP_PACKET_SIZE];
			while (true) {

				DatagramPacket udpReceivePacket = new DatagramPacket(data, data.length);
				// Wait for data packet
				clientSocket.receive(udpReceivePacket);
				FCpacket ackpack = new FCpacket(udpReceivePacket.getData(), udpReceivePacket.getLength());
				long receivedSeqNumber = ackpack.getSeqNum();
				System.out.println(receivedSeqNumber);
				if (sendbuffer.containsKey(receivedSeqNumber)) {
					cancelTimer(sendbuffer.get(receivedSeqNumber));
					long packrtt = System.nanoTime() - sendbuffer.get(receivedSeqNumber).getTimestamp();
					System.out.println(packrtt);
					if(receivedSeqNumber!=0){
					rtt_plus(packrtt);
					computeTimeoutValue(packrtt);
					}
					sendbuffer.get(receivedSeqNumber).setValidACK(true);
					if (receivedSeqNumber == sendbase) {
						FileCopyClient.ack_plus();
						sendbuffer.remove(receivedSeqNumber);
						FileCopyClient.sendbase_plus();
						while (sendbuffer.containsKey(sendbase) && sendbuffer.get(sendbase).isValidACK()) {
							FileCopyClient.ack_plus();
							sendbuffer.remove(receivedSeqNumber);
							FileCopyClient.sendbase_plus();

						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
