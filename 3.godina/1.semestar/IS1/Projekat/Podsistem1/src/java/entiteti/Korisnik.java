package entiteti;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "korisnik")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Korisnik.findAll", query = "SELECT k FROM Korisnik k"),
    @NamedQuery(name = "Korisnik.findByIDKor", query = "SELECT k FROM Korisnik k WHERE k.iDKor = :iDKor"),
    @NamedQuery(name = "Korisnik.findByIme", query = "SELECT k FROM Korisnik k WHERE k.ime = :ime"),
    @NamedQuery(name = "Korisnik.findByPrezime", query = "SELECT k FROM Korisnik k WHERE k.prezime = :prezime"),
    @NamedQuery(name = "Korisnik.findByUsername", query = "SELECT k FROM Korisnik k WHERE k.username = :username"),
    @NamedQuery(name = "Korisnik.findBySifra", query = "SELECT k FROM Korisnik k WHERE k.sifra = :sifra"),
    @NamedQuery(name = "Korisnik.findByAdresa", query = "SELECT k FROM Korisnik k WHERE k.adresa = :adresa"),
    @NamedQuery(name = "Korisnik.findByNovac", query = "SELECT k FROM Korisnik k WHERE k.novac = :novac")})
public class Korisnik implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDKor")
    private Integer iDKor;
    @Size(max = 100)
    @Column(name = "Ime")
    private String ime;
    @Size(max = 100)
    @Column(name = "Prezime")
    private String prezime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Username")
    private String username;
    @Size(max = 100)
    @Column(name = "Sifra")
    private String sifra;
    @Size(max = 100)
    @Column(name = "Adresa")
    private String adresa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Novac")
    private double novac;
    @JoinColumn(name = "IDGrad", referencedColumnName = "IDGrad")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Grad iDGrad;

    public Korisnik() {
    }

    public Korisnik(Integer iDKor) {
        this.iDKor = iDKor;
    }

    public Korisnik(Integer iDKor, String username, double novac) {
        this.iDKor = iDKor;
        this.username = username;
        this.novac = novac;
    }

    public Integer getIDKor() {
        return iDKor;
    }

    public void setIDKor(Integer iDKor) {
        this.iDKor = iDKor;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public double getNovac() {
        return novac;
    }

    public void setNovac(double novac) {
        this.novac = novac;
    }

    public Grad getIDGrad() {
        return iDGrad;
    }

    public void setIDGrad(Grad iDGrad) {
        this.iDGrad = iDGrad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDKor != null ? iDKor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Korisnik)) {
            return false;
        }
        Korisnik other = (Korisnik) object;
        if ((this.iDKor == null && other.iDKor != null) || (this.iDKor != null && !this.iDKor.equals(other.iDKor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Korisnik[ iDKor=" + iDKor + " ]";
    }
    
}
