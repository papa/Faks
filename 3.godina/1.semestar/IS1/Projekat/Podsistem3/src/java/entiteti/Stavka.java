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
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "stavka")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stavka.findAll", query = "SELECT s FROM Stavka s"),
    @NamedQuery(name = "Stavka.findByIDStavka", query = "SELECT s FROM Stavka s WHERE s.iDStavka = :iDStavka"),
    @NamedQuery(name = "Stavka.findByIDArt", query = "SELECT s FROM Stavka s WHERE s.iDArt = :iDArt"),
    @NamedQuery(name = "Stavka.findByKolicina", query = "SELECT s FROM Stavka s WHERE s.kolicina = :kolicina"),
    @NamedQuery(name = "Stavka.findByCena", query = "SELECT s FROM Stavka s WHERE s.cena = :cena")})
public class Stavka implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDStavka")
    private Integer iDStavka;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDArt")
    private int iDArt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Kolicina")
    private int kolicina;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Cena")
    private double cena;
    @JoinColumn(name = "IDNar", referencedColumnName = "IDNar")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Narudzbina iDNar;

    public Stavka() {
    }

    public Stavka(Integer iDStavka) {
        this.iDStavka = iDStavka;
    }

    public Stavka(Integer iDStavka, int iDArt, int kolicina, double cena) {
        this.iDStavka = iDStavka;
        this.iDArt = iDArt;
        this.kolicina = kolicina;
        this.cena = cena;
    }

    public Integer getIDStavka() {
        return iDStavka;
    }

    public void setIDStavka(Integer iDStavka) {
        this.iDStavka = iDStavka;
    }

    public int getIDArt() {
        return iDArt;
    }

    public void setIDArt(int iDArt) {
        this.iDArt = iDArt;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public Narudzbina getIDNar() {
        return iDNar;
    }

    public void setIDNar(Narudzbina iDNar) {
        this.iDNar = iDNar;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDStavka != null ? iDStavka.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stavka)) {
            return false;
        }
        Stavka other = (Stavka) object;
        if ((this.iDStavka == null && other.iDStavka != null) || (this.iDStavka != null && !this.iDStavka.equals(other.iDStavka))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Stavka[ iDStavka=" + iDStavka + " ]";
    }
    
}
