package gui;

import java.awt.Color;
import java.awt.Graphics;

public class Kometa extends NebeskoTelo {
	
	private double rotacija;
	private static final double ugaoDodaj = (72*Math.PI/180);
	private int[] xKoordinate = new int[5];
	private int[] yKoordinate = new int[5];
	
	public Kometa(int x, int y, int r) 
	{
		super(x, y, Color.GRAY, r);
		napraviPetougao();
	}

	private void napraviPetougao() 
	{
		rotacija = Math.random()*(360 * Math.PI/180);
		for(int i=0;i<5;i++) 
		{
			xKoordinate[i] = centarX + (int)(Math.cos(rotacija) * radius); 
			yKoordinate[i] = centarY + (int)(Math.sin(rotacija) * radius); 
			rotacija+=ugaoDodaj;
		}
	}
	
	public void paint(Graphics g) 
	{
		g.setColor(boja());
		g.fillPolygon(xKoordinate, yKoordinate, 5);
	}
	
	@Override
	public void promeniYCentar(int delta) {
		centarY+=delta;
		for(int i=0;i<5;i++)
			yKoordinate[i]+=delta;
	}
}