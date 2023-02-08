package utility;
import java.io.Serializable;

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
