/*
ChatClient

Sends packets on multicast socket
Also opens thread for receiving packets
Continually asks user for new message while printing messages received from socket
Infinite while loop terminates with the user's "exit" message

*/

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {


	private byte[] buffer = new byte[256];
	private DatagramPacket sendPack;
	private MulticastSocket socket;
	private InetAddress address;
	private String userIn;

	public void run(){

		
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

			//keep getting user input and sending in multicast socket
			while(true){

				userIn = key.nextLine();
				
				if(userIn.equals("exit")){
					userIn = sName + " "+ userIn;
					sendPack = new DatagramPacket(userIn.getBytes(), userIn.getBytes().length, address, 8888);
					socket.send(sendPack);
					System.exit(0);
				}
				else{
					userIn = sName + " "+ userIn;
					sendPack = new DatagramPacket(userIn.getBytes(), userIn.getBytes().length, address, 8888);
					socket.send(sendPack);
					
				}
				
			}
		}

		catch(IOException e){
			e.printStackTrace();
		}

	}

}
