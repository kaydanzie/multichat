import java.io.*;
import java.net.*;


public ChatServer{
	
	public static void main(String[] args) {
		DatagramSocket socket = null;
		DatagramPacket packet= null;
		byte[] buffer = null;

		try {
			socket = new DatagramSocket();

			while(true){
				String out = "my first message";
				buffer = out.getBytes();

				InetAddress address = InetAddress.getByName("localhost");
				packet = new DatagramPacket(buffer, buffer.length, address, 6789);

				socket.send(packet);
				System.out.println("Server is sending: "+ out);
				
			}
		}

	}
}
