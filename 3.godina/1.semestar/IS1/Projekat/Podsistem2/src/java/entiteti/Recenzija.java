/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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

/**
 *
 * @author Jelena
 */
@Entity
@Table(name = "recenzija")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recenzija.findAll", query = "SELECT r FROM Recenzija r"),
    @NamedQuery(name = "Recenzija.findByIDRec", query = "SELECT r FROM Recenzija r WHERE r.iDRec = :iDRec"),
    @NamedQuery(name = "Recenzija.findByIDKor", query = "SELECT r FROM Recenzija r WHERE r.iDKor = :iDKor"),
    @NamedQuery(name = "Recenzija.findByOcena", query = "SELECT r FROM Recenzija r WHERE r.ocena = :ocena"),
    @NamedQuery(name = "Recenzija.findByOpis", query = "SELECT r FROM Recenzija r WHERE r.opis = :opis")})
public class Recenzija implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "IDKor")
    private int iDKor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Ocena")
    private int ocena;
    @Size(max = 200)
    @Column(name = "Opis")
    private String opis;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDRec")
    private Integer iDRec;
    @JoinColumn(name = "IDArt", referencedColumnName = "IDArt")
    @ManyToOne(optional = false)
    private Artikl iDArt;

    public Recenzija() {
    }

    public Recenzija(Integer iDRec) {
        this.iDRec = iDRec;
    }

    public Recenzija(Integer iDRec, int iDKor, int ocena) {
        this.iDRec = iDRec;
        this.iDKor = iDKor;
        this.ocena = ocena;
    }

    public Integer getIDRec() {
        return iDRec;
    }

    public void setIDRec(Integer iDRec) {
        this.iDRec = iDRec;
    }


    public Artikl getIDArt() {
        return iDArt;
    }

    public void setIDArt(Artikl iDArt) {
        this.iDArt = iDArt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDRec != null ? iDRec.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recenzija)) {
            return false;
        }
        Recenzija other = (Recenzija) object;
        if ((this.iDRec == null && other.iDRec != null) || (this.iDRec != null && !this.iDRec.equals(other.iDRec))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Recenzija[ iDRec=" + iDRec + " ]";
    }

    public int getIDKor() {
        return iDKor;
    }

    public void setIDKor(int iDKor) {
        this.iDKor = iDKor;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }
    
}
