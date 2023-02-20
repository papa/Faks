/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "korisnik")
public class Zahtev {
    public String imeKor;
    public String sifra;
}
