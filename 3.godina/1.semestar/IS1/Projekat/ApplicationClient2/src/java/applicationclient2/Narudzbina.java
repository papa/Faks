/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationclient2;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jelena
 */
@Entity
@Table(name = "narudzbina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Narudzbina.findAll", query = "SELECT n FROM Narudzbina n"),
    @NamedQuery(name = "Narudzbina.findByIDNar", query = "SELECT n FROM Narudzbina n WHERE n.iDNar = :iDNar"),
    @NamedQuery(name = "Narudzbina.findByUkupnaCena", query = "SELECT n FROM Narudzbina n WHERE n.ukupnaCena = :ukupnaCena"),
    @NamedQuery(name = "Narudzbina.findByVremeKreiranja", query = "SELECT n FROM Narudzbina n WHERE n.vremeKreiranja = :vremeKreiranja"),
    @NamedQuery(name = "Narudzbina.findByAdresa", query = "SELECT n FROM Narudzbina n WHERE n.adresa = :adresa"),
    @NamedQuery(name = "Narudzbina.findByIDGrad", query = "SELECT n FROM Narudzbina n WHERE n.iDGrad = :iDGrad"),
    @NamedQuery(name = "Narudzbina.findByIDKor", query = "SELECT n FROM Narudzbina n WHERE n.iDKor = :iDKor")})
public class Narudzbina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDNar")
    private Integer iDNar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UkupnaCena")
    private double ukupnaCena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VremeKreiranja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vremeKreiranja;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Adresa")
    private String adresa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDGrad")
    private int iDGrad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDKor")
    private int iDKor;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDNar")
    private Collection<Stavka> stavkaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDNar")
    private Collection<Transakcija> transakcijaCollection;

    public Narudzbina() {
    }

    public Narudzbina(Integer iDNar) {
        this.iDNar = iDNar;
    }

    public Narudzbina(Integer iDNar, double ukupnaCena, Date vremeKreiranja, String adresa, int iDGrad, int iDKor) {
        this.iDNar = iDNar;
        this.ukupnaCena = ukupnaCena;
        this.vremeKreiranja = vremeKreiranja;
        this.adresa = adresa;
        this.iDGrad = iDGrad;
        this.iDKor = iDKor;
    }

    public Integer getIDNar() {
        return iDNar;
    }

    public void setIDNar(Integer iDNar) {
        this.iDNar = iDNar;
    }

    public double getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(double ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }

    public Date getVremeKreiranja() {
        return vremeKreiranja;
    }

    public void setVremeKreiranja(Date vremeKreiranja) {
        this.vremeKreiranja = vremeKreiranja;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public int getIDGrad() {
        return iDGrad;
    }

    public void setIDGrad(int iDGrad) {
        this.iDGrad = iDGrad;
    }

    public int getIDKor() {
        return iDKor;
    }

    public void setIDKor(int iDKor) {
        this.iDKor = iDKor;
    }

    @XmlTransient
    public Collection<Stavka> getStavkaCollection() {
        return stavkaCollection;
    }

    public void setStavkaCollection(Collection<Stavka> stavkaCollection) {
        this.stavkaCollection = stavkaCollection;
    }

    @XmlTransient
    public Collection<Transakcija> getTransakcijaCollection() {
        return transakcijaCollection;
    }

    public void setTransakcijaCollection(Collection<Transakcija> transakcijaCollection) {
        this.transakcijaCollection = transakcijaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDNar != null ? iDNar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Narudzbina)) {
            return false;
        }
        Narudzbina other = (Narudzbina) object;
        if ((this.iDNar == null && other.iDNar != null) || (this.iDNar != null && !this.iDNar.equals(other.iDNar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "applicationclient2.Narudzbina[ iDNar=" + iDNar + " ]";
    }
    
}
