package id.kawahedukasi.controller;

import id.kawahedukasi.model.Store;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("/store")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class StoreController {
    @GET
    public Response inputPathParameter() {
        return Response.status(Response.Status.OK).entity(Store.findAll().list()).build();
    }
    @GET
    @Path("/{Id}")
    public Response inputPathParameter(@PathParam("Id") Long id) {
        Store store = Store.findById(id);
        if (store == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.OK).entity(Store.find("id = ?1", id).list()).build();
    }

    @POST
    @Transactional
    public Response post(Map<String, Object> request) {
        Store store = new Store();
        store.name = request.get("name").toString();
        store.count = Integer.valueOf(request.get("count").toString());
        store.price = Double.valueOf(request.get("price").toString());
        store.type = request.get("type").toString();
        store.description = request.get("description").toString();
        store.createdAt = request.get("createdAt").toString();
        store.updatedAt = request.get("updatedAt").toString();

        //save to database
        store.persist();

        return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response put(@PathParam("id") Long id, Map<String, Object> request) {
        Store store = Store.findById(id);
        if (store == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        store.name = request.get("name").toString();
        store.count = Integer.valueOf(request.get("count").toString());
        store.price = Double.valueOf(request.get("price").toString());
        store.type = request.get("type").toString();
        store.description = request.get("description").toString();
        store.createdAt = request.get("createdAt").toString();
        store.updatedAt = request.get("updatedAt").toString();

        //save to database
        store.persist();

        return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id, Map<String, Object> request) {
        Store store = Store.findById(id);
        if (store == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        //save to database
        store.delete();

        return Response.status(Response.Status.OK).entity(new HashMap<>()).build();
    }
}
