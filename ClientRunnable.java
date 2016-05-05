import java.io.*;
import java.net.*;

public class ClientRunnable implements Runnable {

	DatagramPacket packet;
	Thread myThread;
	boolean stopped;
	MulticastSocket socket;
	InetAddress address;

	ClientRunnable(InetAddress address) {
		this.address = address;
		myThread = new Thread(this, "");
		myThread.start();
	}

	public void stop(){
		stopped = false;
	}

	public void run(){

		

		byte[] buffer = new byte[256];

		while(!stopped){

			try {

				socket = new MulticastSocket(8888);
				socket.joinGroup(address);

				while(true){

					packet = new DatagramPacket(buffer, buffer.length);
					socket.receive(packet);
					System.out.println();
					String printOut = new String(buffer, 0, packet.getLength());

					if(printOut.startsWith("exit")){
						stopped = true;
						break;
					}

					else{
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
	
}