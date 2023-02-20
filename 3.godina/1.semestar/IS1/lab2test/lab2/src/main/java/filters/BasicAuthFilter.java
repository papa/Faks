/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import entiteti.Admin;
import entiteti.Korisnik;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Stefan
 */
@Provider
public class BasicAuthFilter implements ContainerRequestFilter{

    @PersistenceContext(unitName = "fakultetPU")
    EntityManager em;
    
    //izmeniti telo ove metode
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        List<String> authHeaderValues = requestContext.getHeaders().get("Authorization");
        
        if(authHeaderValues != null && authHeaderValues.size() > 0){
            String authHeaderValue = authHeaderValues.get(0);
            String decodedAuthHeaderValue = new String(Base64.getDecoder().decode(authHeaderValue.replaceFirst("Basic ", "")),StandardCharsets.UTF_8);
            StringTokenizer stringTokenizer = new StringTokenizer(decodedAuthHeaderValue, ":");
            String username = stringTokenizer.nextToken();
            String password = stringTokenizer.nextToken();
            
            List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByKorisnickoIme").setParameter("korisnickoIme", username).getResultList();
            if(korisnici.isEmpty())
            {
                Response response = Response.status(Response.Status.UNAUTHORIZED).entity("Nema korisnika sa tim username").build();
                requestContext.abortWith(response);
                return;
            }
            
            Korisnik k = korisnici.get(0);
            if(!k.getSifra().equals(password))
            {
                Response response = Response.status(Response.Status.UNAUTHORIZED).entity("Sifra nije dobra").build();
                requestContext.abortWith(response);
                return;
            }
            
            String method = requestContext.getMethod();
            UriInfo uriInfo = requestContext.getUriInfo();
            String uriPath = requestContext.getUriInfo().getPath();
            List<PathSegment> pathSegments = uriInfo.getPathSegments();
            String endPointName = pathSegments.get(0).getPath();
            String pathSegment1 = null;
            if(pathSegments.size() > 1)
                pathSegment1 = pathSegments.get(1).getPath();
            
            Admin a = k.getAdmin();
            
//            if(a != null)
//            {
//                Response response = Response.status(Response.Status.UNAUTHORIZED).entity("Jeste admin").build();
//                requestContext.abortWith(response);
//                return;
//            }
//            Response response = Response.status(Response.Status.UNAUTHORIZED).entity("Nije admin").build();
//            requestContext.abortWith(response);
            return;
           
        }
        
        Response response = Response.status(Response.Status.UNAUTHORIZED).entity("Posaljite kredencijale.").build();
        requestContext.abortWith(response);
        return;
    }
    
}