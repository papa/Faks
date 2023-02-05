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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "artikl")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Artikl.findAll", query = "SELECT a FROM Artikl a"),
    @NamedQuery(name = "Artikl.findByIDArt", query = "SELECT a FROM Artikl a WHERE a.iDArt = :iDArt"),
    @NamedQuery(name = "Artikl.findByNaziv", query = "SELECT a FROM Artikl a WHERE a.naziv = :naziv"),
    @NamedQuery(name = "Artikl.findByOpis", query = "SELECT a FROM Artikl a WHERE a.opis = :opis"),
    @NamedQuery(name = "Artikl.findByCena", query = "SELECT a FROM Artikl a WHERE a.cena = :cena"),
    @NamedQuery(name = "Artikl.findByPopust", query = "SELECT a FROM Artikl a WHERE a.popust = :popust"),
    @NamedQuery(name = "Artikl.findByIDKor", query = "SELECT a FROM Artikl a WHERE a.iDKor = :iDKor")})
public class Artikl implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "Naziv")
    private String naziv;
    @Size(max = 60)
    @Column(name = "Opis")
    private String opis;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Cena")
    private int cena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDKor")
    private int iDKor;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDArt")
    private Integer iDArt;
    @Column(name = "Popust")
    private Integer popust;
    @JoinColumn(name = "IDKat", referencedColumnName = "IDKat")
    @ManyToOne(optional = false)
    private Kategorija iDKat;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDArt")
    private List<Recenzija> recenzijaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artikl")
    private List<Sadrzi> sadrziList;

    public Artikl() {
    }

    public Artikl(Integer iDArt) {
        this.iDArt = iDArt;
    }

    public Artikl(Integer iDArt, String naziv, int cena, int iDKor) {
        this.iDArt = iDArt;
        this.naziv = naziv;
        this.cena = cena;
        this.iDKor = iDKor;
    }

    public Integer getIDArt() {
        return iDArt;
    }

    public void setIDArt(Integer iDArt) {
        this.iDArt = iDArt;
    }


    public Integer getPopust() {
        return popust;
    }

    public void setPopust(Integer popust) {
        this.popust = popust;
    }


    public Kategorija getIDKat() {
        return iDKat;
    }

    public void setIDKat(Kategorija iDKat) {
        this.iDKat = iDKat;
    }

    @XmlTransient
    public List<Recenzija> getRecenzijaList() {
        return recenzijaList;
    }

    public void setRecenzijaList(List<Recenzija> recenzijaList) {
        this.recenzijaList = recenzijaList;
    }

    @XmlTransient
    public List<Sadrzi> getSadrziList() {
        return sadrziList;
    }

    public void setSadrziList(List<Sadrzi> sadrziList) {
        this.sadrziList = sadrziList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDArt != null ? iDArt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Artikl)) {
            return false;
        }
        Artikl other = (Artikl) object;
        if ((this.iDArt == null && other.iDArt != null) || (this.iDArt != null && !this.iDArt.equals(other.iDArt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Artikl[ iDArt=" + iDArt + " ]";
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public int getIDKor() {
        return iDKor;
    }

    public void setIDKor(int iDKor) {
        this.iDKor = iDKor;
    }
    
}
