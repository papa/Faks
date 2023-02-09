package entiteti;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;


public class Artikl implements Serializable {

    private static final long serialVersionUID = 1L;   
    private Integer iDArt;    
    private String naziv;    
    private String opis;    
    private double cena;   
    private double popust;    
    private int iDKor;    
    private Kategorija iDKat;   
    private List<Recenzija> recenzijaList;    
    private List<Sadrzi> sadrziList;

    public Artikl() {
    }

    public Artikl(Integer iDArt) {
        this.iDArt = iDArt;
    }

    public Artikl(Integer iDArt, String naziv, double cena, double popust, int iDKor) {
        this.iDArt = iDArt;
        this.naziv = naziv;
        this.cena = cena;
        this.popust = popust;
        this.iDKor = iDKor;
    }

    public Integer getIDArt() {
        return iDArt;
    }

    public void setIDArt(Integer iDArt) {
        this.iDArt = iDArt;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public double getPopust() {
        return popust;
    }

    public void setPopust(double popust) {
        this.popust = popust;
    }

    public int getIDKor() {
        return iDKor;
    }

    public void setIDKor(int iDKor) {
        this.iDKor = iDKor;
    }

    public Kategorija getIDKat() {
        return iDKat;
    }

    public void setIDKat(Kategorija iDKat) {
        this.iDKat = iDKat;
    }

    @XmlTransient
    public List<Recenzija> getRecenzijaList() {
        return recenzijaList;
    }

    public void setRecenzijaList(List<Recenzija> recenzijaList) {
        this.recenzijaList = recenzijaList;
    }

    @XmlTransient
    public List<Sadrzi> getSadrziList() {
        return sadrziList;
    }

    public void setSadrziList(List<Sadrzi> sadrziList) {
        this.sadrziList = sadrziList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDArt != null ? iDArt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Artikl)) {
            return false;
        }
        Artikl other = (Artikl) object;
        if ((this.iDArt == null && other.iDArt != null) || (this.iDArt != null && !this.iDArt.equals(other.iDArt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Artikl[ iDArt=" + iDArt + " ]";
    }
    
}
