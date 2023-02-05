/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.kzp.resources;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import paramTypes.VremenskiPeriod;
import entiteti.Role;
import entiteti.User;

/**
 *
 * @author Stefan
 */
@Path("users")
@Stateless
public class Users {
    
    @PersistenceContext(unitName = "my_persistence_unit")
    EntityManager em;
    
    @GET
    public List<User> getUsers(){
        return em.createNamedQuery("User.findAll", User.class).getResultList();
    }
    
    @GET
    @Path("myinfo/myrole")
    public Role getCurrentUserRole(@Context HttpHeaders httpHeaders){
        List<String> authHeaderValues = httpHeaders.getRequestHeader("Authorization");
        
        if(authHeaderValues != null && authHeaderValues.size() > 0){
            String authHeaderValue = authHeaderValues.get(0);
            String decodedAuthHeaderValue = new String(Base64.getDecoder().decode(authHeaderValue.replaceFirst("Basic ", "")),StandardCharsets.UTF_8);
            StringTokenizer stringTokenizer = new StringTokenizer(decodedAuthHeaderValue, ":");
            String username = stringTokenizer.nextToken();
            String password = stringTokenizer.nextToken();
            
            User user = em.createNamedQuery("User.findByUsername", User.class).setParameter("username", username).getSingleResult();
            
            return user.getIdrole();
        }
        
        return null;
    }
    
    @POST
    public void createUser(@FormParam("username") String userName, @FormParam("password") String password, 
            @FormParam("ime") String ime, @FormParam("prezime") String prezime){
        User user = new User();
        user.setUsername(userName);
        user.setPassword(password);
        user.setIme(ime);
        user.setPrezime(prezime);
        user.setIdrole(em.find(Role.class, 3));
        em.persist(user);
    }
    
    @PUT
    @Path("{idUsr}")
    public void changePassword(@PathParam("idUsr") int idUsr, String password){
        User user = em.find(User.class, idUsr);
        if(user != null) user.setPassword(password);
    }
    
    @DELETE
    @Path("{idUsr}")
    public void deleteUser(@PathParam("idUsr") int idUsr){
        User user = em.find(User.class, idUsr);
        if(user != null) em.remove(user);
    }
    
    // Moramo definisati ParamConverter za tip VremenskiPeriod
    @GET
    @Path("{datum}")
    public List<User> dohvatiUsereKreiraneUPeriodu(@PathParam("datum") VremenskiPeriod period){
        return
            em.createQuery("SELECT u from User u WHERE u.vremekreiranja BETWEEN :pocetak and :kraj", User.class)
                .setParameter("pocetak", period.getDatumPocetka())
                .setParameter("kraj", period.getDatumKraja())
            .getResultList();
    }
    
    // JAX-RS nema MessageBodyWriter koji Date konvertuje u TEXT/PLAIN. Zato moramo napisati svoj MessageBodyWriter.
    // JAX-RS ima MessageBodyWriter koji Date konvertuje u TEXT/XML.
    @GET
    @Path("{idUsr}")
    @Produces(MediaType.TEXT_PLAIN)
    public Date dohvatiDatumRodjenjaZaKorisnika(@PathParam("idUsr") int idUsr){
        User user = em.find(User.class, idUsr);
        if(user != null)
            return user.getDatumRodjenja();
        
        return null;
    }
    
    @POST
    @Path("slika/{idUsr}")
    public void postaviSliku(@PathParam("idUsr") int idUsr, byte[] slika){
        User user = em.find(User.class, idUsr);
        
        if(user != null){
            user.setSlika(slika);
        }
    }
    
    @DELETE
    @Path("slika/{idUsr}")
    public void obrisiSliku(@PathParam("idUsr") int idUsr){
        User user = em.find(User.class, idUsr);
        
        if(user != null){
            user.setSlika(null);
        }
    }
    
    @GET
    @Path("slika/{idUsr}")
    @Produces("image/jpeg")
    public byte[] dohvatiSliku(@PathParam("idUsr") int idUsr){
        User user = em.find(User.class, idUsr);
        
        if(user != null){
            return user.getSlika();
        }
        
        return null;
    }
}
