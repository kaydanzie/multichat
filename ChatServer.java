/*
ChatServer

Sends and receives packets on multicast socket
In the case of a new client or an exiting client it modifies the message and sends it

*/

import java.io.*;
import java.net.*;
import java.util.*;


public class ChatServer{

	

	public static void main(String[] args) {
		
		System.setProperty("java.net.preferIPv4Stack" , "true");

		MulticastSocket socket = null;
		DatagramPacket getPacket= null, sendPacket = null;
		String screenName, joined, exited, received;
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
				received = new String(receiveBuffer, 0, getPacket.getLength());

				//client has just connected
				//send notification to those in chat
				if(received.endsWith(":")){
					screenName = received.replace(':', ' ');
					joined = screenName + "has joined the chat room.";
					
					sendPacket = new DatagramPacket(joined.getBytes(), joined.getBytes().length, address, 8888);
					socket.send(sendPacket);

				}

				//a client has entered the exit keyword as their only message
				//send exit notification to clients
				else if(received.endsWith(": exit")){
					exited = received.replace(": exit", " ");
					exited += "has exited the chat room";
					sendPacket = new DatagramPacket(exited.getBytes(), exited.getBytes().length, address, 8888);
					socket.send(sendPacket);
				}

				//plain message from any client
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



