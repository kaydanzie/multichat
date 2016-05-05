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

		// port number, screen name
		Map<Integer, String> names = new HashMap<Integer, String>();

		
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

				//if first message, add to hashmap and arraylist, don't send
				if(received.endsWith(":")){
					names.put(getPacket.getPort(), received);
					screenName = received.replace(':', ' ');
					joined = screenName + "has joined the chat room.";
					
					sendPacket = new DatagramPacket(joined.getBytes(), joined.getBytes().length, address, 8888);
					socket.send(sendPacket);

				}

				else if(received.equalsIgnoreCase("exit")){
					exited = names.get(getPacket.getPort());
					exited = exited.replace(':', ' ');
					sendPacket = new DatagramPacket(exited.getBytes(), exited.getBytes().length);
					socket.send(sendPacket);
					sendPacket = new DatagramPacket("exit".getBytes(), "exit".getBytes().length);
					socket.send(sendPacket);
				}

				else if(received.contains("has joined the chat room")){
					System.out.println(received);
				}

				else{
					System.out.println(names.get(getPacket.getPort())+ " " + received);
				}
				getPacket.setLength(receiveBuffer.length);
			}
		}

		catch(IOException e){
			e.printStackTrace();
		}

	}
}



