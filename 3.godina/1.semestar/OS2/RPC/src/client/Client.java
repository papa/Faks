package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread{
	
	ClientService clientService;
	private static int ID = -1;
	private int id = ++ID;
	private int x = id;
	private static int limit = 15;
	public Client() {System.out.println(id);}
	
	public void run() 
	{
		Socket socket;
		try {
			socket = new Socket("localhost", 5555);
			clientService = new ClientService(socket);
		} catch (IOException e) {}
		
		
		while(true)
		{
			x = clientService.f(x);
			System.out.println("id " + id + " x " + x);
			if(x >= limit)
				x = id;
		}
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		for(int i = 0; i < 2;i++)
		{
			new Client().start();
		}
		
	}
	
}
