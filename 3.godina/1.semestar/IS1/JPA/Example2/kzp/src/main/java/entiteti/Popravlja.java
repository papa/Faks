/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Stefan
 */
@Entity
@Table(name = "popravlja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Popravlja.findAll", query = "SELECT p FROM Popravlja p")
    , @NamedQuery(name = "Popravlja.findByDana", query = "SELECT p FROM Popravlja p WHERE p.dana = :dana")
    , @NamedQuery(name = "Popravlja.findByIDZap", query = "SELECT p FROM Popravlja p WHERE p.popravljaPK.iDZap = :iDZap")
    , @NamedQuery(name = "Popravlja.findByIDKam", query = "SELECT p FROM Popravlja p WHERE p.popravljaPK.iDKam = :iDKam")})
public class Popravlja implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PopravljaPK popravljaPK;
    @Column(name = "Dana")
    private Integer dana;
    @JoinColumn(name = "IDZap", referencedColumnName = "IDZap", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Mehanicar mehanicar;
    @JoinColumn(name = "IDKam", referencedColumnName = "IDKam", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Kamion kamion;

    public Popravlja() {
    }

    public Popravlja(PopravljaPK popravljaPK) {
        this.popravljaPK = popravljaPK;
    }

    public Popravlja(int iDZap, int iDKam) {
        this.popravljaPK = new PopravljaPK(iDZap, iDKam);
    }

    public PopravljaPK getPopravljaPK() {
        return popravljaPK;
    }

    public void setPopravljaPK(PopravljaPK popravljaPK) {
        this.popravljaPK = popravljaPK;
    }

    public Integer getDana() {
        return dana;
    }

    public void setDana(Integer dana) {
        this.dana = dana;
    }

    public Mehanicar getMehanicar() {
        return mehanicar;
    }

    public void setMehanicar(Mehanicar mehanicar) {
        this.mehanicar = mehanicar;
    }

    public Kamion getKamion() {
        return kamion;
    }

    public void setKamion(Kamion kamion) {
        this.kamion = kamion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (popravljaPK != null ? popravljaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Popravlja)) {
            return false;
        }
        Popravlja other = (Popravlja) object;
        if ((this.popravljaPK == null && other.popravljaPK != null) || (this.popravljaPK != null && !this.popravljaPK.equals(other.popravljaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Popravlja[ popravljaPK=" + popravljaPK + " ]";
    }
    
}
