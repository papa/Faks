/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;


public class Sadrzi implements Serializable {


    private int cena;

    private int kolicina;

    private static final long serialVersionUID = 1L;
  
    protected SadrziPK sadrziPK;
   
    private Artikl artikl;
    
    private Korpa korpa;

    public Sadrzi() {
    }

    public Sadrzi(SadrziPK sadrziPK) {
        this.sadrziPK = sadrziPK;
    }

    public Sadrzi(SadrziPK sadrziPK, int cena, int kolicina) {
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

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }
    
}
