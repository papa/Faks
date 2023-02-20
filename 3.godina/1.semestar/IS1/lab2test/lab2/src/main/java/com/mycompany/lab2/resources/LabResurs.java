package com.mycompany.lab2.resources;

import entiteti.Korisnik;
import entiteti.Prati;
import entiteti.Predmet;
import entiteti.Student;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import utility.Zahtev;

@Stateless
@Path("lab")
public class LabResurs {
    
    @PersistenceContext(unitName = "fakultetPU")
    EntityManager em;
    
    @POST
    @Path("prijava/{idPredmeta}")
    public Response prijaviPredmet(@PathParam("idPredmeta")int idPredmeta, @Context HttpHeaders headers){
        
        List<String> hs = headers.getRequestHeader("Authorization");
        Korisnik k = null;
        Student s = null;
        int idKor = 0;
        if(hs != null && hs.size() > 0)
        {
            String authHeaderValue = hs.get(0);
            String decodedAuthHeaderValue = new String(Base64.getDecoder().decode(authHeaderValue.replaceFirst("Basic ", "")),StandardCharsets.UTF_8);
            StringTokenizer stringTokenizer = new StringTokenizer(decodedAuthHeaderValue, ":");
            String username = stringTokenizer.nextToken();
            
            List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByKorisnickoIme").setParameter("korisnickoIme", username).getResultList();
            k = korisnici.get(0);
            List<Student> studenti = em.createNamedQuery("Student.findByKorisnikId").setParameter("korisnikId", k.getId()).getResultList();
            s = studenti.get(0);
            idKor = k.getId();
        }
        
        Predmet predmet = em.find(Predmet.class, idPredmeta);
        
        if(predmet == null)
        {
            Response response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Ne postoji predmet").build();
            return response;
        }
        
        Prati prati = new Prati();
        prati.setId(0);
        prati.setPredmetId(predmet);
        prati.setStudentKorisnikId(s);
        
        em.persist(prati);
        //em.flush();
        
        Response response = Response.status(Response.Status.OK).entity("Prijavljen predmet").build();
        return response;
    }
    
    
    @POST
    @Path("admin")
    public Response napraviAdmina(Zahtev zahtev){       
        return Response.status(Response.Status.OK).entity(zahtev.imeKor + " " + zahtev.sifra).build();
    }
    
    @POST
    @Path("z")
    public Response getget(String s){       
        return Response.status(Response.Status.OK).entity(s).build();
    }
    
    @POST
    @Path("forma")
    public Response kurcina(@FormParam("kor_ime")String kor_ime){       
        return Response.status(Response.Status.OK).entity(kor_ime).build();
    }
    
    @GET
    @Path("xml")
    public Response kurcina2(){       
        Zahtev z = new Zahtev();
        z.sifra = "sifra";
        z.imeKor = "imeKor";
        return Response.status(Response.Status.OK).entity(z).build();
    }
    
    @GET
    @Path("put")
    public Response kurcina3(String x){
        return Response.status(Response.Status.OK).entity(x).build();
    }
}
