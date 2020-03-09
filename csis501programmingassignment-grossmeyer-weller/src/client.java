import java.io.*;
import java.net.*;
import java.util.*;

public class client{
	public static void main(String[] args) throws Exception
	{
		Scanner input = new Scanner(System.in);
		String serverResponse;
		int choice;
		String key, value;
		int hashKey;
		
		Socket socket = new Socket("localhost",9000);
		
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		while(true)
		{
			System.out.println("Welcome to the DHT! Here are your choices:");
			System.out.println("1-Insert a pair into DHT");
			System.out.println("2-Get the IP address of content");
			System.out.println("3-Exit");
			System.out.println("Enter your choice:");
			
			choice = input.nextInt();
		
			switch(choice)
			{
				case 1:
					System.out.println("Enter the content name:");
					key = input.next();
					System.out.println("Enter the IP address it is to be stored at:");
					value = input.next();
					out.println(choice);
					out.println(key);
					out.println(value);
					serverResponse = in.readLine();
					System.out.println("Pair successfully inserted into DHT!");
					System.out.println("Here is the new table: " + serverResponse + "\n");
					break;
				case 2:
					System.out.println("Enter the content name:");
					key = input.next();
					out.println(choice);
					out.println(key);
					value = in.readLine();
					System.out.println("The IP address that the content is stored at is: " + value + "\n");
					break;
				case 3:
					System.exit(0);
				default:
					System.out.println("Wrong Entry\n");
			}
		}	
	}
}
