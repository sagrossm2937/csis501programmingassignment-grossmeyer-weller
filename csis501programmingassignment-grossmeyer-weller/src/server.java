import java.io.*;
import java.net.*;

public class server {
	public static void main(String[] args) throws Exception{
		String clientInput;
		double clientRadius, circleArea;
		
		
			ServerSocket serversocket = new ServerSocket(9000);
			
			System.out.println("Waiting for incoming connections");
			Socket socket = serversocket.accept();
			
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);  /*  socket.getOutputStream() returns an output stream for this socket. 
																					PrintWriter(socket.getOutputStream(), true) creates a new PrintWriter from an existing OutputStream. 
																					This convenience constructor creates the necessary intermediateOutputStreamWriter, 
																					which will convert characters into bytes using the default character encoding.*/

			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));/* socket.getInputStream() returns an input stream for this socket. 
																									  new InputStreamReader(socket.getInputStream()) creates an InputStreamReader that uses the default charset.
																									  new BufferedReader(new InputStreamReader(socket.getInputStream())) creates a buffering character-input stream that uses a default-sized input buffer.
																									*/
			
			clientInput = in.readLine();			   
			clientRadius = Double.valueOf(clientInput);
			System.out.println(clientRadius);
			circleArea = clientRadius * clientRadius * Math.PI;
			out.println(circleArea);
			
		
	}

}
