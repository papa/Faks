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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Jelena
 */
@Entity
@Table(name = "turnir")
@NamedQueries({
    @NamedQuery(name = "Turnir.findAll", query = "SELECT t FROM Turnir t")})
public class Turnir implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SifT")
    private Integer sifT;
    @Basic(optional = false)
    @Column(name = "Naziv")
    private String naziv;
    @Basic(optional = false)
    @Column(name = "Mesto")
    private String mesto;
    @Basic(optional = false)
    @Column(name = "Datum")
    @Temporal(TemporalType.DATE)
    private Date datum;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sifT")
    private List<Odigrana> odigranaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sifT")
    private List<Ucestvovanje> ucestvovanjeList;

    public Turnir() {
    }

    public Turnir(Integer sifT) {
        this.sifT = sifT;
    }

    public Turnir(Integer sifT, String naziv, String mesto, Date datum) {
        this.sifT = sifT;
        this.naziv = naziv;
        this.mesto = mesto;
        this.datum = datum;
    }

    public Integer getSifT() {
        return sifT;
    }

    public void setSifT(Integer sifT) {
        this.sifT = sifT;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public List<Odigrana> getOdigranaList() {
        return odigranaList;
    }

    public void setOdigranaList(List<Odigrana> odigranaList) {
        this.odigranaList = odigranaList;
    }

    public List<Ucestvovanje> getUcestvovanjeList() {
        return ucestvovanjeList;
    }

    public void setUcestvovanjeList(List<Ucestvovanje> ucestvovanjeList) {
        this.ucestvovanjeList = ucestvovanjeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sifT != null ? sifT.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Turnir)) {
            return false;
        }
        Turnir other = (Turnir) object;
        if ((this.sifT == null && other.sifT != null) || (this.sifT != null && !this.sifT.equals(other.sifT))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Turnir[ sifT=" + sifT + " ]";
    }
    
}
