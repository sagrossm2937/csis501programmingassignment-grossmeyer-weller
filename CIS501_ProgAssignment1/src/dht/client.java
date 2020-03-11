package dht;

import java.io.*;
import java.net.*;
import java.util.*;

public class client{
	public static void main(String[] args) throws Exception
	{
		//Create local variables
		String serverResponse;
		int choice;
		String key, value;
		int hashKey;
		
		//Create the input scanner for user input
		Scanner input = new Scanner(System.in);
		
		//Create the socket to connect to the peer and input and output streams
		Socket socket = new Socket("localhost",9000);	
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		while(true)
		{
			//Print the menu to the screen
			System.out.println("Welcome to the Movie DHT! Here are your choices:");
			System.out.println("1-Insert a pair into DHT");
			System.out.println("2-Get the IP address of Movie");
			System.out.println("3-Enter the socket to place new peer");
			System.out.println("4-Exit");
			System.out.println("Enter your choice:");
			
			//Read the users choice
			choice = input.nextInt();
			input.nextLine();
		
			switch(choice)
			{
				//Case 1 is for inserting a key, value pair into the DHT
				case 1:
					//Enter and read the content to be pushed, this will be the key
					System.out.println("Enter the Movie name:");
					key = input.nextLine();
					
					//Enter and read the IP address the content is stored at, this will be the value
					System.out.println("Enter the IP address where the Movie is stored:");
					value = input.next();
					
					//Send menu choice, key, and value to the peer
					out.println(choice);
					out.println(key);
					out.println(value);
					
					//Read the response from the peer
					serverResponse = in.readLine();
					
					//Verification that the key, value pair has successfully been put in the DHT
					System.out.println("Pair successfully inserted into DHT!");
					System.out.println("Here is the new table: " + serverResponse + "\n");
					break;
				//Case 2 is for retrieving the IP address value from the DHT for a user entered key
				case 2:
					//Enter and read the key to retrieve the value for
					System.out.println("Enter the Movie name:");
					key = input.nextLine();
					
					//Send the menu choice and the key to the peer
					out.println(choice);
					out.println(key);
					
					//Read the value sent from the peer and print it to the screen for the user
					value = in.readLine();
					System.out.println("The IP address where the Movie is stored is: " + value + "\n");
					break;
				case 3:
					//Enter new peer to join DHT
					System.out.println("Enter socket number greater than 9000 and less than 10000");
					key = input.nextLine();
					
					out.println(choice);
					out.println(key);
					
					System.out.println("Peer successfully inserted into DHT!");
					//Send the menu choice and key to the peer
					
				//Case 4 is for ending the program
				case 4:
					System.exit(0);
				//The default handles if the user entered the wrong menu choice and sends the user back
				default:
					System.out.println("Wrong Entry. Try again.\n");
			}
		}	
	}
}
