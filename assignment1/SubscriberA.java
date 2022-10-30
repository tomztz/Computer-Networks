import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Scanner;



public class SubscriberA{


	public static void main(String[] args) {
		final int RECV_PORT = 12349;
		final int DEST_PORT = 12345;
		final int MTU = 1500;

		DatagramPacket packetSub;
		DatagramSocket socketSub;
		DatagramPacket packetRev;
		DatagramSocket socketRev;
		InetAddress address;
		int portSend;
		int portRev;
		byte[] bufferRev;

		ObjectInputStream ostreamRev;
		ByteArrayInputStream bstreamRev;
		ObjectOutputStream ostreamSend;
		ByteArrayOutputStream bstreamSend;
		byte[] payload;
		String data;
        byte[] header;
		byte[] wind = {1,2,3,4,9,0,0,2};
		byte[] temp = {1,2,3,4,9,0,0,1};
		byte[]humidity= {1,2,3,4,9,0,0,3};
		
		try {
			portRev= RECV_PORT;                     
			bufferRev= new byte[MTU];
			packetRev= new DatagramPacket(bufferRev, bufferRev.length);
			socketRev= new DatagramSocket(portRev);
			while(true){
            System.out.println("type a topic to subscribe i.e. temp,wind,humidity");
			Scanner scanner = new Scanner(System.in);
			String topic = scanner.next();
            System.out.println("Subscriber : subscription request");
            address= InetAddress.getByName("broker");
			portSend= DEST_PORT;                       

			
			bstreamSend= new ByteArrayOutputStream();
			ostreamSend= new ObjectOutputStream(bstreamSend);
			ostreamSend.writeUTF("TopicA");
			ostreamSend.flush();
			payload= bstreamSend.toByteArray();
			if(topic.equals("temp")){
				header =  temp;
			}
			else if(topic.equals("wind")){
				header =  wind;
			}
			else {
				header = humidity;
			}
			int count = 0;
			byte[] buffer = new byte[MTU];
			for(int i=0;i<header.length;i++){
				buffer[i]=header[i];
				count++;
			}
			for(int j=0;j<payload.length;j++){
				buffer[count++]=payload[j];
			}
			
			// create packet addressed to destination
			packetSub= new DatagramPacket(buffer, buffer.length,
					address, portSend);

			// create socket and send packet
			socketSub= new DatagramSocket();
			socketSub.send(packetSub);

			System.out.println("Subsucriber : subsrciption requested");
            
			System.out.println("Subsucriber : waiting for topic");

			// extract destination from arguments
			address= InetAddress.getByName("broker");

            
			
			// attempt to receive packet
            while(true){
			System.out.println("Subsucriber : Trying to receive");
			socketRev.receive(packetRev);

			// extract data from packet
			buffer= packetRev.getData();
            byte[] bufferModified = Arrays.copyOfRange(buffer, 8,buffer.length);
            bstreamRev= new ByteArrayInputStream(bufferModified);
			ostreamRev= new ObjectInputStream(bstreamRev);

			// print data and end of program
			data = ostreamRev.readUTF();
			System.out.println(data);
			System.out.println("Subsucriber : topic received");
			System.out.println("subscribe to another topic y/n");
			Scanner sc = new Scanner(System.in);
			String exit = sc.next();
			if(exit.equals("y")){
			    break;
			}
			else if(exit.equals("n")){
				continue;
			}
			else{
				System.out.println("invalid input - program end");
				System.exit(-1);
			}
			}
            
		}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		

	}




}
