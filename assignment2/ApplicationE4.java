import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.util.Arrays;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

public class ApplicationE4 {
	public static void main(String[] args) {
		final int DEST_PORT = 51510;
        final int SRC_PORT = 53521;
        final int MTU = 1500;

		DatagramPacket packet;
		DatagramSocket socket;
		InetAddress address;
		int port;
        DatagramPacket packetRev;
		DatagramSocket socketRev;

		ObjectOutputStream ostream1;
		ByteArrayOutputStream bstream1;
		ObjectOutputStream ostream2;
		ByteArrayOutputStream bstream2;
        
		ObjectInputStream ostreamRev;
		ByteArrayInputStream bstreamRev;
		byte[] buffer;
		byte[] buffer1;
        byte[]bufferRev;
		String input;
		String addressID="";
        int portRev;
		

		try {
			System.out.println("Application - program begin");
            System.out.println("Send or receive? s/r");
            Scanner scanner = new Scanner(System.in);
            String SR = scanner.next();
            if(SR.equals("s")){
			System.out.println("Type address to forward to:");
			Scanner sc = new Scanner(System.in);
			input = sc.next();

			// extract destination from arguments

			
			port= DEST_PORT;                       

			
			
			address= InetAddress.getLocalHost();
			bstream1= new ByteArrayOutputStream();
			ostream1= new ObjectOutputStream(bstream1);
			ostream1.writeUTF("Hello");
			ostream1.flush();
			buffer1 = bstream1.toByteArray();

			bstream2= new ByteArrayOutputStream();
			ostream2= new ObjectOutputStream(bstream2);
			ostream2.writeUTF(input);
			ostream2.flush();
			buffer= bstream2.toByteArray();

			byte[] buffer2 = new byte[1500];
			byte[] TL =  {1,(byte)buffer.length};
			byte[] header = new byte[2+buffer.length];
			int count1 = 0;
			for(int i=0;i<TL.length;i++){
				header[i]=TL[i];
				count1++;
			}
			for(int j=0;j<buffer.length;j++){
				header[count1++]= buffer[j];

			}
			int count = 0;

			for(int i=0;i<header.length;i++){
				buffer2[i]=header[i];
				count++;
			}
			for(int j=0;j<buffer1.length;j++){
				buffer2[count++]=buffer1[j];
			}

			// create packet addressed to destination
			packet= new DatagramPacket(buffer2, buffer2.length,
					address, port);

			// create socket and send packet
			socket= new DatagramSocket();
			socket.send(packet);
        }

        else{
            System.out.println("Application : Trying to receive");
            portRev= SRC_PORT;                     
			bufferRev= new byte[MTU];
			packetRev= new DatagramPacket(bufferRev, bufferRev.length);
			socketRev= new DatagramSocket(portRev);
			
			socketRev.receive(packetRev);

			// extract data from packet
			buffer= packetRev.getData();
			byte[] bufferModified1 = {};
			bufferModified1 = Arrays.copyOfRange(buffer,buffer[1]+2,buffer.length);
			bstreamRev= new ByteArrayInputStream(bufferModified1);
			ostreamRev= new ObjectInputStream(bstreamRev);

			// print data and end of program
			String data = ostreamRev.readUTF();
			System.out.println(data);
			System.out.println("Application : topic received");
        }

			System.out.println("Application - program end");

		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
