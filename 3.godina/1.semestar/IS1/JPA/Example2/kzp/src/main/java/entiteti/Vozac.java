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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Stefan
 */
@Entity
@Table(name = "vozac")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vozac.findAll", query = "SELECT v FROM Vozac v")
    , @NamedQuery(name = "Vozac.findByKategorija", query = "SELECT v FROM Vozac v WHERE v.kategorija = :kategorija")
    , @NamedQuery(name = "Vozac.findByIDZap", query = "SELECT v FROM Vozac v WHERE v.iDZap = :iDZap")})
public class Vozac implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 5)
    @Column(name = "Kategorija")
    private String kategorija;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDZap")
    private Integer iDZap;
    @JoinTable(name = "vozi", joinColumns = {
        @JoinColumn(name = "IDZap", referencedColumnName = "IDZap")}, inverseJoinColumns = {
        @JoinColumn(name = "IDPut", referencedColumnName = "IDPut")})
    @ManyToMany
    private List<Putovanje> putovanjeList;
    @JoinColumn(name = "IDZap", referencedColumnName = "IDZap", insertable = false, updatable = false)
    @OneToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
    private Zaposlen zaposlen;

    public Vozac() {
    }

    public Vozac(Integer iDZap) {
        this.iDZap = iDZap;
    }

    public String getKategorija() {
        return kategorija;
    }

    public void setKategorija(String kategorija) {
        this.kategorija = kategorija;
    }

    public Integer getIDZap() {
        return iDZap;
    }

    public void setIDZap(Integer iDZap) {
        this.iDZap = iDZap;
    }

    @XmlTransient
    public List<Putovanje> getPutovanjeList() {
        return putovanjeList;
    }

    public void setPutovanjeList(List<Putovanje> putovanjeList) {
        this.putovanjeList = putovanjeList;
    }
    
    public Zaposlen getZaposlen() {
        return zaposlen;
    }

    public void setZaposlen(Zaposlen zaposlen) {
        this.zaposlen = zaposlen;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDZap != null ? iDZap.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vozac)) {
            return false;
        }
        Vozac other = (Vozac) object;
        if ((this.iDZap == null && other.iDZap != null) || (this.iDZap != null && !this.iDZap.equals(other.iDZap))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Vozac[ iDZap=" + iDZap + " ]";
    }
    
}
