import java.io.*;
import java.net.*;
import java.util.*;

public class server2 {
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
		h.put("Inception", "128.17.123.40");

		//Listen for incoming connections from previous peer in circle
		ServerSocket serversocket = new ServerSocket(9002);			
		System.out.println("Waiting for incoming connections");

		//Accept the connection on the socket and create input and output streams
		Socket socket = serversocket.accept();
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		//Create socket and input and output streams to next peer in the circle
		Socket socketNextServer = new Socket("localhost",9003);
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
					
					//Read the hashKey
					clientInput = in.readLine();
					hashKey = Integer.valueOf(clientInput);
					
					//Read the value
					clientInput = in.readLine();
					value = clientInput;
					
					//If hashKey is less than 1, the key, value pair will be inserted into the hash table on this peer
					//Else it is sent to the next peer in the circle
					if(hashKey <= 11 && hashKey >= 8)
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
						outNextServer.println(hashKey);
						outNextServer.println(value);
						
						//Read the response from further peers in the circle to pass on to the client
						serverResponse = inNextServer.readLine();
						System.out.println(serverResponse);
						out.println(serverResponse);
					}
					break;
				//Case 2 is for retrieving the IP address value from the DHT for a user entered key
				case 2:
					clientInput = in.readLine();
					System.out.println(clientInput);
					key = clientInput;
					value = h.get(key);
					out.println(value);
			}
		}
	}

}
