package utility;

import java.io.Serializable;

public class PaketArtikl implements Serializable{
    private int idArt;
    private int kolicina;
    private double cena;
    
    public PaketArtikl(int idArt, int kolicina, double cena)
    {
        this.idArt = idArt;
        this.kolicina=  kolicina;
        this.cena = cena;
    }

    public int getIdArt() {
        return idArt;
    }

    public int getKolicina() {
        return kolicina;
    }

    public double getCena() {
        return cena;
    }
}
