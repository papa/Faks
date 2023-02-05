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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jelena
 */
@Entity
@Table(name = "korpa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Korpa.findAll", query = "SELECT k FROM Korpa k"),
    @NamedQuery(name = "Korpa.findByIDKorpa", query = "SELECT k FROM Korpa k WHERE k.iDKorpa = :iDKorpa"),
    @NamedQuery(name = "Korpa.findByUkupnaCena", query = "SELECT k FROM Korpa k WHERE k.ukupnaCena = :ukupnaCena"),
    @NamedQuery(name = "Korpa.findByIDKor", query = "SELECT k FROM Korpa k WHERE k.iDKor = :iDKor")})
public class Korpa implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "UkupnaCena")
    private int ukupnaCena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDKor")
    private int iDKor;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDKorpa")
    private Integer iDKorpa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "korpa")
    private List<Sadrzi> sadrziList;

    public Korpa() {
    }

    public Korpa(Integer iDKorpa) {
        this.iDKorpa = iDKorpa;
    }

    public Korpa(Integer iDKorpa, int ukupnaCena, int iDKor) {
        this.iDKorpa = iDKorpa;
        this.ukupnaCena = ukupnaCena;
        this.iDKor = iDKor;
    }

    public Integer getIDKorpa() {
        return iDKorpa;
    }

    public void setIDKorpa(Integer iDKorpa) {
        this.iDKorpa = iDKorpa;
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
        hash += (iDKorpa != null ? iDKorpa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Korpa)) {
            return false;
        }
        Korpa other = (Korpa) object;
        if ((this.iDKorpa == null && other.iDKorpa != null) || (this.iDKorpa != null && !this.iDKorpa.equals(other.iDKorpa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Korpa[ iDKorpa=" + iDKorpa + " ]";
    }

    public int getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(int ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }

    public int getIDKor() {
        return iDKor;
    }

    public void setIDKor(int iDKor) {
        this.iDKor = iDKor;
    }
    
}
