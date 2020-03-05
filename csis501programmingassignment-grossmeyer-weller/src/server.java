import java.io.*;
import java.net.*;
import java.util.*;

public class server {
	public static void main(String[] args) throws Exception{
		String clientInput;
		int choice;
		String key, value;
		
		Hashtable<String, String> h = new Hashtable<String, String>();
		
		h.put("Led", "128.17.123.38");
		
		ServerSocket serversocket = new ServerSocket(9000);
			
		System.out.println("Waiting for incoming connections");
		Socket socket = serversocket.accept();
			
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);  /*  socket.getOutputStream() returns an output stream for this socket. 
																				PrintWriter(socket.getOutputStream(), true) creates a new PrintWriter from an existing OutputStream. 
																				This convenience constructor creates the necessary intermediateOutputStreamWriter, 
																				which will convert characters into bytes using the default character encoding.*/

		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));/* socket.getInputStream() returns an input stream for this socket. 
																								  new InputStreamReader(socket.getInputStream()) creates an InputStreamReader that uses the default charset.
																								  new BufferedReader(new InputStreamReader(socket.getInputStream())) creates a buffering character-input stream that uses a default-sized input buffer.*/
		clientInput = in.readLine();
		System.out.println(clientInput);
		choice = Integer.valueOf(clientInput);
			
		switch(choice)
		{
			case 1:
				clientInput = in.readLine();
				key = clientInput;
				clientInput = in.readLine();
				value = clientInput;
				h.put(key, value);
				out.println(h);
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
