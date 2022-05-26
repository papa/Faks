package gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Plac extends Panel {

	private int brRedova;
	private int brKolona;
	private Parcela parcele[][];
	private int izabraniRed = -1;
	private int izabranaKol = -1;
	
	private Parcela selektovana = null;
	
	public Plac(int brR, int brK)
	{
		super(new GridLayout(brR, brK, 3, 3));
		brRedova = brR;
		brKolona = brK;
		
		parcele = new Parcela[brR][brK];
		for(int i = 0; i < brRedova;i++)
		{
			for(int j = 0; j < brKolona;j++)
			{
				double rand = Math.random();
				
				if(rand <= 0.7)
				{
					parcele[i][j] = new TravnataPovrs();
					this.add(parcele[i][j]);
				}
				else 
				{
					parcele[i][j] = new VodenaPovrs();
					this.add(parcele[i][j]);
				}
			}
		}
		
		addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e)
			{
				if(selektovana != null)
					selektovana.setFont(new Font(Font.SANS_SERIF, Font.BOLD,14));
				if(e.getSource() instanceof Parcela)
				{
					selektovana = (Parcela)e.getSource();
					selektovana.setFont(new Font(Font.SANS_SERIF, Font.BOLD,50));
					repaint();
				}
			}
		});
	}
	
	private int vodena(int i, int j)
	{
		if(i >= 0 && i < brRedova && j >= 0 && j < brKolona &&
				(parcele[i][j] instanceof VodenaPovrs))
			return 1;
		return 0;
	}
	
	public synchronized void dodajProizvodjaca(Proizvodjac proizvodjac)
	{
		izabraniRed = -1;
		izabranaKol = -1;
		for(int i = 0; i < brRedova;i++)
		{
			for(int j = 0 ; j < brKolona;j++)	
			{
				if(parcele[i][j] == selektovana)
				{
					izabraniRed = i;
					izabranaKol = j;
					break;
				}
			}
		}
		
		if(izabraniRed == -1) return;
		
		parcele[izabraniRed][izabranaKol] = proizvodjac;
		for(int i = 0;i < brRedova;i++)
		{
			for(int j = 0;j<brKolona;j++)
			{
				if(parcele[i][j] instanceof Hidroelektrana)
				{
					((Hidroelektrana)parcele[i][j]).postaviBrojVodenih(
							vodena(i - 1,j) + vodena(i+1,j)
							+vodena(i, j-1) + vodena(i,j+1));
				}
			}
		}
		
		this.remove(izabraniRed*brRedova + izabranaKol);
		this.add(proizvodjac, izabraniRed*brRedova + izabranaKol);
		
		revalidate();
	}
	
	public synchronized void zaustavi()
	{
		for(int i =0; i < brRedova;i++)
		{
			for(int j = 0; j < brKolona;j++)
			{
				if(parcele[i][j] instanceof Proizvodjac)
				{
					((Proizvodjac)parcele[i][j]).zaustavi();
				}
			}
		}
	}
}
