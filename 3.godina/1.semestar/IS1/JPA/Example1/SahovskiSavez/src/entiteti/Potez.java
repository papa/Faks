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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "potez")
@NamedQueries({
    @NamedQuery(name = "Potez.findAll", query = "SELECT p FROM Potez p")})
public class Potez implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SifZ")
    private Integer sifZ;
    @Basic(optional = false)
    @Column(name = "Potez")
    private String potez;
    @Basic(optional = false)
    @Column(name = "BrPoteza")
    private int brPoteza;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sifZ")
    private List<Analiza> analizaList;
    @JoinColumn(name = "SifP", referencedColumnName = "SifP")
    @ManyToOne(optional = false)
    private Partija sifP;

    public Potez() {
    }

    public Potez(Integer sifZ) {
        this.sifZ = sifZ;
    }

    public Potez(Integer sifZ, String potez, int brPoteza) {
        this.sifZ = sifZ;
        this.potez = potez;
        this.brPoteza = brPoteza;
    }

    public Integer getSifZ() {
        return sifZ;
    }

    public void setSifZ(Integer sifZ) {
        this.sifZ = sifZ;
    }

    public String getPotez() {
        return potez;
    }

    public void setPotez(String potez) {
        this.potez = potez;
    }

    public int getBrPoteza() {
        return brPoteza;
    }

    public void setBrPoteza(int brPoteza) {
        this.brPoteza = brPoteza;
    }

    public List<Analiza> getAnalizaList() {
        return analizaList;
    }

    public void setAnalizaList(List<Analiza> analizaList) {
        this.analizaList = analizaList;
    }

    public Partija getSifP() {
        return sifP;
    }

    public void setSifP(Partija sifP) {
        this.sifP = sifP;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sifZ != null ? sifZ.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Potez)) {
            return false;
        }
        Potez other = (Potez) object;
        if ((this.sifZ == null && other.sifZ != null) || (this.sifZ != null && !this.sifZ.equals(other.sifZ))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Potez[ sifZ=" + sifZ + " ]";
    }
    
}
