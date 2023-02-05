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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Stefan
 */
@Entity
@Table(name = "mehanicar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mehanicar.findAll", query = "SELECT m FROM Mehanicar m")
    , @NamedQuery(name = "Mehanicar.findBySpecijalnost", query = "SELECT m FROM Mehanicar m WHERE m.specijalnost = :specijalnost")
    , @NamedQuery(name = "Mehanicar.findByIDZap", query = "SELECT m FROM Mehanicar m WHERE m.iDZap = :iDZap")})
public class Mehanicar implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 15)
    @Column(name = "Specijalnost")
    private String specijalnost;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDZap")
    private Integer iDZap;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mehanicar")
    private List<Popravlja> popravljaList;
    @JoinColumn(name = "IDZap", referencedColumnName = "IDZap", insertable = false, updatable = false)
    @OneToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
    private Zaposlen zaposlen;

    public Mehanicar() {
    }

    public Mehanicar(Integer iDZap) {
        this.iDZap = iDZap;
    }

    public String getSpecijalnost() {
        return specijalnost;
    }

    public void setSpecijalnost(String specijalnost) {
        this.specijalnost = specijalnost;
    }

    public Integer getIDZap() {
        return iDZap;
    }

    public void setIDZap(Integer iDZap) {
        this.iDZap = iDZap;
    }

    @XmlTransient
    public List<Popravlja> getPopravljaList() {
        return popravljaList;
    }

    public void setPopravljaList(List<Popravlja> popravljaList) {
        this.popravljaList = popravljaList;
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
        if (!(object instanceof Mehanicar)) {
            return false;
        }
        Mehanicar other = (Mehanicar) object;
        if ((this.iDZap == null && other.iDZap != null) || (this.iDZap != null && !this.iDZap.equals(other.iDZap))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Mehanicar[ iDZap=" + iDZap + " ]";
    }
    
}
