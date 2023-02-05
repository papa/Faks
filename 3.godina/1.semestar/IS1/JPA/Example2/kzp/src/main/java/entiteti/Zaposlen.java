/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Stefan
 */
@Entity
@Table(name = "zaposlen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zaposlen.findAll", query = "SELECT z FROM Zaposlen z")
    , @NamedQuery(name = "Zaposlen.findByIDZap", query = "SELECT z FROM Zaposlen z WHERE z.iDZap = :iDZap")
    , @NamedQuery(name = "Zaposlen.findByImePrezime", query = "SELECT z FROM Zaposlen z WHERE z.imePrezime = :imePrezime")
    , @NamedQuery(name = "Zaposlen.findByStaz", query = "SELECT z FROM Zaposlen z WHERE z.staz = :staz")})
public class Zaposlen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDZap")
    private Integer iDZap;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "ImePrezime")
    private String imePrezime;
    @Column(name = "Staz")
    private Integer staz;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "zaposlen")
    private Vozac vozac;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "zaposlen")
    private Mehanicar mehanicar;

    public Zaposlen() {
    }

    public Zaposlen(Integer iDZap) {
        this.iDZap = iDZap;
    }

    public Zaposlen(Integer iDZap, String imePrezime) {
        this.iDZap = iDZap;
        this.imePrezime = imePrezime;
    }

    public Integer getIDZap() {
        return iDZap;
    }

    public void setIDZap(Integer iDZap) {
        this.iDZap = iDZap;
    }

    public String getImePrezime() {
        return imePrezime;
    }

    public void setImePrezime(String imePrezime) {
        this.imePrezime = imePrezime;
    }

    public Integer getStaz() {
        return staz;
    }

    public void setStaz(Integer staz) {
        this.staz = staz;
    }

    @XmlTransient
    public Vozac getVozac() {
        return vozac;
    }

    public void setVozac(Vozac vozac) {
        this.vozac = vozac;
    }

    @XmlTransient
    public Mehanicar getMehanicar() {
        return mehanicar;
    }

    public void setMehanicar(Mehanicar mehanicar) {
        this.mehanicar = mehanicar;
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
        if (!(object instanceof Zaposlen)) {
            return false;
        }
        Zaposlen other = (Zaposlen) object;
        if ((this.iDZap == null && other.iDZap != null) || (this.iDZap != null && !this.iDZap.equals(other.iDZap))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Zaposlen[ iDZap=" + iDZap + " ]";
    }
    
}
