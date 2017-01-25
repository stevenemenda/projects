package com.store.catalog.ws.rest.view.nosql;

import org.springframework.stereotype.Component;

import javax.ws.rs.Path;

/**
 * Created by zouheir on 11/11/15.
 */
@Path("catalog/admin")
@Component
public class CatalogAdminResource {

    /*
    @Autowired
    CatalogAdminService catalogAdminService;


    public void setCatalogAdminService(CatalogAdminService catalogAdminService) {
        this.catalogAdminService = catalogAdminService;
    }


    @GET
    @Path("item/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItem(@PathParam("id") String id) throws Exception {

        SearchableItem item = catalogAdminService.getItem(id);

        return Response.status(200).entity(item).build();
    }


    @POST
    @Path("item")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSearchableItem(SearchableItem searchableItem) throws Exception {

        String id =  catalogAdminService.createItem(searchableItem);

        return Response.status(201).entity(id).build();
    }


    @PUT
    @Path("item")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCategory(SearchableItem searchableItem) throws Exception {

        SearchableItem item = catalogAdminService.updateItem(searchableItem) ;
        return Response.status(201).entity(item).build();



    }

    @DELETE
    @Path("item/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteCategory(@PathParam("id") String id) throws Exception {

        catalogAdminService.deleteItem(id);

        return Response.status(204).build();

    }
    */
}



