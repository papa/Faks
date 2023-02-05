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

/**
 *
 * @author Jelena
 */
@Entity
@Table(name = "komentator")
@NamedQueries({
    @NamedQuery(name = "Komentator.findAll", query = "SELECT k FROM Komentator k")})
public class Komentator implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "SifK")
    private Integer sifK;
    @Basic(optional = false)
    @Column(name = "iskustvo")
    private int iskustvo;
    @JoinColumn(name = "SifK", referencedColumnName = "SifK", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Korisnik korisnik;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sifK")
    private List<Analiza> analizaList;

    public Komentator() {
    }

    public Komentator(Integer sifK) {
        this.sifK = sifK;
    }

    public Komentator(Integer sifK, int iskustvo) {
        this.sifK = sifK;
        this.iskustvo = iskustvo;
    }

    public Integer getSifK() {
        return sifK;
    }

    public void setSifK(Integer sifK) {
        this.sifK = sifK;
    }

    public int getIskustvo() {
        return iskustvo;
    }

    public void setIskustvo(int iskustvo) {
        this.iskustvo = iskustvo;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public List<Analiza> getAnalizaList() {
        return analizaList;
    }

    public void setAnalizaList(List<Analiza> analizaList) {
        this.analizaList = analizaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sifK != null ? sifK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Komentator)) {
            return false;
        }
        Komentator other = (Komentator) object;
        if ((this.sifK == null && other.sifK != null) || (this.sifK != null && !this.sifK.equals(other.sifK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Komentator[ sifK=" + sifK + " ]";
    }
    
}
