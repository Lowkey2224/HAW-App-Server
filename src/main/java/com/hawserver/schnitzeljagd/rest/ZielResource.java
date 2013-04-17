package com.hawserver.schnitzeljagd.rest;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hawserver.schnitzeljagd.model.Ziel;
import com.hawserver.schnitzeljagd.model.Ziele;
import com.sun.jersey.api.NotFoundException;

@Path("ziel")
public class ZielResource {

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id){
        if(Ziele.zielExists(id)){
            Ziele.deleteZiel(id);
            return Response.ok().build();    
        }
        else
            throw new NotFoundException("No Ziel with id: "+id);
    }
    
    @POST
    @Consumes( MediaType.APPLICATION_JSON )
    @Produces( MediaType.APPLICATION_JSON )
    public Ziel add(Ziel z, @Context HttpServletResponse response){
        Ziele.addZiel(z);
        response.setStatus(Response.Status.CREATED.getStatusCode());
        return z;
    }
    
    @PUT
    @Consumes( MediaType.APPLICATION_JSON )
    @Path("{id}")
    public Response save(@PathParam("id") int id, Ziel z){
        if(Ziele.zielExists(id)){
            Ziele.updateZiel(z);
            return Response.ok().build();
        }
        else{
            Ziele.addZiel(z);
            return Response.status(Response.Status.CREATED).build();
        }
    }
    
    @GET
    @Path("all/{id}")
    @Produces( MediaType.APPLICATION_JSON)
    public List<Ziel> getAll(@PathParam("id") int id){
        return Ziele.getZieleForSchnitzeljagdId(id);
    }
    
    @GET
    @Produces( MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Ziel get(@PathParam("id") int id){
        if(Ziele.zielExists(id)){
            return Ziele.getZiel(id);    
        }
        else
            throw new NotFoundException("No Ziel with id: "+id);
    }
    
}
