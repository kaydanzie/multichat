import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {

	public static void main(String[] args) {

		
		System.setProperty("java.net.preferIPv4Stack" , "true");

		byte[] buffer = new byte[256];

		DatagramPacket sendPack, packet;

		try {


			//sending
			DatagramSocket socket = new DatagramSocket();
			InetAddress address = InetAddress.getByName("localhost");


			//set screen name
			Scanner key = new Scanner(System.in);
			System.out.print("Enter screen name: ");
			String sName = key.nextLine()+":";
			sendPack = new DatagramPacket(sName.getBytes(), sName.getBytes().length, address, 8888);
			socket.send(sendPack);

			int port = sendPack.getPort();

			ClientRunnable runnable = new ClientRunnable(socket);

			while(true){
				
				while(runnable.myThread.isAlive() && runnable.myThread.getState() != Thread.State.TERMINATED){
					System.out.print("Enter message: ");
					String userIn = key.nextLine();
					sendPack = new DatagramPacket(userIn.getBytes(), userIn.getBytes().length, address, 8888);
					socket.send(sendPack);
				}

				System.exit(1);

			 }

		}

		catch(IOException e){
			e.printStackTrace();
		}


	}

}
