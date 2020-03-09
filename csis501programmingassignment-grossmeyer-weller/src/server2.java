import java.io.*;
import java.net.*;
import java.util.*;

public class server2 {
	public static void main(String[] args) throws Exception{
		String clientInput;
		String serverResponse;
		int choice;
		String key, value;
		int hashKey;
		
		Hashtable<String, String> h = new Hashtable<String, String>();	
		h.put("Goodbye", "128.17.123.40");

		ServerSocket serversocket = new ServerSocket(9002);			
		System.out.println("Waiting for incoming connections");

		Socket socket = serversocket.accept();
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		Socket socketNextServer = new Socket("localhost",9003);
		PrintWriter outNextServer = new PrintWriter(socketNextServer.getOutputStream(), true);
		BufferedReader inNextServer = new BufferedReader(new InputStreamReader(socketNextServer.getInputStream()));

		while(true)
		{
			clientInput = in.readLine();
			System.out.println(clientInput);
			choice = Integer.valueOf(clientInput);
				
			switch(choice)
			{
				case 1:
					clientInput = in.readLine();
					key = clientInput;
					clientInput = in.readLine();
					hashKey = Integer.valueOf(clientInput);
					clientInput = in.readLine();
					value = clientInput;
					System.out.println(hashKey);
					
					if(hashKey <= 2)
					{
						h.put(key, value);
						out.println(h);
					}
					else
					{
						outNextServer.println(choice);
						outNextServer.println(key);
						outNextServer.println(hashKey);
						outNextServer.println(value);
						serverResponse = inNextServer.readLine();
						System.out.println(serverResponse);
						out.println(serverResponse);
					}
					break;
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
