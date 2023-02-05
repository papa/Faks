/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Stefan
 */
@Embeddable
public class PopravljaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "IDZap")
    private int iDZap;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDKam")
    private int iDKam;

    public PopravljaPK() {
    }

    public PopravljaPK(int iDZap, int iDKam) {
        this.iDZap = iDZap;
        this.iDKam = iDKam;
    }

    public int getIDZap() {
        return iDZap;
    }

    public void setIDZap(int iDZap) {
        this.iDZap = iDZap;
    }

    public int getIDKam() {
        return iDKam;
    }

    public void setIDKam(int iDKam) {
        this.iDKam = iDKam;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) iDZap;
        hash += (int) iDKam;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PopravljaPK)) {
            return false;
        }
        PopravljaPK other = (PopravljaPK) object;
        if (this.iDZap != other.iDZap) {
            return false;
        }
        if (this.iDKam != other.iDKam) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.PopravljaPK[ iDZap=" + iDZap + ", iDKam=" + iDKam + " ]";
    }
    
}
