package client;

import java.io.IOException;
import java.net.Socket;

import common.Service;

public class ClientService {
		
	Service service;
	
	public ClientService(Socket socket) throws IOException 
	{
		service = new Service(socket);
	}
	
	public int f(int x)
	{
		String s = new Integer(x).toString();
		service.sendMsg(s);
		return Integer.parseInt(service.receiveMsg());
	}
}
