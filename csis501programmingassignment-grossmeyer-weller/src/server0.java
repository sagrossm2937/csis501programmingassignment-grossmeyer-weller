import java.io.*;
import java.net.*;
import java.util.*;

public class server0 {
	
	static int TABLE_SIZE = 4;
	
	public static void main(String[] args) throws Exception{
		String clientInput;
		String serverResponse;
		int choice;
		String key, value;
		int hashKey;
		
		Hashtable<String, String> h = new Hashtable<String, String>();
		h.put("Led", "128.17.123.38");

		ServerSocket serversocket = new ServerSocket(9000);			
		System.out.println("Waiting for incoming connections");

		Socket socket = serversocket.accept();
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		Socket socketNextServer = new Socket("localhost",9001);
		PrintWriter outNextServer = new PrintWriter(socketNextServer.getOutputStream(), true);
		BufferedReader inNextServer = new BufferedReader(new InputStreamReader(socketNextServer.getInputStream()));

		
		ServerSocket serversocketprev = new ServerSocket(9004);
		
		Socket socketPrevServer = serversocketprev.accept();
		PrintWriter outPrevServer = new PrintWriter(socketPrevServer.getOutputStream(), true);
		BufferedReader inPrevServer = new BufferedReader(new InputStreamReader(socketPrevServer.getInputStream()));

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
					hashKey = hashFunction(key);
					clientInput = in.readLine();
					value = clientInput;
					System.out.println(hashKey);
					
					if(hashKey == 0)
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
	
	public static int hashFunction(String s)
	{
		int r = 0;
		char c;
		
		for(int i = 0;i < s.length();i++)
		{
			c = s.charAt(i);
			r = (int) c + 41*r;
		}
		
		return Math.abs(r % TABLE_SIZE);
	}

}
