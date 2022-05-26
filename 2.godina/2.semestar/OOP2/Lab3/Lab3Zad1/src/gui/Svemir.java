package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Vector;

public class Svemir extends Canvas implements Runnable{
	
	private ArrayList<NebeskoTelo> nebeskaTela;
	private int spavanje = 100;
	Thread thread;
	
	Svemir()
	{
		nebeskaTela = new ArrayList<NebeskoTelo>();
		setBackground(Color.BLACK);
		thread = new Thread(this);
	}
	
	@Override
	public synchronized void paint(Graphics g) {
		for(NebeskoTelo t:nebeskaTela)
			t.paint(g);
	}


	@Override
	public void run() 
	{
		try 
		{
			while(!thread.interrupted()) 
			{
				for(int i = 0;i < nebeskaTela.size();i++)
					nebeskaTela.get(i).promeniYCentar(5);
				repaint();
				thread.sleep(spavanje);
			}
		}
		catch(InterruptedException e) {}
	}

	public void start() 
	{
		thread.start();
	} 
	
	public void zaustavi() 
	{
		thread.interrupt();
	}
	
	public void dodaj(NebeskoTelo t)
	{
		nebeskaTela.add(t);
	}
	
}