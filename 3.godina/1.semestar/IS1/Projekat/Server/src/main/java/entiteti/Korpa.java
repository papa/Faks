/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;

public class Korpa implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer iDKorpa;
    private double ukupnaCena;
    private List<Sadrzi> sadrziList;

    public Korpa() {
    }

    public Korpa(Integer iDKorpa) {
        this.iDKorpa = iDKorpa;
    }

    public Korpa(Integer iDKorpa, double ukupnaCena) {
        this.iDKorpa = iDKorpa;
        this.ukupnaCena = ukupnaCena;
    }

    public Integer getIDKorpa() {
        return iDKorpa;
    }

    public void setIDKorpa(Integer iDKorpa) {
        this.iDKorpa = iDKorpa;
    }

    public double getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(double ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
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
        hash += (iDKorpa != null ? iDKorpa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Korpa)) {
            return false;
        }
        Korpa other = (Korpa) object;
        if ((this.iDKorpa == null && other.iDKorpa != null) || (this.iDKorpa != null && !this.iDKorpa.equals(other.iDKorpa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Korpa[ iDKorpa=" + iDKorpa + " ]";
    }
    
}
