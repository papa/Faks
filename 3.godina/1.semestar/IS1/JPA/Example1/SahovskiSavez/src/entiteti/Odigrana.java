/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Jelena
 */
@Entity
@Table(name = "odigrana")
@NamedQueries({
    @NamedQuery(name = "Odigrana.findAll", query = "SELECT o FROM Odigrana o")})
public class Odigrana implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SifP")
    private Integer sifP;
    @JoinColumn(name = "SifP", referencedColumnName = "SifP", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Partija partija;
    @JoinColumn(name = "SifT", referencedColumnName = "SifT")
    @ManyToOne(optional = false)
    private Turnir sifT;

    public Odigrana() {
    }

    public Odigrana(Integer sifP) {
        this.sifP = sifP;
    }

    public Integer getSifP() {
        return sifP;
    }

    public void setSifP(Integer sifP) {
        this.sifP = sifP;
    }

    public Partija getPartija() {
        return partija;
    }

    public void setPartija(Partija partija) {
        this.partija = partija;
    }

    public Turnir getSifT() {
        return sifT;
    }

    public void setSifT(Turnir sifT) {
        this.sifT = sifT;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sifP != null ? sifP.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Odigrana)) {
            return false;
        }
        Odigrana other = (Odigrana) object;
        if ((this.sifP == null && other.sifP != null) || (this.sifP != null && !this.sifP.equals(other.sifP))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Odigrana[ sifP=" + sifP + " ]";
    }
    
}
