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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Jelena
 */
@Entity
@Table(name = "partija")
@NamedQueries({
    @NamedQuery(name = "Partija.findAll", query = "SELECT p FROM Partija p")})
public class Partija implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SifP")
    private Integer sifP;
    @Basic(optional = false)
    @Column(name = "Vreme")
    private int vreme;
    @Basic(optional = false)
    @Column(name = "Ishod")
    private int ishod;
    @JoinColumn(name = "Beli", referencedColumnName = "SifK")
    @ManyToOne(optional = false)
    private Sahist beli;
    @JoinColumn(name = "Crni", referencedColumnName = "SifK")
    @ManyToOne(optional = false)
    private Sahist crni;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sifP")
    private List<Potez> potezList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "partija")
    private Odigrana odigrana;

    public Partija() {
    }

    public Partija(Integer sifP) {
        this.sifP = sifP;
    }

    public Partija(Integer sifP, int vreme, int ishod) {
        this.sifP = sifP;
        this.vreme = vreme;
        this.ishod = ishod;
    }

    public Integer getSifP() {
        return sifP;
    }

    public void setSifP(Integer sifP) {
        this.sifP = sifP;
    }

    public int getVreme() {
        return vreme;
    }

    public void setVreme(int vreme) {
        this.vreme = vreme;
    }

    public int getIshod() {
        return ishod;
    }

    public void setIshod(int ishod) {
        this.ishod = ishod;
    }

    public Sahist getBeli() {
        return beli;
    }

    public void setBeli(Sahist beli) {
        this.beli = beli;
    }

    public Sahist getCrni() {
        return crni;
    }

    public void setCrni(Sahist crni) {
        this.crni = crni;
    }

    public List<Potez> getPotezList() {
        return potezList;
    }

    public void setPotezList(List<Potez> potezList) {
        this.potezList = potezList;
    }

    public Odigrana getOdigrana() {
        return odigrana;
    }

    public void setOdigrana(Odigrana odigrana) {
        this.odigrana = odigrana;
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
        if (!(object instanceof Partija)) {
            return false;
        }
        Partija other = (Partija) object;
        if ((this.sifP == null && other.sifP != null) || (this.sifP != null && !this.sifP.equals(other.sifP))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Partija[ sifP=" + sifP + " ]";
    }
    
}
