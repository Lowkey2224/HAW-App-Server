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

import com.hawserver.schnitzeljagd.model.Schnitzeljagd;
import com.hawserver.schnitzeljagd.model.Schnitzeljagden;
import com.sun.jersey.api.NotFoundException;

@Path("schnitzeljagd")
public class SchnitzeljagdResource {

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id){
        if(Schnitzeljagden.schnitzeljagdExists(id)){
            Schnitzeljagden.deleteSchnitzeljagd(id);
            return Response.ok().build();    
        }
        else
            throw new NotFoundException("No Schnitzeljagd with id: "+id);
    }
    
    @POST
    @Consumes( MediaType.APPLICATION_JSON )
    @Produces( MediaType.APPLICATION_JSON )
    public Schnitzeljagd add(Schnitzeljagd sj, @Context HttpServletResponse response){
        Schnitzeljagden.addSchnitzeljagd(sj);
        response.setStatus(Response.Status.CREATED.getStatusCode());
        return sj;
    }
    
    @PUT
    @Consumes( MediaType.APPLICATION_JSON )
    @Path("{id}")
    public Response save(@PathParam("id") int id, Schnitzeljagd sj){
        if(Schnitzeljagden.schnitzeljagdExists(id)){
            Schnitzeljagden.updateSchnitzeljagd(sj);
            return Response.ok().build();
        }
        else{
            Schnitzeljagden.addSchnitzeljagd(sj);
            return Response.status(Response.Status.CREATED).build();
        }
    }
    
    @GET
    @Produces( MediaType.APPLICATION_JSON)
    public List<Schnitzeljagd> get(){
        return Schnitzeljagden.getSchnitzeljagden();
    }
    
    @GET
    @Produces( MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Schnitzeljagd get(@PathParam("id") int id){
        if(Schnitzeljagden.schnitzeljagdExists(id)){
            return Schnitzeljagden.getSchnitzeljagd(id);    
        }
        else
            throw new NotFoundException("No Schnitzeljagd with id: "+id);
    }

}
