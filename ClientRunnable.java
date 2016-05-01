import java.io.*;
import java.net.*;

public class ClientRunnable implements Runnable {


	ClientRunnable() {
		Thread myThread = new Thread(this, "thread object");
		myThread.start();
	}

	public void run(){

		try {

			byte[] buffer = new byte[256];
			DatagramSocket socket = new DatagramSocket();

			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

			while(true){
				socket.receive(packet);
				String printOut = new String(buffer, 0, packet.getLength());
				System.out.println(printOut);
			}
		}

		catch(IOException e){
			System.out.println("exception");
		}
		

	}
	
}