package entiteti;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "sadrzi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sadrzi.findAll", query = "SELECT s FROM Sadrzi s"),
    @NamedQuery(name = "Sadrzi.findByIDArt", query = "SELECT s FROM Sadrzi s WHERE s.sadrziPK.iDArt = :iDArt"),
    @NamedQuery(name = "Sadrzi.findByIDKorpa", query = "SELECT s FROM Sadrzi s WHERE s.sadrziPK.iDKorpa = :iDKorpa"),
    @NamedQuery(name = "Sadrzi.findByCena", query = "SELECT s FROM Sadrzi s WHERE s.cena = :cena"),
    @NamedQuery(name = "Sadrzi.findByIDArtIDKorpa", query = "SELECT s FROM Sadrzi s WHERE s.sadrziPK.iDArt = :iDArt and s.sadrziPK.iDKorpa = :iDKorpa"),
    @NamedQuery(name = "Sadrzi.findByKolicina", query = "SELECT s FROM Sadrzi s WHERE s.kolicina = :kolicina")})
public class Sadrzi implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SadrziPK sadrziPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Cena")
    private double cena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Kolicina")
    private int kolicina;
    @JoinColumn(name = "IDArt", referencedColumnName = "IDArt", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Artikl artikl;
    @JoinColumn(name = "IDKorpa", referencedColumnName = "IDKorpa", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Korpa korpa;

    public Sadrzi() {
    }

    public Sadrzi(SadrziPK sadrziPK) {
        this.sadrziPK = sadrziPK;
    }

    public Sadrzi(SadrziPK sadrziPK, double cena, int kolicina) {
        this.sadrziPK = sadrziPK;
        this.cena = cena;
        this.kolicina = kolicina;
    }

    public Sadrzi(int iDArt, int iDKorpa) {
        this.sadrziPK = new SadrziPK(iDArt, iDKorpa);
    }

    public SadrziPK getSadrziPK() {
        return sadrziPK;
    }

    public void setSadrziPK(SadrziPK sadrziPK) {
        this.sadrziPK = sadrziPK;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public Artikl getArtikl() {
        return artikl;
    }

    public void setArtikl(Artikl artikl) {
        this.artikl = artikl;
    }

    public Korpa getKorpa() {
        return korpa;
    }

    public void setKorpa(Korpa korpa) {
        this.korpa = korpa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sadrziPK != null ? sadrziPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sadrzi)) {
            return false;
        }
        Sadrzi other = (Sadrzi) object;
        if ((this.sadrziPK == null && other.sadrziPK != null) || (this.sadrziPK != null && !this.sadrziPK.equals(other.sadrziPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Sadrzi[ sadrziPK=" + sadrziPK + " ]";
    }
    
}
