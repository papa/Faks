package entiteti;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "kategorija")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kategorija.findAll", query = "SELECT k FROM Kategorija k"),
    @NamedQuery(name = "Kategorija.findByIDKat", query = "SELECT k FROM Kategorija k WHERE k.iDKat = :iDKat"),
    @NamedQuery(name = "Kategorija.findByNaziv", query = "SELECT k FROM Kategorija k WHERE k.naziv = :naziv")})
public class Kategorija implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDKat")
    private Integer iDKat;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Naziv")
    private String naziv;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDKat", fetch = FetchType.EAGER)
    private List<Artikl> artiklList;
    @OneToMany(mappedBy = "nadKat", fetch = FetchType.EAGER)
    private List<Kategorija> kategorijaList;
    @JoinColumn(name = "NadKat", referencedColumnName = "IDKat")
    @ManyToOne(fetch = FetchType.EAGER)
    private Kategorija nadKat;

    public Kategorija() {
    }

    public Kategorija(Integer iDKat) {
        this.iDKat = iDKat;
    }

    public Kategorija(Integer iDKat, String naziv) {
        this.iDKat = iDKat;
        this.naziv = naziv;
    }

    public Integer getIDKat() {
        return iDKat;
    }

    public void setIDKat(Integer iDKat) {
        this.iDKat = iDKat;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @XmlTransient
    public List<Artikl> getArtiklList() {
        return artiklList;
    }

    public void setArtiklList(List<Artikl> artiklList) {
        this.artiklList = artiklList;
    }

    @XmlTransient
    public List<Kategorija> getKategorijaList() {
        return kategorijaList;
    }

    public void setKategorijaList(List<Kategorija> kategorijaList) {
        this.kategorijaList = kategorijaList;
    }

    public Kategorija getNadKat() {
        return nadKat;
    }

    public void setNadKat(Kategorija nadKat) {
        this.nadKat = nadKat;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDKat != null ? iDKat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kategorija)) {
            return false;
        }
        Kategorija other = (Kategorija) object;
        if ((this.iDKat == null && other.iDKat != null) || (this.iDKat != null && !this.iDKat.equals(other.iDKat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Kategorija[ iDKat=" + iDKat + " ]";
    }
    
}
