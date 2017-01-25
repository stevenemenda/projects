package com.store.catalog.ws.rest.view.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;


/**
 * Created by ZCadi on 28/10/2015.
 */
@Provider
public class CustomExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Exception> {

    @Override public Response toResponse(Exception exception) {
        exception.printStackTrace(System.out);
        return Response.status(500).build();
    }
}
