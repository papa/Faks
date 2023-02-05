/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import entiteti.Role;
import entiteti.User;
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

@Provider
public class BasicAuthFilter implements ContainerRequestFilter{

    @PersistenceContext(unitName = "my_persistence_unit")
    EntityManager em;
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        List<String> authHeaderValues = requestContext.getHeaders().get("Authorization");
        
        if(authHeaderValues != null && authHeaderValues.size() > 0){
            String authHeaderValue = authHeaderValues.get(0);
            String decodedAuthHeaderValue = new String(Base64.getDecoder().decode(authHeaderValue.replaceFirst("Basic ", "")),StandardCharsets.UTF_8);
            StringTokenizer stringTokenizer = new StringTokenizer(decodedAuthHeaderValue, ":");
            String username = stringTokenizer.nextToken();
            String password = stringTokenizer.nextToken();
            
            List<User> users = em.createNamedQuery("User.findByUsername", User.class).setParameter("username", username).getResultList();
            
            if(users.size() != 1){
                Response response = Response.status(Response.Status.UNAUTHORIZED).entity("Korisnicko ime ili sifra nije ispravno.").build();
                requestContext.abortWith(response);
                return;
            }
            
            User user = users.get(0);
            
            if(!user.getPassword().equals(password)){
                Response response = Response.status(Response.Status.UNAUTHORIZED).entity("Korisnicko ime ili sifra nije ispravno.").build();
                requestContext.abortWith(response);
                return;
            }
            
            Role role = user.getIdrole();
            
            String method = requestContext.getMethod();
            UriInfo uriInfo = requestContext.getUriInfo();
            String uriPath = requestContext.getUriInfo().getPath();
            List<PathSegment> pathSegments = uriInfo.getPathSegments();
            String endpointName = pathSegments.get(0).getPath();
            String pathSegment1 = null;
            if(pathSegments.size() > 1)
                pathSegment1 = pathSegments.get(1).getPath();
            
            if(role.getRole().equals("admin")) return;
            if(role.getRole().equals("privileged") && !endpointName.equals("users") && !endpointName.equals("roles")) return;
            if(role.getRole().equals("unprivileged") && (!endpointName.equals("users") || ( endpointName.equals("users") && pathSegment1 != null && pathSegment1.equals("myinfo"))) && !endpointName.equals("roles") && "GET".equals(method)) return;
            
            Response response = Response.status(Response.Status.UNAUTHORIZED).entity("Nemate privilegije.").build();
            requestContext.abortWith(response);
            return;
        }
        
        Response response = Response.status(Response.Status.UNAUTHORIZED).entity("Posaljite kredencijale.").build();
        requestContext.abortWith(response);
        return;
    }
    
}
