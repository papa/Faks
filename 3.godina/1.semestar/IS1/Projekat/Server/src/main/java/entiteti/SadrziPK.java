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


public class SadrziPK implements Serializable {

    
    private int iDArt;
    
    private int iDKorpa;

    public SadrziPK() {
    }

    public SadrziPK(int iDArt, int iDKorpa) {
        this.iDArt = iDArt;
        this.iDKorpa = iDKorpa;
    }

    public int getIDArt() {
        return iDArt;
    }

    public void setIDArt(int iDArt) {
        this.iDArt = iDArt;
    }

    public int getIDKorpa() {
        return iDKorpa;
    }

    public void setIDKorpa(int iDKorpa) {
        this.iDKorpa = iDKorpa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) iDArt;
        hash += (int) iDKorpa;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SadrziPK)) {
            return false;
        }
        SadrziPK other = (SadrziPK) object;
        if (this.iDArt != other.iDArt) {
            return false;
        }
        if (this.iDKorpa != other.iDKorpa) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.SadrziPK[ iDArt=" + iDArt + ", iDKorpa=" + iDKorpa + " ]";
    }
    
}
