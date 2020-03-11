package dht;

import java.io.*;
import java.net.*;
import java.util.*;

public class server0 {
	
	static int TABLE_SIZE = 16;
	
	public static void main(String[] args) throws Exception
	{	
		//Create local variables
		String clientInput;
		String serverResponse;
		int choice;
		String key, value;
		int hashKey;
		
		//Create the hash table and put an initial value in it
		Hashtable<String, String> h = new Hashtable<String, String>();
		h.put("Fast & Furious", "128.17.123.38");
		
		//Listen for incoming connections on port 9000
		ServerSocket serversocket = new ServerSocket(9000);			
		System.out.println("Waiting for incoming connections");

		//Accept the connection on the socket and create input and output streams
		Socket socket = serversocket.accept();
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		//Create socket and input and output streams to next peer in the circle
		Socket socketNextServer = new Socket("localhost",9001);
		PrintWriter outNextServer = new PrintWriter(socketNextServer.getOutputStream(), true);
		BufferedReader inNextServer = new BufferedReader(new InputStreamReader(socketNextServer.getInputStream()));

		//Listen for incoming connections on port 9004 to complete the circle
		ServerSocket serversocketprev = new ServerSocket(9004);
		
		//Accept incoming connections on the socket and create input and output streams
		Socket socketPrevServer = serversocketprev.accept();
		PrintWriter outPrevServer = new PrintWriter(socketPrevServer.getOutputStream(), true);
		BufferedReader inPrevServer = new BufferedReader(new InputStreamReader(socketPrevServer.getInputStream()));

		while(true)
		{
			//Read the menu choice that the client user has selected
			clientInput = in.readLine();
			System.out.println(clientInput);
			choice = Integer.valueOf(clientInput);
			
			switch(choice)
			{
				//Case 1 is for putting a key, value pair in the DHT
				case 1:
					//Read the key
					clientInput = in.readLine();
					key = clientInput;
					
					//Generate the hash key by calling hashFunction
					hashKey = hashFunction(key);
					
					//Read the value
					clientInput = in.readLine();
					value = clientInput;
					
					//If hashKey is 0, the key, value pair will be inserted into the hash table on this peer
					//Else it is sent to the next peer in the circle
					if(hashKey <= 3)
					{
						//Put the key, value pair in the hash table and return it to the client for verification
						h.put(key, value);
						out.println(h);
					}
					else
					{
						//Send necessary values to next peer in circle
						outNextServer.println(choice);
						outNextServer.println(key);
						outNextServer.println(value);
						
						//Read the response from further peers in the circle to pass on to the client
						serverResponse = inNextServer.readLine();
						System.out.println(serverResponse);
						out.println(serverResponse);
					}
					break;
				//Case 2 is for retrieving the IP address value from the DHT for a user entered key
				case 2:
					//Read the key
					clientInput = in.readLine();
					key = clientInput;
					
					//Generate the hash key by calling hashFunction
					hashKey = hashFunction(key);					
					
					if(hashKey <= 3 && h.containsKey(key))
					{
						//Put the key, value pair in the hash table and return it to the client for verification
						value = h.get(key);
						out.println(value);
					}
					else if(hashKey > 3)
					{
						//Send necessary values to next peer in circle
						outNextServer.println(choice);
						outNextServer.println(key);
						
						//Read the response from further peers in the circle to pass on to the client
						serverResponse = inNextServer.readLine();
						System.out.println(serverResponse);
						out.println(serverResponse);
					}
					else
					{
						out.println("Movie is not in DHT. Try again.");
					}
					break;
				// Case 3 if for adding a new peer to the DHT table
				case 3:
					clientInput = in.readLine();
					key = clientInput;
					hashKey = hashFunction(key);
					
					connectNewPeer(Integer.parseInt(key));
					break;
			}
		}
	}
	
	public static int hashFunction(String s)
	{
		//Create variables
		int r = 0;
		char c;
		
		//Run through string and create hash value r
		for(int i = 0;i < s.length();i++)
		{
			c = s.charAt(i);
			r = (int) c + 41*r;
		}
		
		//return the hash value r modulo the DHT TABLE_SIZE
		return Math.abs(r % TABLE_SIZE);
	}
	
	public static void connectNewPeer(int key) throws Exception {
		
		int oldPort = 9000;
		boolean t = true;
		
		while (t) {
			
			if (key > 9000 && key < oldPort && key != oldPort) {
				
				//Listen for incoming connections on port x
				ServerSocket serversocket = new ServerSocket(key);		

				//Accept the connection on the socket and create input and output streams
				Socket socket = serversocket.accept();
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				//Create socket and input and output streams to next peer in the circle
				Socket socketNextServer = new Socket("localhost",key + 1);
				PrintWriter outNextServer = new PrintWriter(socketNextServer.getOutputStream(), true);
				BufferedReader inNextServer = new BufferedReader(new InputStreamReader(socketNextServer.getInputStream()));

				//Listen for incoming connections on port 9004 to complete the circle
				ServerSocket serversocketprev = new ServerSocket(key + 2);
				
				//Accept incoming connections on the socket and create input and output streams
				Socket socketPrevServer = serversocketprev.accept();
				PrintWriter outPrevServer = new PrintWriter(socketPrevServer.getOutputStream(), true);
				BufferedReader inPrevServer = new BufferedReader(new InputStreamReader(socketPrevServer.getInputStream()));
				
				t = false;
				
				} else {
					oldPort++;					
				}
		}
		
	}
	
}
