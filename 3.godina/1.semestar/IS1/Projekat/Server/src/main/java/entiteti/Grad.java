package entiteti;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;


public class Grad implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer iDGrad;
    private String naziv;
    private List<Korisnik> korisnikList;

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
    public List<Korisnik> getKorisnikList() {
        return korisnikList;
    }

    public void setKorisnikList(List<Korisnik> korisnikList) {
        this.korisnikList = korisnikList;
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
        return "entiteti.Grad[ iDGrad=" + iDGrad + " ]";
    }
    
}
