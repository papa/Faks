/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paramTypes;

import java.util.Date;

public class VremenskiPeriod {
    private Date datumPocetka;
    private Date datumKraja;

    public Date getDatumPocetka() {
        return datumPocetka;
    }

    public void setDatumPocetka(Date datumPocetka) {
        this.datumPocetka = datumPocetka;
    }

    public Date getDatumKraja() {
        return datumKraja;
    }

    public void setDatumKraja(Date datumKraja) {
        this.datumKraja = datumKraja;
    }

    @Override
    public String toString() {
        return "VremenskiPeriod{" + "datumPocetka=" + datumPocetka + ", datumKraja=" + datumKraja + '}';
    } 
}
