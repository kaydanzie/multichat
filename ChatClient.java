import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {

	public static void main(String[] args) {

		MulticastSocket socket = null;
		System.setProperty("java.net.preferIPv4Stack" , "true");

		byte[] buffer = new byte[256];



		try {


			//sending
			socket = new MulticastSocket(8888);
			InetAddress address = InetAddress.getByName("224.0.0.3");
			socket.joinGroup(address);


			//set screen name
			Scanner key = new Scanner(System.in);
			System.out.print("Enter screen name: ");
			String sName = key.nextLine()+":";
			DatagramPacket sendPack = new DatagramPacket(sName.getBytes(), sName.getBytes().length, address, 8888);
			socket.send(sendPack);


			//need to make repeat
			//need to resend sName before every message?
			System.out.print("Enter message: ");
			String userIn = key.nextLine();
			sendPack = new DatagramPacket(userIn.getBytes(), userIn.getBytes().length, address, 8888);
			socket.send(sendPack);



			

			//receiving
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

			while(true){
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
