/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;
import java.io.Serializable;

/**
 *
 * @author Jelena
 */
public class Odgovor implements Serializable{
    private int ishod;
    private String poruka;
    private Object objekat;
    public Odgovor(int ishod, String poruka)
    {
        this.ishod = ishod;
        this.poruka = poruka;
        this.objekat = null;
    }
    public Odgovor(int ishod, String poruka, Object objekat)
    {
        this.ishod = ishod;
        this.poruka = poruka;
        this.objekat = objekat;
    }
    public Object getObjekat() {return objekat;}
    public String getPoruka() {return poruka;}
    public int getIshod() {return ishod;}
}
