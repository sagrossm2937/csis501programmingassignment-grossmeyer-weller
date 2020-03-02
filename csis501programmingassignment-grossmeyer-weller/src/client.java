import java.io.*;
import java.net.*;
import java.util.*;

public class client{
	public static void main(String[] args) throws Exception
	{
		Scanner input = new Scanner(System.in);
		String serverResponse;
		double circleArea, circleRadius;
		
		System.out.println("Enter the radius of the circle");
		circleRadius = input.nextDouble();
		
		
		
		//System.out.println(add);
		Socket socket = new Socket("localhost",9000);
		
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);  /*  socket.getOutputStream() returns an output stream for this socket. 
																				PrintWriter(socket.getOutputStream(), true) creates a new PrintWriter from an existing OutputStream. 
																				This convenience constructor creates the necessary intermediateOutputStreamWriter, 
																				which will convert characters into bytes using the default character encoding.*/

		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));/* socket.getInputStream() returns an input stream for this socket. 
																								  new InputStreamReader(socket.getInputStream()) creates an InputStreamReader that uses the default charset.
																								  new BufferedReader(new InputStreamReader(socket.getInputStream())) creates a buffering character-input stream that uses a default-sized input buffer.
																								*/
				
		out.println(circleRadius);
		serverResponse = in.readLine();
		circleArea = Double.valueOf(serverResponse);
		System.out.println("The area of the circle with radius "+circleRadius+" is: "+ circleArea);
		
			
		
	}
}
