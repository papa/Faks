package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Simulator extends Frame{
	
	private int sirina = 200;
	private int visina = 400;
	private Button startDugme = new Button("Pokreni!");
	private Svemir svemir = new Svemir();
	private Generator generator = new Generator(svemir);
	private Panel bottomPanel = new Panel();
	
	private void populateWindow()
	{	
		bottomPanel.add(startDugme);
		svemir.setBackground(Color.BLACK);
		add(bottomPanel, BorderLayout.SOUTH);
		add(svemir, BorderLayout.CENTER);
	}
	
	public Simulator()
	{
		setBounds(700, 200, sirina, visina);
		
		setResizable(false);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				generator.zaustavi();
				svemir.zaustavi();
				dispose();
			}
		});

		
		populateWindow();
		
		startDugme.addActionListener((ae) ->
		{
			startDugme.setEnabled(false);
			svemir.start();
			generator.start();
		});
		
		setVisible(true);
	}
	
	public static void main(String[] args)
	{
		new Simulator();
	}
}