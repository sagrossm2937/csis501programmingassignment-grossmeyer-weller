import java.io.*;
import java.net.*;
import java.util.*;

import org.apache.commons.net.telnet.TelnetClient;

public class server1 {
	
	static int TABLE_SIZE = 16;
	
	public static void main(String[] args) throws Exception
	{
		//Create local variables
		String clientInput;
		String serverResponse;
		int choice;
		String key, value;
		int hashKey;
		int port = 9001;
		int nextPort = 9002;
		
		//Create the hash table and put an initial value in it
		Hashtable<String, String> h = new Hashtable<String, String>();
		h.put("Harry Potter", "128.17.123.39");

		//Listen for incoming connections from previous peer in circle
		ServerSocket serversocket = new ServerSocket(port);			
		System.out.println("Waiting for incoming connections");

		//Accept the connection on the socket and create input and output streams
		Socket socket = serversocket.accept();
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		//Create socket and input and output streams to next peer in the circle
		Socket socketNextServer = new Socket("localhost",nextPort);
		PrintWriter outNextServer = new PrintWriter(socketNextServer.getOutputStream(), true);
		BufferedReader inNextServer = new BufferedReader(new InputStreamReader(socketNextServer.getInputStream()));
		
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
					
					//If hashKey is less than 1, the key, value pair will be inserted into the hash table on this peer
					//Else it is sent to the next peer in the circle
					if(hashKey <= 7)
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
					
					if(hashKey <= 7 && h.containsKey(key))
					{
						//Put the key, value pair in the hash table and return it to the client for verification
						value = h.get(key);
						out.println(value);
					}
					else if(hashKey > 7)
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
			}
		}
	}
	
	public static boolean testConnectionPrev(int p)
	{
		TelnetClient client = new TelnetClient();
		client.setConnectTimeout(5000);
		
		 try
		 {
			 client.connect("localhost", p);
		 }
		 catch (SocketException socketException)
		 {		 
			return false;
		 }
		 catch (IOException ioException)
		 { 
		    return false;
		 }
		 
		 return true;
	}
	
	public static boolean testConnectionNext(int p)
	{	
		TelnetClient client = new TelnetClient();
		client.setConnectTimeout(5000);
		
		 try
		 {
			 client.connect("localhost", p);
		 }
		 catch (SocketException socketException)
		 {		 
			return false;
		 }
		 catch (IOException ioException)
		 { 
		    return false;
		 }
		 
		 return true;
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
}
