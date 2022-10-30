
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;



public class Controller{


	public static void main(String[] args) {
		final int RECV_PORT = 51510;
        
		
		
		final int MTU = 1500;

		DatagramPacket packet;
		DatagramSocket socket;

        ObjectInputStream ostream;
		ByteArrayInputStream bstream;
        ObjectOutputStream ostream1;
		ByteArrayOutputStream bstream1;
		ObjectOutputStream ostream2;
		ByteArrayOutputStream bstream2;

		DatagramPacket packet1;
		DatagramSocket socket1;
		InetAddress address;
		int port;

        byte[] buffer;
        byte[] buffer1;
        String flowTableE1[][] = {{"r1","r1"},{"r2","r1"},{"r3","r1"},{"r4","r1"},{"r5","r1"},{"r6","r1"},{"e1","e1"},{"e4","r1"}};
        String flowTableE2[][] = {{"r1","r6"},{"r2","r6"},{"r3","r6"},{"r4","r6"},{"r5","r6"},{"r6","r6"},{"e1","r6"},{"e4","e4"}};
        String flowTable1[][] = {{"r2","r2"},{"r3","r3"},{"r4","r3"},{"r5","r3"},{"r6","r3"},{"e1","e1"},{"e4","r3"}};
		String flowTable2[][] = {{"r1","r1"},{"r3","r3"},{"r4","r3"},{"r5,r3"},{"r6","r3"},{"e1","r1"},{"e4","r3"}};
        String flowTable3[][] = {{"r1","r1"},{"r2","r2"},{"r4","r4"},{"r5","r5"},{"r6","r6"},{"e1","r1"},{"e4","r6"}};
		String flowTable4[][] = {{"r1","r3"},{"r2","r3"},{"r3","r3"},{"r5","r5"},{"r6","r6"},{"e1","r3"},{"e4","r6"}};
        String flowTable5[][] = {{"r1","r3"},{"r2","r3"},{"r3","r3"},{"r4","r4"},{"r6","r6"},{"e1","r3"},{"e4","r6"}};
        String flowTable6[][] = {{"r1","r3"},{"r2","r3"},{"r3","r3"},{"r4","r4"},{"r5","r5"},{"e1","r3"},{"e4","e4"}};
		try {
			
			System.out.println("receiving - Program start");

			
			port= RECV_PORT;                     

			// create buffer for data, packet and socket
			buffer= new byte[MTU];
			packet= new DatagramPacket(buffer, buffer.length);
			socket= new DatagramSocket(port);

			
			while(true){
			
			System.out.println("Controller : Trying to receive");
			socket.receive(packet);

			
			buffer= packet.getData();
            address = packet.getAddress();
			byte[] bufferModified1 = {};
			bufferModified1 = Arrays.copyOfRange(buffer, 2,buffer[1]+2);
            bstream = new ByteArrayInputStream(bufferModified1);;
            ostream= new ObjectInputStream(bstream);
			String ID = ostream.readUTF();
			
			System.out.println("Controller : recieved");
            String addressID = "";
           
            String addr = address.getHostName();
            
            String srcID =addr.substring(0, 2);
         
            if(srcID.equals("r1")){
                for(int i=0;i<flowTable1.length;i++){
                    if(flowTable1[i][0].equals(ID)){
                        addressID=flowTable1[i][1];
                    }
                }
            }
			else if(srcID.equals("r2")){
                for(int i=0;i<flowTable2.length;i++){
                    if(flowTable2[i][0].equals(ID)){
                        addressID=flowTable2[i][1];
                    }
                }

            }
            else if(srcID.equals("r3")){
                for(int i=0;i<flowTable3.length;i++){
                    if(flowTable3[i][0].equals(ID)){
                        addressID=flowTable3[i][1];
                    }
                }

            }
			else if(srcID.equals("r4")){
                for(int i=0;i<flowTable4.length;i++){
                    if(flowTable4[i][0].equals(ID)){
                        addressID=flowTable4[i][1];
                    }
                }

            }
			else if(srcID.equals("r5")){
                for(int i=0;i<flowTable5.length;i++){
                    if(flowTable5[i][0].equals(ID)){
                        addressID=flowTable5[i][1];
                    }
                }

            }
			else if(srcID.equals("r6")){
                for(int i=0;i<flowTable6.length;i++){
                    if(flowTable6[i][0].equals(ID)){
                        addressID=flowTable6[i][1];
                    }
                }

            }
            else if(srcID.equals("e1")){
                for(int i=0;i<flowTableE1.length;i++){
                    if(flowTableE1[i][0].equals(ID)){
                        addressID=flowTableE1[i][1];
                    }
                }

            }
            else if(srcID.equals("e4")){
                for(int i=0;i<flowTableE2.length;i++){
                    if(flowTableE2[i][0].equals(srcID)){
                        addressID=flowTableE2[i][1];
                    }
                }

            }
            
			bstream1= new ByteArrayOutputStream();
			ostream1= new ObjectOutputStream(bstream1);
			ostream1.writeUTF("Here is the address");
			ostream1.flush();
			buffer1 = bstream1.toByteArray();

			bstream2= new ByteArrayOutputStream();
			ostream2= new ObjectOutputStream(bstream2);
			ostream2.writeUTF(addressID);
			ostream2.flush();
			buffer= bstream2.toByteArray();

			byte[] buffer2 = new byte[1500];
			byte[] TL =  {0,(byte)buffer.length};
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
			packet1= new DatagramPacket(buffer2, buffer2.length,
					address, port);

			// create socket and send packet
			socket1= new DatagramSocket();
			socket1.send(packet1);
			


			System.out.println("Controller : topic sent - Program end");
            }
		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	
	}




}