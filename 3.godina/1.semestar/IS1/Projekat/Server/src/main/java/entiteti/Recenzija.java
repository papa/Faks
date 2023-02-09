package entiteti;

import java.io.Serializable;

public class Recenzija implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer iDRec;
    private int iDKor;
    private int ocena;
    private String opis;
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
    
}
