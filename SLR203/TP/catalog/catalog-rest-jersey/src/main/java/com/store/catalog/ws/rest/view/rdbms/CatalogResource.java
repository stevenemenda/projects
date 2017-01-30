package com.store.catalog.ws.rest.view.rdbms;

import com.store.catalog.model.ProductDTO;
import com.store.catalog.service.catalog.CatalogService;
import com.store.catalog.model.CategoryDTO;
import com.store.catalog.model.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by ZCadi on 26/10/2015.
 */
@Path("/catalog")
public class CatalogResource {


    @Autowired CatalogService catalogServiceImpl;

    public CatalogService getCatalogServiceImpl() {
        return catalogServiceImpl;
    }

    public void setCatalogServiceImpl(CatalogService catalogServiceImpl) {
        this.catalogServiceImpl = catalogServiceImpl;
    }
    
    @Path("/categories")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getCategories() throws Exception {

        return Response.ok(catalogServiceImpl.findCategories()).build();
    }

    @Path("/category/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getCategory(@PathParam("id") Long id) throws Exception {

    	return Response.ok(catalogServiceImpl.findCategory(id)).build();
    }

    @Path("/category")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCategory(CategoryDTO categoryDTO) throws Exception {

    	return Response.ok(catalogServiceImpl.createCategory(categoryDTO)).build();

    }

    @Path("/category")
    @PUT
    public Response updateCategory(CategoryDTO categoryDTO) throws Exception {
    	catalogServiceImpl.updateCategory(categoryDTO);
    	return Response.ok().build();

    }

    @Path("/category/{id}")
    @DELETE
    public Response deleteCategory(@PathParam("id") Long id) throws Exception {
    	catalogServiceImpl.deleteCategory(id);
    	return Response.ok().build();

    }

    @Path("/products")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response getProducts() throws Exception {

    	return Response.ok(catalogServiceImpl.findProducts()).build();
    }

    @Path("/product/{id}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public Response getProduct(@PathParam("id") Long id) throws Exception {

    	return Response.ok(catalogServiceImpl.findProduct(id)).build();
    }

    @Path("/product")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createProduct(ProductDTO productDTO) throws Exception {

    	return Response.ok(catalogServiceImpl.createProduct(productDTO)).build();
    }

    @Path("/product")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateProduct(ProductDTO productDTO) throws Exception {
    	catalogServiceImpl.updateProduct(productDTO);
    	return Response.ok().build();

    }

    @Path("/product/{id}")
    @DELETE
    public Response deleteProduct(@PathParam("id") Long id) throws Exception {
    	catalogServiceImpl.deleteProduct(id);
    	return Response.ok().build();

    }

    @Path("/items")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItems() throws Exception {

    	return Response.ok(catalogServiceImpl.findItems()).build();
    }

    @Path("/item/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItem(@PathParam("id") Long id) throws Exception {

    	return Response.ok(catalogServiceImpl.findItem(id)).build();
    }

    @Path("/item")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createItem(ItemDTO itemDTO) throws Exception {
    	
    	return Response.ok(catalogServiceImpl.createItem(itemDTO)).build();

    }

    @Path("/item")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateItem(ItemDTO itemDTO) throws Exception {
    	catalogServiceImpl.updateItem(itemDTO);
    	return Response.ok().build();

    }
    
    @Path("/item/{id}")
    @DELETE
    public Response deleteItem(@PathParam("id") Long id) throws Exception {
    	catalogServiceImpl.deleteItem(id);
    	return Response.ok().build();

    }

    @Path("/items/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getItemsByName(@PathParam("name") String name) throws Exception {

    	return Response.ok(catalogServiceImpl.searchItems(name)).build();
    }

}
