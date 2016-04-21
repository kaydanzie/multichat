import java.io.*;
import java.net.*;

public class ChatClient {
	
	public static void main(String[] args) {

		MulticastSocket socket = null;
		DatagramPacket packet = null;

		byte[] buffer = new byte[256];

		try {
			socket = new MulticastSocket(4446);
			InetAddress address = InetAddress.getByName("203.0.113.0");
			socket.joinGroup(address);


			packet = new DatagramPacket(buffer, buffer.length);
			socket.receive(packet);
			String printOut = new String(buffer, 0, packet.getLength());
			System.out.println(packet.getAddress()+ ": " + printOut);

			socket.leaveGroup(address);
			socket.close();
		}

		catch(IOException e){
			e.printStackTrace();
		}


	}

}