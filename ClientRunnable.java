/*
ClientRunnable

Starts thread that continuously receives messages on the same socket address as the Client that called the runnable
In cases of a new client and an exiting client, the straight message from the user is not printed, instead it prints a modified message from the server
Continues running until the Client program is terminated with an "exit" message by the user

*/
import java.io.*;
import java.net.*;

public class ClientRunnable implements Runnable {

	DatagramPacket packet;
	Thread myThread;
	MulticastSocket socket;
	InetAddress address;

	ClientRunnable(InetAddress address) {
		this.address = address;
		myThread = new Thread(this, "");
		myThread.start();
	}


	public void run(){


		byte[] buffer = new byte[256];

		try {

			socket = new MulticastSocket(8888);
			socket.joinGroup(address);

			while(true){

				packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				String printOut = new String(buffer, 0, packet.getLength());


				//":" and ": exit" are the cases of a client joining and exiting respectively
				//a modified message is sent by the server to the clients so the original user message shouldn't show
				if(printOut.endsWith(":") || printOut.endsWith(": exit")){
					//don't print blank messages or messages sent with enter key
				}

				else{
					System.out.println();
					System.out.println(printOut);
					System.out.print("Enter message: ");
				}

			}
		}
		catch(IOException e){
			e.printStackTrace();
		}

	}
	
}