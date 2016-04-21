import java.io.*;
import java.net.*;
import java.util.ArrayList;


public class ChatServer{

	public static void main(String[] args) {
		DatagramSocket socket = null;
		DatagramPacket packet= null;


		ArrayList<InetAddress> addresses = new ArrayList<>();
		ArrayList<String> names = new ArrayList<>();

		try {
			socket = new DatagramSocket();


			String out = "my first message";

			InetAddress address = InetAddress.getByName("224.0.0.3");
			packet = new DatagramPacket(out.getBytes(), out.getBytes().length, address, 8888);

			//get address of packet sent by client
			addresses.add(packet.getAddress());

			socket.send(packet);
			System.out.println(addresses.get(0) + ": "+ out);


		}

		catch(IOException e){
			e.printStackTrace();
		}

	}
}
