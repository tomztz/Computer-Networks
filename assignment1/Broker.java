
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;



public class Broker{


	public static void main(String[] args) {
		final int RECV_PORT_FROM_SUB = 12345;
        
		
		
		final int MTU = 1500;

		DatagramPacket packet;
		DatagramSocket socket;

		DatagramPacket packetToSub;
		DatagramSocket socketToSub;
		InetAddress address;
		int port;

		byte[] bufferPacket;
        byte[] buffer;
        boolean hasTopic;
		ArrayList<byte[]>tempSub = new ArrayList<>();
		ArrayList<byte[]>windSub = new ArrayList<>();
		ArrayList<byte[]>humiditySub = new ArrayList<>();
		HashMap<Integer,String>addresses = new HashMap<>();
		addresses.put(12346,"subscriber");
		addresses.put(12349,"subscriberA");

		
		try {
			
			System.out.println("receiving - Program start");

			// extract destination from arguments
			address= InetAddress.getByName("subscriber");
			port= RECV_PORT_FROM_SUB;                     

			// create buffer for data, packet and socket
			bufferPacket= new byte[MTU];
			packet= new DatagramPacket(bufferPacket, bufferPacket.length);
			socket= new DatagramSocket(port);

			
			while(true){
			
			System.out.println("Broker : Trying to receive");
			socket.receive(packet);

			
			buffer= packet.getData();
			byte[] bufferModified1 = {};
			bufferModified1 = Arrays.copyOfRange(buffer, 0,8);
			if(bufferModified1[5]==0){
			  if(bufferModified1[6]==0 && bufferModified1[7]==1){
					tempSub.add(bufferModified1);
			}
			else if(bufferModified1[6]==0 && bufferModified1[7]==2){
					windSub.add(bufferModified1);
			}
			else if(bufferModified1[6]==0 && bufferModified1[7]==3){
					humiditySub.add(bufferModified1);
			}
			}
			
			
			
			
			hasTopic=false;
			ArrayList<byte[]>subs = new ArrayList<>();

			if(bufferModified1[5]==1){
				if(bufferModified1[6]==0&&bufferModified1[7]==1&&!tempSub.isEmpty()){
					subs = tempSub;
					hasTopic=true;
				}
				if(bufferModified1[6]==0&&bufferModified1[7]==2&&!windSub.isEmpty()){
					subs = windSub;
					hasTopic=true;

				}
				if(bufferModified1[6]==0&&bufferModified1[7]==3&&!humiditySub.isEmpty()){
					subs = humiditySub;
					hasTopic=true;
				}
	
			}
			

			System.out.println("Broker : recieved Program end");

            if(hasTopic){
			System.out.println("Broker : sending topic - Program start");

			for(byte[] head:subs){
				byte[] portToSub = Arrays.copyOfRange(head, 0, 5);
				StringBuilder sb = new StringBuilder();

				for (byte num : portToSub) 
				{
     			sb.append(num);
				}
				int finalPort = Integer.parseInt(sb.toString());
				System.out.println(finalPort);
				String addressOfSub=addresses.get(finalPort);
				// extract destination from arguments
				InetAddress addressToSend= InetAddress.getByName(addressOfSub);// Integer.parseInt(args[1]);
				
					// create packet addressed to destination
					packetToSub= new DatagramPacket(buffer, 
					buffer.length,
					addressToSend, finalPort);
		
					// create socket and send packet
					socketToSub= new DatagramSocket();
					socketToSub.send(packetToSub);
					
			}
			
			
			


			System.out.println("Broker : topic sent - Program end");
            }
		}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	
	}




}