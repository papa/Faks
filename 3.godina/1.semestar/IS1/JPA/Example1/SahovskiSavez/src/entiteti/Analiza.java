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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Jelena
 */
@Entity
@Table(name = "analiza")
@NamedQueries({
    @NamedQuery(name = "Analiza.findAll", query = "SELECT a FROM Analiza a")})
public class Analiza implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SifA")
    private Integer sifA;
    @Basic(optional = false)
    @Column(name = "Potez")
    private String potez;
    @JoinColumn(name = "SifK", referencedColumnName = "SifK")
    @ManyToOne(optional = false)
    private Komentator sifK;
    @JoinColumn(name = "SifZ", referencedColumnName = "SifZ")
    @ManyToOne(optional = false)
    private Potez sifZ;

    public Analiza() {
    }

    public Analiza(Integer sifA) {
        this.sifA = sifA;
    }

    public Analiza(Integer sifA, String potez) {
        this.sifA = sifA;
        this.potez = potez;
    }

    public Integer getSifA() {
        return sifA;
    }

    public void setSifA(Integer sifA) {
        this.sifA = sifA;
    }

    public String getPotez() {
        return potez;
    }

    public void setPotez(String potez) {
        this.potez = potez;
    }

    public Komentator getSifK() {
        return sifK;
    }

    public void setSifK(Komentator sifK) {
        this.sifK = sifK;
    }

    public Potez getSifZ() {
        return sifZ;
    }

    public void setSifZ(Potez sifZ) {
        this.sifZ = sifZ;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sifA != null ? sifA.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Analiza)) {
            return false;
        }
        Analiza other = (Analiza) object;
        if ((this.sifA == null && other.sifA != null) || (this.sifA != null && !this.sifA.equals(other.sifA))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Analiza[ sifA=" + sifA + " ]";
    }
    
}
