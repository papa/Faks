/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Jelena
 */
@Entity
@Table(name = "sahist")
@NamedQueries({
    @NamedQuery(name = "Sahist.findAll", query = "SELECT s FROM Sahist s")})
public class Sahist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SifK")
    private Integer sifK;
    @Basic(optional = false)
    @Column(name = "Rejting")
    private int rejting;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "beli")
    private List<Partija> partijaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "crni")
    private List<Partija> partijaList1;
    @JoinColumn(name = "SifK", referencedColumnName = "SifK", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Korisnik korisnik;

    public Sahist() {
    }

    public Sahist(Integer sifK) {
        this.sifK = sifK;
    }

    public Sahist(Integer sifK, int rejting) {
        this.sifK = sifK;
        this.rejting = rejting;
    }

    public Integer getSifK() {
        return sifK;
    }

    public void setSifK(Integer sifK) {
        this.sifK = sifK;
    }

    public int getRejting() {
        return rejting;
    }

    public void setRejting(int rejting) {
        this.rejting = rejting;
    }

    public List<Partija> getPartijaList() {
        return partijaList;
    }

    public void setPartijaList(List<Partija> partijaList) {
        this.partijaList = partijaList;
    }

    public List<Partija> getPartijaList1() {
        return partijaList1;
    }

    public void setPartijaList1(List<Partija> partijaList1) {
        this.partijaList1 = partijaList1;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sifK != null ? sifK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sahist)) {
            return false;
        }
        Sahist other = (Sahist) object;
        if ((this.sifK == null && other.sifK != null) || (this.sifK != null && !this.sifK.equals(other.sifK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Sahist[ sifK=" + sifK + " ]";
    }
    
}
