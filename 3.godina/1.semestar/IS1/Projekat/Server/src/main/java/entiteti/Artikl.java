package entiteti;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

public class Artikl implements Serializable {

    private String naziv;
    private String opis;
    private int cena;
    private int iDKor;
    private static final long serialVersionUID = 1L;
    private Integer iDArt;
    private Integer popust;
    private Kategorija iDKat;
    private List<Recenzija> recenzijaList;
    private List<Sadrzi> sadrziList;

    public Artikl() {
    }

    public Artikl(Integer iDArt) {
        this.iDArt = iDArt;
    }

    public Artikl(Integer iDArt, String naziv, int cena, int iDKor) {
        this.iDArt = iDArt;
        this.naziv = naziv;
        this.cena = cena;
        this.iDKor = iDKor;
    }

    public Integer getIDArt() {
        return iDArt;
    }

    public void setIDArt(Integer iDArt) {
        this.iDArt = iDArt;
    }


    public Integer getPopust() {
        return popust;
    }

    public void setPopust(Integer popust) {
        this.popust = popust;
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

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public int getIDKor() {
        return iDKor;
    }

    public void setIDKor(int iDKor) {
        this.iDKor = iDKor;
    }
    
}
