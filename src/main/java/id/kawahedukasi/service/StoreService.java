package id.kawahedukasi.service;

import javax.transaction.Transactional;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import id.kawahedukasi.model.Store;


import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped

public class StoreService {
    public Response get() {
        return Response.status(Response.Status.OK).entity(Store.findAll().list()).build();
    }

    @Transactional
    public Response post(Map<String, Object> request) {
        Store store = new Store();
        store.name = request.get("name").toString();
        store.count = Integer.valueOf(request.get("count").toString());
        store.price = Double.valueOf(request.get("price").toString());
        store.type = request.get("type").toString();
        store.description = request.get("description").toString();
        store.createdAt = LocalDateTime.parse(request.get("createdAt").toString());
        store.updatedAt = LocalDateTime.parse(request.get("updatedAt").toString());

        //save to database
        store.persist();

        return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
    }

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
        store.createdAt = LocalDateTime.parse(request.get("createdAt").toString());
        store.updatedAt = LocalDateTime.parse(request.get("updatedAt").toString());

        //save to database
        store.persist();

        return Response.status(Response.Status.CREATED).entity(new HashMap<>()).build();
    }


    @Transactional
    public Response delete(Long id) {
        Store store = Store.findById(id);
        if (store == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        //save to database
        store.delete();

        return Response.status(Response.Status.OK).entity(new HashMap<>()).build();
    }
}
