import java.io.*;
import java.net.*;


public class ChatServer{

	public static void main(String[] args) {
		DatagramSocket socket = null;
		DatagramPacket packet= null;

		try {
			socket = new DatagramSocket();

			//while(true){
				String out = "my first message";

				InetAddress address = InetAddress.getByName("224.0.0.3");
				packet = new DatagramPacket(out.getBytes(), out.getBytes().length, address, 8888);

				socket.send(packet);
				System.out.println("Server is sending: "+ out);

			//}
		}

		catch(IOException e){
			e.printStackTrace();
		}

	}
}
