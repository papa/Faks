package utility;

import java.io.Serializable;
import java.util.ArrayList;

public class Zahtev implements Serializable
{
    private int brZahteva;
    private int brParam = 0;
    private ArrayList<Object> parametri = new ArrayList<Object>();
    
    public int getBrParam(){return brParam;}
    public int getBrZahteva() {return brZahteva;}
    public ArrayList<Object> getParametri() {return parametri;}
    
    public void dodajParam(Object o) {parametri.add(o);brParam = parametri.size();}
    public void postaviBrZahteva(int x) {brZahteva = x;}
}