package gui;

public class Generator implements Runnable
{
	Thread thread;
	private int spavanje = 900;
	Svemir svemir;
	
	Generator(Svemir s)
	{
		thread = new Thread(this);
		svemir = s;
	}

	@Override
	public void run() 
	{
		try 
		{
			while(!thread.interrupted()) 
			{
				int xkoordinata = (int)(Math.random()*200);
				int radius = (int)(Math.random()*20) + 10;
				svemir.dodaj(new Kometa(xkoordinata, 0, radius));
				thread.sleep(900);
			}
		}
		catch(InterruptedException e) {}
	}
	
	public void start() {
		thread.start();
	}
	
	public void zaustavi() {
		thread.interrupt();
	}
	
	
}