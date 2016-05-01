import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {

	public static void main(String[] args) {

		
		System.setProperty("java.net.preferIPv4Stack" , "true");

		byte[] buffer = new byte[256];



		try {


			//sending
			DatagramSocket socket = new DatagramSocket();
			InetAddress address = InetAddress.getByName("localhost");



			//set screen name
			Scanner key = new Scanner(System.in);
			System.out.print("Enter screen name: ");
			String sName = key.nextLine()+":";
			DatagramPacket sendPack = new DatagramPacket(sName.getBytes(), sName.getBytes().length, address, 8888);
			socket.send(sendPack);

			

			//receiving
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

			while(true){
				System.out.print("Enter message: ");
				String userIn = key.nextLine();
				sendPack = new DatagramPacket(userIn.getBytes(), userIn.getBytes().length, address, 8888);
				socket.send(sendPack);


				socket.receive(packet);
				String printOut = new String(buffer, 0, packet.getLength());
				System.out.println(printOut);
			}



			// socket.leaveGroup(address);
			// socket.close();
		}

		catch(IOException e){
			e.printStackTrace();
		}


	}

}
