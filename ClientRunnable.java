import java.io.*;
import java.net.*;

public class ClientRunnable implements Runnable {

	DatagramPacket packet;
	Thread myThread;
	DatagramSocket socket;

	ClientRunnable(DatagramSocket socket) {
		this.socket = socket;
		myThread = new Thread(this, "thread object");
		myThread.start();
	}

	public void run(){

		try {

			byte[] buffer = new byte[256];

			while(true){

				packet = new DatagramPacket(buffer, buffer.length);
				socket.receive(packet);
				System.out.println();
				String printOut = new String(buffer, 0, packet.getLength());
				System.out.println(printOut);
			}
		}

		catch(IOException e){
			e.printStackTrace();
		}
		

	}
	
}