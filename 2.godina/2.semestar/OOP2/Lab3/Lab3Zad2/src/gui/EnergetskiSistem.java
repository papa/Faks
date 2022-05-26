package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EnergetskiSistem extends Frame
{

	private Baterija baterija;
	private int redovi;
	private int kolone;
	private int kapacitet;
	Panel plac;
	private Panel topPanel = new Panel();
	Button dugmeDodaj = new Button("Dodaj");
	
	private void populateWindow()
	{
		plac = new Plac(redovi, kolone);
		baterija = new Baterija(kapacitet);
		setBounds(700, 200, 500, 500);
		setResizable(false);
		setTitle("Energetski sistem");
		
		add(plac, BorderLayout.CENTER);
	
		topPanel.add(dugmeDodaj);
		add(topPanel, BorderLayout.NORTH);
	}
	
	public EnergetskiSistem(int red, int kol, int kap)
	{
		redovi = red;
		kolone = kol;
		kapacitet = kap;
		
		populateWindow();
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				((Plac)plac).zaustavi();
				dispose();
			}
		});
		
		plac.repaint();
		
		dugmeDodaj.addActionListener((ae) -> {
			((Plac)plac).dodajProizvodjaca(new Hidroelektrana(baterija));
			repaint();
		});
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new EnergetskiSistem(5, 5, 5);
	}
}
