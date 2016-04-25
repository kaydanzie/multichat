import java.io.*;
import java.net.*;
import java.util.ArrayList;


public class ChatServer{

	public static void main(String[] args) {


		MulticastSocket socket = null;
		DatagramPacket packet= null;
		System.setProperty("java.net.preferIPv4Stack" , "true");
		String receiveName = null;



		try {
			byte[] buffer = new byte[256];

			packet = new DatagramPacket(buffer, buffer.length);



			socket = new MulticastSocket(8888);
			socket.joinGroup(InetAddress.getByName("224.0.0.3"));


			while(true) {
				socket.receive(packet); // blocks until a datagram is received
				String printOut = new String(buffer, 0, packet.getLength());

				if(printOut.contains(":")){
					receiveName = printOut;
				}
				else{
					System.out.println(receiveName+ " " + printOut);
				}
				packet.setLength(buffer.length);
			}


		}

		catch(IOException e){
			e.printStackTrace();
		}

	}
}
