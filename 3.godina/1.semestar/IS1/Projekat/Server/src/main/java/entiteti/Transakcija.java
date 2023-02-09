/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.util.Date;

public class Transakcija implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer iDTrans;
    private double suma;
    private Date vremePlacanja;
    private Narudzbina iDNar;

    public Transakcija() {
    }

    public Transakcija(Integer iDTrans) {
        this.iDTrans = iDTrans;
    }

    public Transakcija(Integer iDTrans, double suma, Date vremePlacanja) {
        this.iDTrans = iDTrans;
        this.suma = suma;
        this.vremePlacanja = vremePlacanja;
    }

    public Integer getIDTrans() {
        return iDTrans;
    }

    public void setIDTrans(Integer iDTrans) {
        this.iDTrans = iDTrans;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    public Date getVremePlacanja() {
        return vremePlacanja;
    }

    public void setVremePlacanja(Date vremePlacanja) {
        this.vremePlacanja = vremePlacanja;
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
        hash += (iDTrans != null ? iDTrans.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transakcija)) {
            return false;
        }
        Transakcija other = (Transakcija) object;
        if ((this.iDTrans == null && other.iDTrans != null) || (this.iDTrans != null && !this.iDTrans.equals(other.iDTrans))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Transakcija[ iDTrans=" + iDTrans + " ]";
    }
    
}
