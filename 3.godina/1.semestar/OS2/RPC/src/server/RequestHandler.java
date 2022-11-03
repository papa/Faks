package server;

import java.io.IOException;
import java.net.Socket;

import common.Service;

public class RequestHandler extends Thread {

	private Service service;
	private Socket socket;
	
	public RequestHandler(Socket sock) throws IOException
	{
		socket = sock;
		service = new Service(socket);
	}
	
	@Override
	public void run() {
		while(true)
		{
			String msg;
			msg = service.receiveMsg();
			int x = Integer.parseInt(msg);
			x++;
			Integer i = x;
			service.sendMsg(i.toString());	
		}
		//service.close()	
	}
	
}
