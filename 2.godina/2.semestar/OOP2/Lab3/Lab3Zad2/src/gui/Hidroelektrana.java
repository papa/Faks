package gui;

import java.awt.Color;

public class Hidroelektrana extends Proizvodjac {

	private int brojVodenihOko = 0;
	
	public Hidroelektrana(Baterija b) {
		super('H', Color.BLUE, 1500, b);
	}

	@Override
	public synchronized int proizvediEnergiju() {
		return brojVodenihOko;
	}
	
	public synchronized void postaviBrojVodenih(int br)
	{
		brojVodenihOko = br;
	}

}
