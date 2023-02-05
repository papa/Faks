/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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

@Entity
@Table(name = "firma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Firma.findAll", query = "SELECT f FROM Firma f")
    , @NamedQuery(name = "Firma.findByIDFir", query = "SELECT f FROM Firma f WHERE f.iDFir = :iDFir")
    , @NamedQuery(name = "Firma.findByNaziv", query = "SELECT f FROM Firma f WHERE f.naziv = :naziv")
    , @NamedQuery(name = "Firma.findByAdresa", query = "SELECT f FROM Firma f WHERE f.adresa = :adresa")
    , @NamedQuery(name = "Firma.findByTel1", query = "SELECT f FROM Firma f WHERE f.tel1 = :tel1")
    , @NamedQuery(name = "Firma.findByTel2", query = "SELECT f FROM Firma f WHERE f.tel2 = :tel2")})
public class Firma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDFir")
    private Integer iDFir;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Naziv")
    private String naziv;
    @Size(max = 60)
    @Column(name = "Adresa")
    private String adresa;
    @Size(max = 15)
    @Column(name = "Tel1")
    private String tel1;
    @Size(max = 15)
    @Column(name = "Tel2")
    private String tel2;
    @OneToMany(mappedBy = "iDFir")
    private List<Posiljka> posiljkaList;

    public Firma() {
    }

    public Firma(Integer iDFir) {
        this.iDFir = iDFir;
    }

    public Firma(Integer iDFir, String naziv) {
        this.iDFir = iDFir;
        this.naziv = naziv;
    }

    public Integer getIDFir() {
        return iDFir;
    }

    public void setIDFir(Integer iDFir) {
        this.iDFir = iDFir;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    @XmlTransient
    public List<Posiljka> getPosiljkaList() {
        return posiljkaList;
    }

    public void setPosiljkaList(List<Posiljka> posiljkaList) {
        this.posiljkaList = posiljkaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDFir != null ? iDFir.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Firma)) {
            return false;
        }
        Firma other = (Firma) object;
        if ((this.iDFir == null && other.iDFir != null) || (this.iDFir != null && !this.iDFir.equals(other.iDFir))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Firma[ iDFir=" + iDFir + " ]";
    }
    
}
