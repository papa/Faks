package gui;

public class Baterija 
{
	
	private int kolicinaEnergije;
	private int maksKapacitet;
	
	public Baterija(int mk)
	{
		kolicinaEnergije = maksKapacitet = mk;
	}
	
	public synchronized void dodajEnergiju(int e)
	{
		kolicinaEnergije = Math.min(kolicinaEnergije + e, maksKapacitet);
	}
	
	public synchronized void isprazniBateriju()
	{
		kolicinaEnergije = 0;
	}
	
	public boolean puna()
	{
		return kolicinaEnergije == maksKapacitet;
	}
}
