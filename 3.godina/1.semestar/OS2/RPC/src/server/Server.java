package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public Server() {}
	
	private ServerSocket serverSocket;
	private int port = 6666;
	
	public void run() throws IOException
	{
		serverSocket = new ServerSocket(port);
		
		while(true)
		{
			Socket socket = serverSocket.accept();
			new RequestHandler(socket).start();
		}
		//serverSocket.close();
	}
	
	public static void main(String[] args) throws IOException {
		new Server().run();
	}
	
}
