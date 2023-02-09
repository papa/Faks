/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;

public class Sadrzi implements Serializable {

    private static final long serialVersionUID = 1L;
    protected SadrziPK sadrziPK;
    private double cena;
    private int kolicina;
    private Artikl artikl;
    private Korpa korpa;

    public Sadrzi() {
    }

    public Sadrzi(SadrziPK sadrziPK) {
        this.sadrziPK = sadrziPK;
    }

    public Sadrzi(SadrziPK sadrziPK, double cena, int kolicina) {
        this.sadrziPK = sadrziPK;
        this.cena = cena;
        this.kolicina = kolicina;
    }

    public Sadrzi(int iDArt, int iDKorpa) {
        this.sadrziPK = new SadrziPK(iDArt, iDKorpa);
    }

    public SadrziPK getSadrziPK() {
        return sadrziPK;
    }

    public void setSadrziPK(SadrziPK sadrziPK) {
        this.sadrziPK = sadrziPK;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public Artikl getArtikl() {
        return artikl;
    }

    public void setArtikl(Artikl artikl) {
        this.artikl = artikl;
    }

    public Korpa getKorpa() {
        return korpa;
    }

    public void setKorpa(Korpa korpa) {
        this.korpa = korpa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sadrziPK != null ? sadrziPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sadrzi)) {
            return false;
        }
        Sadrzi other = (Sadrzi) object;
        if ((this.sadrziPK == null && other.sadrziPK != null) || (this.sadrziPK != null && !this.sadrziPK.equals(other.sadrziPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Sadrzi[ sadrziPK=" + sadrziPK + " ]";
    }
    
}
