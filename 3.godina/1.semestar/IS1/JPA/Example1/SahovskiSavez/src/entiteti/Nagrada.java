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
@Table(name = "nagrada")
@NamedQueries({
    @NamedQuery(name = "Nagrada.findAll", query = "SELECT n FROM Nagrada n")})
public class Nagrada implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SifN")
    private Integer sifN;
    @Basic(optional = false)
    @Column(name = "Opis")
    private String opis;
    @JoinColumn(name = "SifU", referencedColumnName = "SifU")
    @ManyToOne(optional = false)
    private Ucestvovanje sifU;

    public Nagrada() {
    }

    public Nagrada(Integer sifN) {
        this.sifN = sifN;
    }

    public Nagrada(Integer sifN, String opis) {
        this.sifN = sifN;
        this.opis = opis;
    }

    public Integer getSifN() {
        return sifN;
    }

    public void setSifN(Integer sifN) {
        this.sifN = sifN;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Ucestvovanje getSifU() {
        return sifU;
    }

    public void setSifU(Ucestvovanje sifU) {
        this.sifU = sifU;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sifN != null ? sifN.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nagrada)) {
            return false;
        }
        Nagrada other = (Nagrada) object;
        if ((this.sifN == null && other.sifN != null) || (this.sifN != null && !this.sifN.equals(other.sifN))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Nagrada[ sifN=" + sifN + " ]";
    }
    
}
