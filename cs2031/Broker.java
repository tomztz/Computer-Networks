import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.io.File;
import java.io.FileInputStream;

public class Broker extends Node {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
	static final int DEFAULT_PORT = 50001;
    static final int DEFAULT_SRC_PORT = 50000;
	static final int DEFAULT_DST_PORT = 50002;
	static final String DEFAULT_DST_NODE = "server";
	InetSocketAddress dstAddress;
	/*
	 *
	 */
	Broker(int port,String dstHost, int dstPort, int srcPort) {
		try {
			socket= new DatagramSocket(port);
			listener.go();
            dstAddress= new InetSocketAddress(dstHost, dstPort);
			socket= new DatagramSocket(srcPort);
			listener.go();
		}
		catch(java.lang.Exception e) {e.printStackTrace();}
	}

	/**
	 * Assume that incoming packets contain a String and print the string.
	 */
	public void onReceipt1(DatagramPacket packet) {
		try {
			System.out.println("Received packet");

			PacketContent content= PacketContent.fromDatagramPacket(packet);

			if (content.getType()==PacketContent.FILEINFO) {
				System.out.println("File name: " + ((FileInfoContent)content).getFileName());
				System.out.println("File size: " + ((FileInfoContent)content).getFileSize());

				DatagramPacket response;
				response= new AckPacketContent("OK - Received this").toDatagramPacket();
				response.setSocketAddress(packet.getSocketAddress());
				socket.send(response);
			}
		}
		catch(Exception e) {e.printStackTrace();}
	}


	public synchronized void ReceiveFromPubs() throws Exception {
		System.out.println("Waiting for contact");
		this.wait();
	}

    public synchronized void onReceipt(DatagramPacket packet) {
		PacketContent content= PacketContent.fromDatagramPacket(packet);

		System.out.println(content.toString());
		this.notify();
	}




	/**
	 * Sender Method
	 *
	 */
	public synchronized void start() throws Exception {
		String fname;
		File file= null;
		FileInputStream fin= null;

		FileInfoContent fcontent;

		int size;
		byte[] buffer= null;
		DatagramPacket packet= null;

		fname= "message.txt";//terminal.readString("Name of file: ");

		file= new File(fname);				// Reserve buffer for length of file and read file
		buffer= new byte[(int) file.length()];
		fin= new FileInputStream(file);
		size= fin.read(buffer);
		if (size==-1) {
			fin.close();
			throw new Exception("Problem with File Access:"+fname);
		}
		System.out.println("File size: " + buffer.length);

		fcontent= new FileInfoContent(fname, size);

		System.out.println("Sending packet w/ name & length"); // Send packet with file name and length
		packet= fcontent.toDatagramPacket();
		packet.setSocketAddress(dstAddress);
		socket.send(packet);
		System.out.println("Packet sent");
		this.wait();
		fin.close();
	}

	/*
	 *
	 */
	public static void main(String[] args) {
		try {
			Broker broker =(new Broker(DEFAULT_PORT,DEFAULT_DST_NODE, DEFAULT_DST_PORT, DEFAULT_SRC_PORT));
            broker.ReceiveFromPubs();
            broker.start();
            System.out.println("Program completed");
            
		
		} catch(java.lang.Exception e) {e.printStackTrace();}
	}
}
