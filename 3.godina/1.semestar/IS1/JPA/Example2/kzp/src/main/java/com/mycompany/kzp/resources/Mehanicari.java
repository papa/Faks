/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.kzp.resources;

import java.util.List;
import entiteti.Mehanicar;
import entiteti.Zaposlen;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

@Path("mehanicari")
@Stateless
public class Mehanicari {
    
    @PersistenceContext(unitName = "my_persistence_unit")
    EntityManager em;
    
    @POST
    public Response createMehanicar(Mehanicar mehanicar){
        try{
            Zaposlen zaposleni = em.find(Zaposlen.class, mehanicar.getIDZap());
            if(zaposleni != null)
                mehanicar.setZaposlen(zaposleni);
            em.persist(mehanicar);
        }
        catch(EntityExistsException e){
            Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Mehanicar vec postoji").build();
        }
        return Response.status(Response.Status.CREATED).entity("Mehanicar je kreiran").build();
    }
    
    @DELETE
    @Path("{idMeh}")
    public Response deleteMehanicar(@PathParam("idMeh") int idMeh){
        Mehanicar mehanicar = em.find(Mehanicar.class, idMeh);
        
        if(mehanicar == null)
            return Response.status(Response.Status.NO_CONTENT).build();
        
        if(!mehanicar.getPopravljaList().isEmpty())
            return Response.status(Response.Status.CONFLICT).entity("Mehanicaru prvo obrisati popravke").build();

        em.remove(mehanicar);
        
        return Response.status(Response.Status.NO_CONTENT).build();
    }
    
    @GET
    public Response getMehanicari(@QueryParam("specijalnost") String specijalnost, @QueryParam("invert") Boolean invert){
        
        String query = "select m from Mehanicar m";
        
        if(specijalnost != null){
            if(invert != null && invert == true)
                query += " where m.specijalnost != :specijalnost";
            else query += " where m.specijalnost = :specijalnost";
        }
        
        TypedQuery<Mehanicar> tq = em.createQuery(query, Mehanicar.class);
        
        if(specijalnost != null) tq.setParameter("specijalnost", specijalnost);
        
        List<Mehanicar> mehanicari = tq.getResultList();
        
        if(mehanicari != null && !mehanicari.isEmpty())
            return Response.status(Response.Status.OK).entity(new GenericEntity<List<Mehanicar>>(mehanicari){}).build();
        
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
