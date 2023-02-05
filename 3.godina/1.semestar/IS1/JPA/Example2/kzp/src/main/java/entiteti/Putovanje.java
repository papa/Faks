/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Stefan
 */
@Entity
@Table(name = "putovanje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Putovanje.findAll", query = "SELECT p FROM Putovanje p")
    , @NamedQuery(name = "Putovanje.findByIDPut", query = "SELECT p FROM Putovanje p WHERE p.iDPut = :iDPut")
    , @NamedQuery(name = "Putovanje.findByStatus", query = "SELECT p FROM Putovanje p WHERE p.status = :status")
    , @NamedQuery(name = "Putovanje.findByMestoOd", query = "SELECT p FROM Putovanje p WHERE p.mestoOd = :mestoOd")
    , @NamedQuery(name = "Putovanje.findByMestoDo", query = "SELECT p FROM Putovanje p WHERE p.mestoDo = :mestoDo")
    , @NamedQuery(name = "Putovanje.findByDuzina", query = "SELECT p FROM Putovanje p WHERE p.duzina = :duzina")})
public class Putovanje implements Serializable {

    @Size(max = 25)
    @Column(name = "MestoOd")
    private String mestoOd;
    @Size(max = 25)
    @Column(name = "MestoDo")
    private String mestoDo;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDPut")
    private Integer iDPut;
    @Column(name = "Status")
    private Character status;
    @Column(name = "Duzina")
    private Integer duzina;
    @ManyToMany(mappedBy = "putovanjeList")
    private List<Posiljka> posiljkaList;
    @ManyToMany(mappedBy = "putovanjeList")
    private List<Vozac> vozacList;
    @JoinColumn(name = "IDKam", referencedColumnName = "IDKam")
    @ManyToOne
    private Kamion iDKam;

    public Putovanje() {
    }

    public Putovanje(Integer iDPut) {
        this.iDPut = iDPut;
    }

    public Integer getIDPut() {
        return iDPut;
    }

    public void setIDPut(Integer iDPut) {
        this.iDPut = iDPut;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }


    public Integer getDuzina() {
        return duzina;
    }

    public void setDuzina(Integer duzina) {
        this.duzina = duzina;
    }

    @XmlTransient
    public List<Posiljka> getPosiljkaList() {
        return posiljkaList;
    }

    public void setPosiljkaList(List<Posiljka> posiljkaList) {
        this.posiljkaList = posiljkaList;
    }

    @XmlTransient
    public List<Vozac> getVozacList() {
        return vozacList;
    }

    public void setVozacList(List<Vozac> vozacList) {
        this.vozacList = vozacList;
    }

    public Kamion getIDKam() {
        return iDKam;
    }

    public void setIDKam(Kamion iDKam) {
        this.iDKam = iDKam;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDPut != null ? iDPut.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Putovanje)) {
            return false;
        }
        Putovanje other = (Putovanje) object;
        if ((this.iDPut == null && other.iDPut != null) || (this.iDPut != null && !this.iDPut.equals(other.iDPut))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Putovanje[ iDPut=" + iDPut + " ]";
    }

    public String getMestoOd() {
        return mestoOd;
    }

    public void setMestoOd(String mestoOd) {
        this.mestoOd = mestoOd;
    }

    public String getMestoDo() {
        return mestoDo;
    }

    public void setMestoDo(String mestoDo) {
        this.mestoDo = mestoDo;
    }
    
}
