/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slatkis;

import java.io.Serializable;

/**
 *
 * @author Jelena
 */
public class Slatkis implements Serializable{
    
    private String naziv;
    public Slatkis(String naziv)
    {
        this.naziv = naziv;
    }
    
    public String get()
    {
        return naziv;
    }
}
