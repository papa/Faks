package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Service {
	
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	
	public Service(Socket sock) throws IOException 
	{ 
		socket = sock;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
	}	

	public void sendMsg(String s)
	{
		out.println(s);
	}
	
	public String receiveMsg() 
	{
		try {
			return in.readLine();
		} catch (IOException e) {}
		return "-1";
	}
	
	public void close() throws IOException
	{
		in.close();
		out.close();
	}
}
