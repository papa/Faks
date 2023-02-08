/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import java.io.Serializable;

/**
 *
 * @author Jelena
 */
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
