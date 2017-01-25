package com.store.catalog.ws.rest.view;





//import org.joda.time.DateTime;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;


/**
 * Created by zouheir on 15/11/15.
 */
@Path("hello")
@Singleton
public class Hello {



    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response getHello() {
        Date time = new Date();
        return Response.status(Response.Status.ACCEPTED).entity("<h1>Hello! "+ time +"!</h1>")
                .build();
    }
}
