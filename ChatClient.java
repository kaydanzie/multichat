import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {



	private byte[] buffer = new byte[256];

	private DatagramPacket sendPack, packet;

	private MulticastSocket socket;

	private InetAddress address;


	public void firstJoin(){

		try {

			address = InetAddress.getByName("224.0.0.3");
			socket = new MulticastSocket(8888);
			socket.joinGroup(address);


			//for continuously receiving packets
			ClientRunnable runnable = new ClientRunnable(address);

			startChat();
		}

		catch(IOException e){
			e.printStackTrace();
		}

	}


	public void startChat(){

		try{
			//set screen name
			Scanner key = new Scanner(System.in);
			System.out.print("Enter screen name: ");
			String sName = key.nextLine()+":";
			sendPack = new DatagramPacket(sName.getBytes(), sName.getBytes().length, address, 8888);
			socket.send(sendPack);

			while(true){
				System.out.print("Enter message: ");
				String userIn = key.nextLine();
				sendPack = new DatagramPacket(userIn.getBytes(), userIn.getBytes().length, address, 8888);
				socket.send(sendPack);
			}
		}

		catch(IOException e){
			e.printStackTrace();
		}

	}



	public void run() {

		
		System.setProperty("java.net.preferIPv4Stack" , "true");

		firstJoin();


		/*try {

			//sending
			InetAddress address = InetAddress.getByName("224.0.0.3");
			socket = new MulticastSocket(8888);
			socket.joinGroup(address);

			//set screen name
			Scanner key = new Scanner(System.in);
			System.out.print("Enter screen name: ");
			String sName = key.nextLine()+":";
			sendPack = new DatagramPacket(sName.getBytes(), sName.getBytes().length, address, 8888);
			socket.send(sendPack);


			//for continuously receiving packets
			ClientRunnable runnable = new ClientRunnable(address);

			while(true){

				do {
					System.out.print("Enter message: ");
					String userIn = key.nextLine();
					sendPack = new DatagramPacket(userIn.getBytes(), userIn.getBytes().length, address, 8888);
					socket.send(sendPack);
				} while(runnable.myThread.isAlive() && runnable.myThread.getState() != Thread.State.TERMINATED);

				System.exit(1);

			 }
		}

		catch(IOException e){
			e.printStackTrace();
		}*/


	}

}
