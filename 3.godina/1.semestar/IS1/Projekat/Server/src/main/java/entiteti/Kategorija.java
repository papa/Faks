package entiteti;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;

public class Kategorija implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer iDKat;
    private String naziv;
    private List<Artikl> artiklList;
    private List<Kategorija> kategorijaList;
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
