package gui;

import java.awt.Color;

public abstract class Proizvodjac extends Parcela implements Runnable {

	
	protected Baterija baterija;
	protected int ciklus;
	protected Thread thread = new Thread(this);
	
	public Proizvodjac(char oz, Color c, int cik, Baterija b) {
		super(oz, c);
		baterija = b;
		ciklus = cik;
		thread.start();
	}

	@Override
	public void run() 
	{
		int ukupnoVreme = ciklus + (int)(Math.random()*300);
		try
		{
			while(!thread.interrupted())
			{
				Thread.sleep(ukupnoVreme);
				int jediniceEnergije = proizvediEnergiju();
				Color prethodna = this.getForeground();
				if(jediniceEnergije > 0)
				{
					this.setForeground(Color.RED);
					baterija.dodajEnergiju(jediniceEnergije);
				}
				Thread.sleep(300);
				this.setForeground(prethodna);
			}
		}
		catch(InterruptedException e) {}
	}
	
	public abstract int proizvediEnergiju();

	public void zaustavi()
	{
		thread.interrupt();
	}
	
}
