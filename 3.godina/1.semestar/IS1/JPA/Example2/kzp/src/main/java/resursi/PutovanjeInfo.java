/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resursi;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "putovanjeInfo")
public class PutovanjeInfo {
    private Character status;
    private String mestoOd;
    private String mestoDo;
    private String markaKamiona;
    private List<String> imenaVozaca;

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
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

    public String getMarkaKamiona() {
        return markaKamiona;
    }

    public void setMarkaKamiona(String markaKamiona) {
        this.markaKamiona = markaKamiona;
    }

    @XmlElementWrapper(name = "vozaci")
    @XmlElement(name = "vozac")
    public List<String> getImenaVozaca() {
        return imenaVozaca;
    }

    public void setImenaVozaca(List<String> imenaVozaca) {
        this.imenaVozaca = imenaVozaca;
    }
    
    
}
