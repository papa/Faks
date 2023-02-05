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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Jelena
 */
@Entity
@Table(name = "ucestvovanje")
@NamedQueries({
    @NamedQuery(name = "Ucestvovanje.findAll", query = "SELECT u FROM Ucestvovanje u")})
public class Ucestvovanje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SifU")
    private Integer sifU;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sifU")
    private List<Nagrada> nagradaList;
    @JoinColumn(name = "SifK", referencedColumnName = "SifK")
    @ManyToOne(optional = false)
    private Korisnik sifK;
    @JoinColumn(name = "SifT", referencedColumnName = "SifT")
    @ManyToOne(optional = false)
    private Turnir sifT;

    public Ucestvovanje() {
    }

    public Ucestvovanje(Integer sifU) {
        this.sifU = sifU;
    }

    public Integer getSifU() {
        return sifU;
    }

    public void setSifU(Integer sifU) {
        this.sifU = sifU;
    }

    public List<Nagrada> getNagradaList() {
        return nagradaList;
    }

    public void setNagradaList(List<Nagrada> nagradaList) {
        this.nagradaList = nagradaList;
    }

    public Korisnik getSifK() {
        return sifK;
    }

    public void setSifK(Korisnik sifK) {
        this.sifK = sifK;
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
        hash += (sifU != null ? sifU.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ucestvovanje)) {
            return false;
        }
        Ucestvovanje other = (Ucestvovanje) object;
        if ((this.sifU == null && other.sifU != null) || (this.sifU != null && !this.sifU.equals(other.sifU))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Ucestvovanje[ sifU=" + sifU + " ]";
    }
    
}
