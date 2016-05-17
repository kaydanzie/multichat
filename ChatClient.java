import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {



	private byte[] buffer = new byte[256];

	private DatagramPacket sendPack, packet;

	private MulticastSocket socket;

	private InetAddress address;



	// public void startChat(){

		

	// }



	public void run() {

		
		System.setProperty("java.net.preferIPv4Stack" , "true");

		try{

			address = InetAddress.getByName("224.0.0.3");
			socket = new MulticastSocket(8888);
			socket.joinGroup(address);

			//for continuously receiving packets
			ClientRunnable runnable = new ClientRunnable(address);

			//set screen name
			Scanner key = new Scanner(System.in);
			System.out.print("Enter screen name: ");
			String sName = key.nextLine()+":";
			sendPack = new DatagramPacket(sName.getBytes(), sName.getBytes().length, address, 8888);
			socket.send(sendPack);

			while(true){
				System.out.print("Enter message: ");
				String userIn = key.nextLine();
				userIn = sName + " "+ userIn;
				sendPack = new DatagramPacket(userIn.getBytes(), userIn.getBytes().length, address, 8888);
				socket.send(sendPack);
			}
		}

		catch(IOException e){
			e.printStackTrace();
		}


		//while(runnable.myThread.isAlive() && runnable.myThread.getState() != Thread.State.TERMINATED);

	}

}
