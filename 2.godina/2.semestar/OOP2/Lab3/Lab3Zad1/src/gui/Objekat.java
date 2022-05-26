package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Shape;

public abstract class Objekat implements Cloneable {
	protected int centarX,centarY;
	protected static Color boja;
	
	public Objekat(int x, int y, Color c) {
		this.centarX = x;
		this.centarY = y;
		boja = c;
	}

	public int getXCentar() 
	{
		return centarX;
	}

	public void promeniXCentar(int delta)
	{
		this.centarX += delta;
	}

	public int getYCentar() 
	{
		return centarY;
	}

	public void promeniYCentar(int delta)
	{
		this.centarY += delta;
	}
	
	public static Color boja() 
	{
		return boja;
	}
	
	public Objekat clone() 
	{
		try 
		{
			return (Objekat) super.clone();
		} catch (CloneNotSupportedException e) { return null; }
	}
	
	public abstract void paint(Graphics g);
}