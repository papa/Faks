package entiteti;

import java.io.Serializable;

public class Stavka implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer iDStavka;
    private int iDArt;
    private int kolicina;
    private double cena;
    private Narudzbina iDNar;

    public Stavka() {
    }

    public Stavka(Integer iDStavka) {
        this.iDStavka = iDStavka;
    }

    public Stavka(Integer iDStavka, int iDArt, int kolicina, double cena) {
        this.iDStavka = iDStavka;
        this.iDArt = iDArt;
        this.kolicina = kolicina;
        this.cena = cena;
    }

    public Integer getIDStavka() {
        return iDStavka;
    }

    public void setIDStavka(Integer iDStavka) {
        this.iDStavka = iDStavka;
    }

    public int getIDArt() {
        return iDArt;
    }

    public void setIDArt(int iDArt) {
        this.iDArt = iDArt;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public Narudzbina getIDNar() {
        return iDNar;
    }

    public void setIDNar(Narudzbina iDNar) {
        this.iDNar = iDNar;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDStavka != null ? iDStavka.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stavka)) {
            return false;
        }
        Stavka other = (Stavka) object;
        if ((this.iDStavka == null && other.iDStavka != null) || (this.iDStavka != null && !this.iDStavka.equals(other.iDStavka))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Stavka[ iDStavka=" + iDStavka + " ]";
    }
    
}
