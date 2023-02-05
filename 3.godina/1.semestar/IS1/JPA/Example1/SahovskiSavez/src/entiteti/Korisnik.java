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
@Table(name = "korisnik")
@NamedQueries({
    @NamedQuery(name = "Korisnik.findAll", query = "SELECT k FROM Korisnik k")})
public class Korisnik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SifK")
    private Integer sifK;
    @Basic(optional = false)
    @Column(name = "Ime")
    private String ime;
    @Basic(optional = false)
    @Column(name = "Prezime")
    private String prezime;
    @Basic(optional = false)
    @Column(name = "BrLK")
    private int brLK;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "korisnik")
    private Komentator komentator;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "korisnik")
    private Sahist sahist;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sifK")
    private List<Ucestvovanje> ucestvovanjeList;

    public Korisnik() {
    }

    public Korisnik(Integer sifK) {
        this.sifK = sifK;
    }

    public Korisnik(Integer sifK, String ime, String prezime, int brLK) {
        this.sifK = sifK;
        this.ime = ime;
        this.prezime = prezime;
        this.brLK = brLK;
    }

    public Integer getSifK() {
        return sifK;
    }

    public void setSifK(Integer sifK) {
        this.sifK = sifK;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public int getBrLK() {
        return brLK;
    }

    public void setBrLK(int brLK) {
        this.brLK = brLK;
    }

    public Komentator getKomentator() {
        return komentator;
    }

    public void setKomentator(Komentator komentator) {
        this.komentator = komentator;
    }

    public Sahist getSahist() {
        return sahist;
    }

    public void setSahist(Sahist sahist) {
        this.sahist = sahist;
    }

    public List<Ucestvovanje> getUcestvovanjeList() {
        return ucestvovanjeList;
    }

    public void setUcestvovanjeList(List<Ucestvovanje> ucestvovanjeList) {
        this.ucestvovanjeList = ucestvovanjeList;
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
        if (!(object instanceof Korisnik)) {
            return false;
        }
        Korisnik other = (Korisnik) object;
        if ((this.sifK == null && other.sifK != null) || (this.sifK != null && !this.sifK.equals(other.sifK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Korisnik[ sifK=" + sifK + " ]";
    }
    
}
