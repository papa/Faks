/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationclient1;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * @author Jelena
 */
@Entity
@Table(name = "grad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grad.findAll", query = "SELECT g FROM Grad g"),
    @NamedQuery(name = "Grad.findByIDGrad", query = "SELECT g FROM Grad g WHERE g.iDGrad = :iDGrad"),
    @NamedQuery(name = "Grad.findByNaziv", query = "SELECT g FROM Grad g WHERE g.naziv = :naziv")})
public class Grad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDGrad")
    private Integer iDGrad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Naziv")
    private String naziv;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDGrad")
    private Collection<Korisnik> korisnikCollection;

    public Grad() {
    }

    public Grad(Integer iDGrad) {
        this.iDGrad = iDGrad;
    }

    public Grad(Integer iDGrad, String naziv) {
        this.iDGrad = iDGrad;
        this.naziv = naziv;
    }

    public Integer getIDGrad() {
        return iDGrad;
    }

    public void setIDGrad(Integer iDGrad) {
        this.iDGrad = iDGrad;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @XmlTransient
    public Collection<Korisnik> getKorisnikCollection() {
        return korisnikCollection;
    }

    public void setKorisnikCollection(Collection<Korisnik> korisnikCollection) {
        this.korisnikCollection = korisnikCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDGrad != null ? iDGrad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grad)) {
            return false;
        }
        Grad other = (Grad) object;
        if ((this.iDGrad == null && other.iDGrad != null) || (this.iDGrad != null && !this.iDGrad.equals(other.iDGrad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "applicationclient1.Grad[ iDGrad=" + iDGrad + " ]";
    }
    
}
