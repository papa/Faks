/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.kzp.resources;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import entiteti.Role;
import entiteti.User;

/**
 *
 * @author Stefan
 */
@Path("roles")
@Stateless
public class Roles {
    @PersistenceContext(unitName = "my_persistence_unit")
    EntityManager em;
    
    @GET
    public List<Role> getRoles(){
        return em.createNamedQuery("Role.findAll", Role.class).getResultList();
    }
    
    @POST
    public void createRole(Role role){
        em.persist(role);
    }
    
    @DELETE
    @Path("{idRole}")
    public void deleteRole(@PathParam("idRole") int idRole){
        Role role = em.find(Role.class, idRole);
        em.remove(role);
    }
}
