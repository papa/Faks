/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.kzp.resources;
import entiteti.Kamion;
import entiteti.Posiljka;
import entiteti.Putovanje;
import entiteti.Vozac;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import resursi.PutovanjeInfo;
import entiteti.Firma;

@Path("posiljke")
@Stateless
public class Posiljke {
    
    @PersistenceContext(unitName = "my_persistence_unit")
    EntityManager em;
    
    @GET
    @Path("putovanjeInfo/{idPos}")
    public Response getPutovanjeInfo(@PathParam("idPos") int idPos){
        Posiljka posiljka = em.find(Posiljka.class, idPos);
        if(posiljka == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Ne postoji posiljka").build();
        if(posiljka.getPutovanjeList().isEmpty())
            return Response.status(Response.Status.CONFLICT).entity("Posiljka se jos uvek ne prevozi").build();

        Putovanje putovanje = posiljka.getPutovanjeList().get(0);
        
        List<Vozac> vozaci = putovanje.getVozacList();
        List<String> imenaVozaca = new ArrayList<>();
        
        for (Vozac vozac : vozaci) {
            imenaVozaca.add(vozac.getZaposlen().getImePrezime());
        }
        
        PutovanjeInfo putovanjeInfo = new PutovanjeInfo();
        putovanjeInfo.setMarkaKamiona(putovanje.getIDKam().getMarka());
        putovanjeInfo.setMestoDo(putovanje.getMestoDo());
        putovanjeInfo.setMestoOd(putovanje.getMestoOd());
        putovanjeInfo.setStatus(putovanje.getStatus());
        putovanjeInfo.setImenaVozaca(imenaVozaca);
        
        return Response.status(Response.Status.OK).entity(putovanjeInfo).build();
    }
    
    @DELETE
    @Path("{idPos}")
    public Response deletePosiljka(@PathParam("idPos") int idPos){
        Posiljka posiljka = em.find(Posiljka.class, idPos);
        if(posiljka.getPutovanjeList().size() > 0)
            return Response.status(Response.Status.CONFLICT).entity("Ne moze se obrisati. Posiljka je vec na putu").build();
        
        em.remove(posiljka);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
    
    @GET
    public List<Posiljka> get(){
        return em.createNamedQuery("Posiljka.findAll").getResultList();
    }
    
    @POST
    public void createPosiljka(Posiljka posiljka){
        posiljka.setIDFir(em.find(Firma.class, posiljka.getIDFir().getIDFir()));
        posiljka.setIDPos(em.createQuery("select max(p.iDPos) from Posiljka p", Integer.class).getSingleResult() + 1);
        em.persist(posiljka);
//        em.flush();
        
        List<Posiljka> posiljke = em.createQuery("SELECT p from Posiljka p where SIZE(p.putovanjeList) = 0 and p.mestoOd = :mestoOd and p.mestoDo = :mestoDo", Posiljka.class).setParameter("mestoOd", posiljka.getMestoOd()).setParameter("mestoDo", posiljka.getMestoDo()).getResultList();
        Long tezinaPosiljki = em.createQuery("SELECT SUM(p.tezina) from Posiljka p where SIZE(p.putovanjeList) = 0 and p.mestoOd = :mestoOd and p.mestoDo = :mestoDo", Long.class).setParameter("mestoOd", posiljka.getMestoOd()).setParameter("mestoDo", posiljka.getMestoDo()).getSingleResult();
        
        if(posiljke.size() >= 3 || tezinaPosiljki > 2000) pocniPrevozPosiljki(posiljke);
    }
    
    private void pocniPrevozPosiljki(List<Posiljka> posiljke){
        String mestoOd = posiljke.get(0).getMestoOd();
        String mestoDo = posiljke.get(0).getMestoDo();
        
        Kamion kamion = selektujKamionZaPrevoz();
        if (kamion == null)
            return;
        
        List<Vozac> vozaci = selektujVozaceZaPrevoz(posiljke);
        if(vozaci == null || vozaci.isEmpty())
            return;
        
        Putovanje putovanje = new Putovanje();
        putovanje.setIDPut(em.createQuery("select max(p.iDPut) + 1 from Putovanje p", Integer.class).getSingleResult());
        putovanje.setMestoOd(mestoOd);
        putovanje.setMestoDo(mestoDo);
        putovanje.setStatus(new Character('P'));
        putovanje.setDuzina(0);
        //putovanje.setPosiljkaList(posiljke);
        putovanje.setVozacList(vozaci);
        putovanje.setIDKam(kamion);
        
        //mora se uraditi jer se sa putovanje.setPosiljkaList vrsi izmena inverznog polja koje se ne perzistira
        for (Posiljka p : posiljke) {
            List<Putovanje> putovanja = new ArrayList<>();
            putovanja.add(putovanje);
            p.setPutovanjeList(putovanja);
        }
        
        for (Vozac vozac : vozaci) {
            List<Putovanje> putovanja = new ArrayList<>();
            putovanja.add(putovanje);
            vozac.setPutovanjeList(putovanja);
        }
        
        em.persist(putovanje);
    }

    private List<Vozac> selektujVozaceZaPrevoz(List<Posiljka> posiljke) {
        boolean potrebanDrugiVozac = false;
        for (Posiljka posiljka : posiljke) {
            if(posiljka.getTezina() > 1500){
                potrebanDrugiVozac = true;
                break;
            }
        }
        
        List<Vozac> vozaci = em.createQuery("select v from Vozac v where SIZE(v.putovanjeList) = (select MIN(SIZE(v2.putovanjeList)) from Vozac v2) ORDER BY v.iDZap", Vozac.class).getResultList();
        
        if(vozaci.isEmpty())
            return null;
        
        List<Vozac> selektovaniVozaci = new ArrayList<>();
        selektovaniVozaci.add(vozaci.get(0));
        if(potrebanDrugiVozac && vozaci.size() >= 2) selektovaniVozaci.add(vozaci.get(1));
        
        return selektovaniVozaci;
    }

    private Kamion selektujKamionZaPrevoz() {
        List<Kamion> kamioni = em.createQuery("select k from Kamion k where SIZE(k.putovanjeList) = (select MIN(SIZE(k2.putovanjeList)) from Kamion k2) ORDER BY k.iDKam", Kamion.class).getResultList();
        if(kamioni.isEmpty())
            return null;
        
        return kamioni.get(0);
    }
}
