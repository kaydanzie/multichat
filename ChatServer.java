import java.io.*;
import java.net.*;
import java.util.ArrayList;


public class ChatServer{

	public static void main(String[] args) {
		MulticastSocket socket = null;
		DatagramPacket packet= null;

		System.setProperty("java.net.preferIPv4Stack" , "true");


		ArrayList<InetAddress> addresses = new ArrayList<>();
		ArrayList<String> names = new ArrayList<>();

		try {
			byte[] buffer = new byte[256];

			packet = new DatagramPacket(buffer, buffer.length);



			socket = new MulticastSocket(8888);
			socket.joinGroup(InetAddress.getByName("224.0.0.3"));


			while(true) {
				socket.receive(packet); // blocks until a datagram is received
				System.out.println("Received " + packet.getLength() + " bytes from " + packet.getAddress());
				String printOut = new String(buffer, 0, packet.getLength());
				System.out.println(printOut);
				packet.setLength(buffer.length);
			}

			//want to get address of packet sent by client
			//addresses.add(packet.getAddress());

			//socket.send(packet);
			//System.out.println(addresses.get(0) + ": "+ out);


		}

		catch(IOException e){
			e.printStackTrace();
		}

	}
}
