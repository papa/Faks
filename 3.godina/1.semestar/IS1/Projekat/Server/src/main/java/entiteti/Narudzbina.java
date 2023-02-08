/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


public class Narudzbina implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer iDNar;
    
    private double ukupnaCena;
   
    private Date vremeKreiranja;
    
    private String adresa;
    
    private int iDGrad;
    
    private int iDKor;
    
    private List<Stavka> stavkaList;
    
    private List<Transakcija> transakcijaList;

    public Narudzbina() {
    }

    public Narudzbina(Integer iDNar) {
        this.iDNar = iDNar;
    }

    public Narudzbina(Integer iDNar, double ukupnaCena, Date vremeKreiranja, String adresa, int iDGrad, int iDKor) {
        this.iDNar = iDNar;
        this.ukupnaCena = ukupnaCena;
        this.vremeKreiranja = vremeKreiranja;
        this.adresa = adresa;
        this.iDGrad = iDGrad;
        this.iDKor = iDKor;
    }

    public Integer getIDNar() {
        return iDNar;
    }

    public void setIDNar(Integer iDNar) {
        this.iDNar = iDNar;
    }

    public double getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(double ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }

    public Date getVremeKreiranja() {
        return vremeKreiranja;
    }

    public void setVremeKreiranja(Date vremeKreiranja) {
        this.vremeKreiranja = vremeKreiranja;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public int getIDGrad() {
        return iDGrad;
    }

    public void setIDGrad(int iDGrad) {
        this.iDGrad = iDGrad;
    }

    public int getIDKor() {
        return iDKor;
    }

    public void setIDKor(int iDKor) {
        this.iDKor = iDKor;
    }

    @XmlTransient
    public List<Stavka> getStavkaList() {
        return stavkaList;
    }

    public void setStavkaList(List<Stavka> stavkaList) {
        this.stavkaList = stavkaList;
    }

    @XmlTransient
    public List<Transakcija> getTransakcijaList() {
        return transakcijaList;
    }

    public void setTransakcijaList(List<Transakcija> transakcijaList) {
        this.transakcijaList = transakcijaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDNar != null ? iDNar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Narudzbina)) {
            return false;
        }
        Narudzbina other = (Narudzbina) object;
        if ((this.iDNar == null && other.iDNar != null) || (this.iDNar != null && !this.iDNar.equals(other.iDNar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Narudzbina[ iDNar=" + iDNar + " ]";
    }
    
}
