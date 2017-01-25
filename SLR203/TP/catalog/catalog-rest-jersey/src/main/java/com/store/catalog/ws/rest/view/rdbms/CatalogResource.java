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

public class CatalogResource {



    @Autowired CatalogService catalogServiceImpl;

    public CatalogService getCatalogServiceImpl() {
        return catalogServiceImpl;
    }

    public void setCatalogServiceImpl(CatalogService catalogServiceImpl) {
        this.catalogServiceImpl = catalogServiceImpl;
    }




    public Response getCategories() throws Exception {

        return null;
    }


    public Response getCategory(@PathParam("id") Long id) throws Exception {

    	return null;
    }


    public Response createCategory(CategoryDTO categoryDTO) throws Exception {

    	return null;

    }


    public Response updateCategory(CategoryDTO categoryDTO) throws Exception {

    	return null;

    }


    public Response deleteCategory(@PathParam("id") Long id) throws Exception {

    	return null;

    }


    public Response getProducts() throws Exception {

    	return null;
    }


    public Response getProduct(@PathParam("id") Long id) throws Exception {

    	return null;
    }


    public Response createProduct(ProductDTO productDTO) throws Exception {

    	return null;
    }


    public Response updateProduct(ProductDTO productDTO) throws Exception {

    	return null;

    }


    public Response deleteProduct(@PathParam("id") Long id) throws Exception {

    	return null;

    }


    public Response getItems() throws Exception {

    	return null;
    }


    public Response getItem(@PathParam("id") Long id) throws Exception {

    	return null;
    }


    public Response createItem(ItemDTO itemDTO) throws Exception {

    	return null;

    }


    public Response updateItem(ItemDTO itemDTO) throws Exception {

    	return null;

    }


    public Response deleteItem(@PathParam("id") Long id) throws Exception {

    	return null;

    }


    public Response getItemsByName(@PathParam("name") String name) throws Exception {

    	return null;
    }

}
