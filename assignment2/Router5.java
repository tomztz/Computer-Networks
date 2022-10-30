import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;


public class Router5{


	public static void main(String[] args) {
		final int PORT = 51510;	
		final int MTU = 1500;

		DatagramPacket packet;
		DatagramPacket packetTemp;
		DatagramSocket socket;

		ObjectInputStream ostream;
		ByteArrayInputStream bstream;

		ObjectInputStream ostream1;
		ByteArrayInputStream bstream1;
		DatagramSocket socket1;

		DatagramPacket packet2;
		DatagramSocket socket2;

		int port;
		
		byte[] bufferPacket;
		byte[] buffer1;
		String flowTable[][];
		flowTable = new String[MTU][MTU];
		int index = 0;
		String addressID = "";
		boolean hasAdd = false;
		

		
		try {
			
			System.out.println("R5- Program start");

			
			port= PORT;                     

			// create buffer for data, packet and socket
			bufferPacket = new byte[MTU];
			packetTemp= new DatagramPacket(bufferPacket, bufferPacket.length);
			packet= new DatagramPacket(bufferPacket, bufferPacket.length);
			socket= new DatagramSocket(port);

			while(true){
			
			System.out.println("R5 : Trying to receive");
			socket.receive(packetTemp);

			
			bufferPacket= packetTemp.getData();
			byte[] bufferOri = {};
			bufferOri = Arrays.copyOfRange(bufferPacket, 0,bufferPacket.length);
			byte[] bufferModified1 = {};
			bufferModified1 = Arrays.copyOfRange(bufferPacket, 2,bufferPacket[1]+2);
            bstream = new ByteArrayInputStream(bufferModified1);;
            ostream= new ObjectInputStream(bstream);
			String ID = ostream.readUTF();
			
			System.out.println("R5 : recieved Program end");

           
			System.out.println("R5 : sending topic - Program start");

							
				
			for(int i=0;i<flowTable.length;i++){
				if(flowTable[i][0]!=null&&flowTable[i][0].equals(ID)){
					addressID=flowTable[i][1];
					hasAdd = true;
				}
			}
			if(hasAdd == false){
				System.out.println("Sending to controller");
				// extract destination from arguments
				InetAddress addressToSend= InetAddress.getByName("controller");
				
				// create packet addressed to destination
				packet2= new DatagramPacket(bufferPacket, 
				bufferPacket.length,
				addressToSend, PORT);
		
				// create socket and send packet
				socket2= new DatagramSocket();
				socket2.send(packet2);
				System.out.println("R5 : Trying to receive");

				socket.receive(packet);		
				buffer1= packet.getData();
				byte[] bufferModified2 = {};
				bufferModified2 = Arrays.copyOfRange(buffer1, 2,buffer1[1]+2);
            	bstream1 = new ByteArrayInputStream(bufferModified2);;
           		ostream1= new ObjectInputStream(bstream1);
				addressID = ostream1.readUTF();
				flowTable[index][0]= ID;
				flowTable[index][1]= addressID;
				index++;

            	System.out.println("R5 : recieved Program end");
					

				System.out.println("R5 : topic sent - Program end");

			
			}
			System.out.println("Sending to "+addressID);
				// extract destination from arguments
				InetAddress addressToSend= InetAddress.getByName(addressID);// Integer.parseInt(args[1]);
					
					// create packet addressed to destination
					packet2= new DatagramPacket(bufferOri, 
					bufferOri.length,
					addressToSend, PORT);
					// create socket and send packet
					socket1= new DatagramSocket();
					socket1.send(packet2);
					
			System.out.println("R5 : topic sent - Program end");
		}	
			
	
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	
	}




}