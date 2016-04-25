import java.io.*;
import java.net.*;

public class ChatClient {

	public static void main(String[] args) {

		MulticastSocket socket = null;
		DatagramPacket packet = null;
		DatagramPacket sendPack = null;
		System.setProperty("java.net.preferIPv4Stack" , "true");

		byte[] buffer = new byte[256];

		try {
			socket = new MulticastSocket(8888);
			InetAddress address = InetAddress.getByName("224.0.0.3");
			socket.joinGroup(address);

			String test = "blah";//change to keyboard input
			sendPack = new DatagramPacket(test.getBytes(), test.getBytes().length, address, 8888);
			socket.send(sendPack);


			


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
