import java.io.*;
import java.net.*;
import java.util.*;


public class ChatServer{

	

	public static void main(String[] args) {
		
		System.setProperty("java.net.preferIPv4Stack" , "true");

		MulticastSocket socket = null;
		DatagramPacket getPacket= null, sendPacket = null;
		String screenName, joined, exited;
		InetAddress address;

		
		try {
			byte[] receiveBuffer = new byte[256];
			byte[] sendBuffer = new byte[256];

			address = InetAddress.getByName("224.0.0.3");

			getPacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

			socket = new MulticastSocket(8888);
			socket.joinGroup(address);


			while(true) {
				socket.receive(getPacket);
				String received = new String(receiveBuffer, 0, getPacket.getLength());

				//if don't send as is
				if(received.endsWith(":")){
					screenName = received.replace(':', ' ');
					joined = screenName + "has joined the chat room.";
					
					sendPacket = new DatagramPacket(joined.getBytes(), joined.getBytes().length, address, 8888);
					socket.send(sendPacket);

				}

				else if(received.endsWith(": exit")){
					exited = received.replace(': exit', ' ');
					exited += "has exited the chat room";
					sendPacket = new DatagramPacket(exited.getBytes(), exited.getBytes().length, address, 8888);
					socket.send(sendPacket);
				}


				else{
					System.out.println(received);
				}
				getPacket.setLength(receiveBuffer.length);
			}
		}

		catch(IOException e){
			e.printStackTrace();
		}

	}
}



