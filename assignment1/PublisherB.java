import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class PublisherB {

	public static void main(String[] args) {
		final int DEST_PORT = 12345;

		DatagramPacket packet;
		DatagramSocket socket;
		InetAddress address;
		int port;

		ObjectOutputStream ostream;
		ByteArrayOutputStream bstream;
		byte[] buffer;

		try {
			System.out.println("Publisher - start to publish");

			// extract destination from arguments
			address= InetAddress.getByName("broker");
			port= DEST_PORT;                       

			bstream= new ByteArrayOutputStream();
			ostream= new ObjectOutputStream(bstream);
			ostream.writeUTF("85 %rh");
			ostream.flush();
			buffer= bstream.toByteArray();
			byte[] header =  {1,2,3,4,5,1,0,3};
			int count = 0;
			byte[] buffer1 = new byte[1500];
			for(int i=0;i<header.length;i++){
				buffer1[i]=header[i];
				count++;
			}
			for(int j=0;j<buffer.length;j++){
				buffer1[count++]=buffer[j];
			}

			// create packet addressed to destination
			packet= new DatagramPacket(buffer1, buffer1.length,
					address, port);

			// create socket and send packet
			socket= new DatagramSocket(port);
			socket.send(packet);

			System.out.println("Publisher - Publish end");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}