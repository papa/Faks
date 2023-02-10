/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package applicationclient2;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jelena
 */
@Entity
@Table(name = "transakcija")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transakcija.findAll", query = "SELECT t FROM Transakcija t"),
    @NamedQuery(name = "Transakcija.findByIDTrans", query = "SELECT t FROM Transakcija t WHERE t.iDTrans = :iDTrans"),
    @NamedQuery(name = "Transakcija.findBySuma", query = "SELECT t FROM Transakcija t WHERE t.suma = :suma"),
    @NamedQuery(name = "Transakcija.findByVremePlacanja", query = "SELECT t FROM Transakcija t WHERE t.vremePlacanja = :vremePlacanja")})
public class Transakcija implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDTrans")
    private Integer iDTrans;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Suma")
    private double suma;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VremePlacanja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vremePlacanja;
    @JoinColumn(name = "IDNar", referencedColumnName = "IDNar")
    @ManyToOne(optional = false)
    private Narudzbina iDNar;

    public Transakcija() {
    }

    public Transakcija(Integer iDTrans) {
        this.iDTrans = iDTrans;
    }

    public Transakcija(Integer iDTrans, double suma, Date vremePlacanja) {
        this.iDTrans = iDTrans;
        this.suma = suma;
        this.vremePlacanja = vremePlacanja;
    }

    public Integer getIDTrans() {
        return iDTrans;
    }

    public void setIDTrans(Integer iDTrans) {
        this.iDTrans = iDTrans;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    public Date getVremePlacanja() {
        return vremePlacanja;
    }

    public void setVremePlacanja(Date vremePlacanja) {
        this.vremePlacanja = vremePlacanja;
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
        hash += (iDTrans != null ? iDTrans.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transakcija)) {
            return false;
        }
        Transakcija other = (Transakcija) object;
        if ((this.iDTrans == null && other.iDTrans != null) || (this.iDTrans != null && !this.iDTrans.equals(other.iDTrans))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "applicationclient2.Transakcija[ iDTrans=" + iDTrans + " ]";
    }
    
}
