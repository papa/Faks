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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "kamion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kamion.findAll", query = "SELECT k FROM Kamion k")
    , @NamedQuery(name = "Kamion.findByIDKam", query = "SELECT k FROM Kamion k WHERE k.iDKam = :iDKam")
    , @NamedQuery(name = "Kamion.findByMarka", query = "SELECT k FROM Kamion k WHERE k.marka = :marka")
    , @NamedQuery(name = "Kamion.findByNosivost", query = "SELECT k FROM Kamion k WHERE k.nosivost = :nosivost")
    , @NamedQuery(name = "Kamion.findByGodiste", query = "SELECT k FROM Kamion k WHERE k.godiste = :godiste")
    , @NamedQuery(name = "Kamion.findByBrPopravljanja", query = "SELECT k FROM Kamion k WHERE k.brPopravljanja = :brPopravljanja")})
public class Kamion implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "Marka")
    private String marka;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Nosivost")
    private int nosivost;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BrPopravljanja")
    private int brPopravljanja;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDKam")
    private Integer iDKam;
    @Column(name = "Godiste")
    private Integer godiste;
    @OneToMany(mappedBy = "iDKam")
    private List<Putovanje> putovanjeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kamion")
    private List<Popravlja> popravljaList;

    public Kamion() {
    }

    public Kamion(Integer iDKam) {
        this.iDKam = iDKam;
    }

    public Kamion(Integer iDKam, String marka, int nosivost, int brPopravljanja) {
        this.iDKam = iDKam;
        this.marka = marka;
        this.nosivost = nosivost;
        this.brPopravljanja = brPopravljanja;
    }

    public Integer getIDKam() {
        return iDKam;
    }

    public void setIDKam(Integer iDKam) {
        this.iDKam = iDKam;
    }


    public Integer getGodiste() {
        return godiste;
    }

    public void setGodiste(Integer godiste) {
        this.godiste = godiste;
    }


    @XmlTransient
    public List<Putovanje> getPutovanjeList() {
        return putovanjeList;
    }

    public void setPutovanjeList(List<Putovanje> putovanjeList) {
        this.putovanjeList = putovanjeList;
    }

    @XmlTransient
    public List<Popravlja> getPopravljaList() {
        return popravljaList;
    }

    public void setPopravljaList(List<Popravlja> popravljaList) {
        this.popravljaList = popravljaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDKam != null ? iDKam.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kamion)) {
            return false;
        }
        Kamion other = (Kamion) object;
        if ((this.iDKam == null && other.iDKam != null) || (this.iDKam != null && !this.iDKam.equals(other.iDKam))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Kamion[ iDKam=" + iDKam + " ]";
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public int getNosivost() {
        return nosivost;
    }

    public void setNosivost(int nosivost) {
        this.nosivost = nosivost;
    }

    public int getBrPopravljanja() {
        return brPopravljanja;
    }

    public void setBrPopravljanja(int brPopravljanja) {
        this.brPopravljanja = brPopravljanja;
    }
    
}
