package gui;

import java.awt.Color;
import java.awt.Graphics;

public abstract class NebeskoTelo extends Objekat{

	protected int radius;
	
	public NebeskoTelo(int x, int y, Color c, int r) {
		super(x, y, c);
		this.radius = r;
	}
	
	public abstract void paint(Graphics g);
}