/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cst8218.patrick.slider.service;

import cst8218.patrick.slider.game.Slider;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author gemmx
 */
@Stateless
@Path("cst8218.patrick.slider.game.slider")
public class SliderFacadeREST extends AbstractFacade<Slider> {
    
    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public SliderFacadeREST() {
        super(Slider.class);
    }

    //Update slider Method to existingSlider from a newSlider
    public void updateSlider(Slider existingSlider, Slider newSlider) {
        existingSlider.setX(newSlider.getX());
        existingSlider.setY(newSlider.getY());
        existingSlider.setMaxTravel(newSlider.getMaxTravel());
        existingSlider.setSize(newSlider.getSize());
        existingSlider.setMvtDirection(newSlider.getMvtDirection());
        existingSlider.setDirChangeCount(newSlider.getDirChangeCount());
        existingSlider.setCurrentTravel(newSlider.getCurrentTravel());
    }
    
    //Create slider POST request to check if the id is not already created, if so make a new one. 
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createSlider(Slider entity) {
        if (entity.getId()==null) {
            super.create(entity);
            return Response.status(Response.Status.CREATED).entity(entity).build();
        } else {
            Slider existingSlider = super.find(entity.getId());
            if (existingSlider != null) {
                updateSlider(existingSlider, entity);
                super.edit(existingSlider);
                return Response.ok(existingSlider).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Slider with this ID does not exist").build();
            }
        }
    }
    
    //Check if the post command has mismatching Id and URL and have a catch all if the id does not exist
    @POST
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response editedId(@PathParam("id") Long id, Slider entity) {
        if (!id.equals(entity.getId())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Id from the URL does not match the body").build();
        }
        Slider existingSlider = super.find(id);
        if (existingSlider != null) {
            updateSlider(existingSlider, entity);
            super.edit(entity);
            return Response.ok(entity).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Slider with Id does not exist").build();
        }
    }
    
    //Check if the post command has mismatching Id and URL and have a catch all if the id does not exist
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Long id, Slider entity) {
        if (!id.equals(entity.getId())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Id from the URL does not match the body").build();
        }
        Slider existingSlider = super.find(id);
        if (existingSlider != null) {
            super.edit(entity);
            return Response.ok(entity).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Slider with Id does not exist").build();
        }
    }
    
    //Put on the resource is not allowed
    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response putRoot(){
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("Put root method not allowed").build();
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Slider find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Slider> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Slider> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
}
