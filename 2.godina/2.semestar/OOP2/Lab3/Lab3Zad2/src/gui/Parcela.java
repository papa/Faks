package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Parcela extends Label {
	
	private char oznaka;
	Color bojaPozadine;
	private static final String font = "Serif";
	private static final int stil = Font.BOLD;
	private int velicina = 14;
	
	public Parcela(char oz, Color c)
	{	
		super();
		this.setAlignment(Label.CENTER);
		this.setText(oz + "");
		this.setForeground(Color.WHITE);
		this.setBackground(c);
		this.setFont(new Font(font, stil, velicina));
	
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				//Component komp = (Component)e.getSource();
				//komp.getParent().dispatchEvent(e);
				Parcela.this.getParent().dispatchEvent(e);
			}
		});
	}
	
	public void uvecajFont(int delta) {
		setFont(new Font(font, stil, velicina+delta));
		velicina += delta;
	}

}
