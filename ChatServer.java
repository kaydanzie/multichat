import java.io.*;
import java.net.*;
import java.util.*;


public class ChatServer{

	public static void main(String[] args) {


		MulticastSocket socket = null;
		DatagramPacket getPacket= null, sendPacket = null;
		String newMessage, screenName, joined, exited;
		System.setProperty("java.net.preferIPv4Stack" , "true");

		
		// port number, screen name
		Map<Integer, String> names = new HashMap<Integer, String>();
		ArrayList<Integer> ports = new ArrayList<>();


		try {
			byte[] buffer = new byte[256];

			getPacket = new DatagramPacket(buffer, buffer.length);

			socket = new MulticastSocket(8888);
			socket.joinGroup(InetAddress.getByName("224.0.0.3"));


			while(true) {
				socket.receive(getPacket);
				String printOut = new String(buffer, 0, getPacket.getLength());

				//if first message, add to hashmap and arraylist, don't send
				if(printOut.endsWith(":")){
					names.put(getPacket.getPort(), printOut);
					screenName = printOut.replace(':', ' ');
					ports.add(getPacket.getPort());
					joined = screenName + "has joined the chat room.";
					System.out.println(joined);

					//alert users of new client
					for(int i=0; i<ports.size(); ++i){
						if(ports.get(i) != getPacket.getPort()){
							sendPacket = new DatagramPacket(joined.getBytes(), joined.getBytes().length, getPacket.getAddress(), ports.get(i));
							socket.send(sendPacket);
						}
					}
				}

				else if(printOut.equalsIgnoreCase("exit")){
					exited = names.get(getPacket.getPort());
					exited = exited.replace(':', ' ');
					exited += "has left the chat room";
					for(int i=0; i<ports.size(); ++i){
						if(ports.get(i) != getPacket.getPort()){
							sendPacket = new DatagramPacket(exited.getBytes(), exited.getBytes().length, getPacket.getAddress(), ports.get(i));
							socket.send(sendPacket);
						}
					}
					sendPacket = new DatagramPacket("exit".getBytes(), "exit".getBytes().length, getPacket.getAddress(), getPacket.getPort());
					socket.send(sendPacket);
				}

				else{
					System.out.println(names.get(getPacket.getPort())+ " " + printOut);
					newMessage = names.get(getPacket.getPort())+ " " + printOut;

					//for ports/addresses not on this packet, send packet
					for(int i=0; i<ports.size(); ++i){
						if(ports.get(i) != getPacket.getPort()){
							sendPacket = new DatagramPacket(newMessage.getBytes(), newMessage.getBytes().length, getPacket.getAddress(), ports.get(i));
							socket.send(sendPacket);
						}
					}
				}
				getPacket.setLength(buffer.length);
			}
		}

		catch(IOException e){
			e.printStackTrace();
		}

	}
}
