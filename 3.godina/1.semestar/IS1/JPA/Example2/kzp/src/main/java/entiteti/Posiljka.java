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
import javax.persistence.JoinTable;
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
@Table(name = "posiljka")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Posiljka.findAll", query = "SELECT p FROM Posiljka p")
    , @NamedQuery(name = "Posiljka.findByIDPos", query = "SELECT p FROM Posiljka p WHERE p.iDPos = :iDPos")
    , @NamedQuery(name = "Posiljka.findByTezina", query = "SELECT p FROM Posiljka p WHERE p.tezina = :tezina")
    , @NamedQuery(name = "Posiljka.findByVrednost", query = "SELECT p FROM Posiljka p WHERE p.vrednost = :vrednost")
    , @NamedQuery(name = "Posiljka.findByMestoOd", query = "SELECT p FROM Posiljka p WHERE p.mestoOd = :mestoOd")
    , @NamedQuery(name = "Posiljka.findByMestoDo", query = "SELECT p FROM Posiljka p WHERE p.mestoDo = :mestoDo")})
public class Posiljka implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDPos")
    private Integer iDPos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Tezina")
    private int tezina;
    @Column(name = "Vrednost")
    private Integer vrednost;
    @Size(max = 25)
    @Column(name = "MestoOd")
    private String mestoOd;
    @Size(max = 25)
    @Column(name = "MestoDo")
    private String mestoDo;
    @JoinTable(name = "seprevozi", joinColumns = {
        @JoinColumn(name = "IDPos", referencedColumnName = "IDPos")}, inverseJoinColumns = {
        @JoinColumn(name = "IDPut", referencedColumnName = "IDPut")})
    @ManyToMany
    private List<Putovanje> putovanjeList;
    @JoinColumn(name = "IDFir", referencedColumnName = "IDFir")
    @ManyToOne
    private Firma iDFir;

    public Posiljka() {
    }

    public Posiljka(Integer iDPos) {
        this.iDPos = iDPos;
    }

    public Posiljka(Integer iDPos, int tezina) {
        this.iDPos = iDPos;
        this.tezina = tezina;
    }

    public Integer getIDPos() {
        return iDPos;
    }

    public void setIDPos(Integer iDPos) {
        this.iDPos = iDPos;
    }

    public int getTezina() {
        return tezina;
    }

    public void setTezina(int tezina) {
        this.tezina = tezina;
    }

    public Integer getVrednost() {
        return vrednost;
    }

    public void setVrednost(Integer vrednost) {
        this.vrednost = vrednost;
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

    @XmlTransient
    public List<Putovanje> getPutovanjeList() {
        return putovanjeList;
    }

    public void setPutovanjeList(List<Putovanje> putovanjeList) {
        this.putovanjeList = putovanjeList;
    }

    public Firma getIDFir() {
        return iDFir;
    }

    public void setIDFir(Firma iDFir) {
        this.iDFir = iDFir;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDPos != null ? iDPos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Posiljka)) {
            return false;
        }
        Posiljka other = (Posiljka) object;
        if ((this.iDPos == null && other.iDPos != null) || (this.iDPos != null && !this.iDPos.equals(other.iDPos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Posiljka[ iDPos=" + iDPos + " ]";
    }
    
}
