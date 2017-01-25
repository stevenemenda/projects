package com.store.catalog.ws.rest.view.nosql;

import org.springframework.stereotype.Component;

import javax.ws.rs.Path;

/**
 * Created by ZCadi on 26/10/2015.
 */
@Path("elastic")
@Component
public class ElasticResource {


    /*
    @Autowired
    private CatalogSearchService catalogSearchService;

    public CatalogSearchService getCatalogServiceImpl() {
        return catalogSearchService;
    }

    public void setCatalogServiceImpl(CatalogSearchService catalogServiceImpl) {
        this.catalogSearchService = catalogSearchService;
    }

    @GET
    @Path("toto")
    @Produces(MediaType.TEXT_PLAIN)
    public String getToto() {
        return "toto is back";
    }




    @GET
    @Path("item/match/{keyword}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemsByName(@PathParam("keyword") String keyword) throws Exception {

        List<SearchableItem>  items = catalogSearchService.searchItems(keyword) ;

        return Response.status(200).entity(items).build();
    }


    @GET
    @Path("item/prefixe/{match}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItemsByMatchPhrase(@PathParam("match") String match, @QueryParam("from") int from, @QueryParam("size") int size) throws Exception {


        List<SearchableItem>  items = catalogSearchService.matchPhrasePrefixeItems(match, from, size) ;

        return Response.status(200).entity(items).build();

    }
    */
}
