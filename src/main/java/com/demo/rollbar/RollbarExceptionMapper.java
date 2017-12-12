package com.demo.rollbar;

import com.rollbar.notifier.Rollbar;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;



public class RollbarExceptionMapper implements ExceptionMapper<Throwable> {

    private final Rollbar rollbar;

    @Inject
    public RollbarExceptionMapper(Rollbar rollbar) {
        this.rollbar = rollbar;
    }

    @Override
    public Response toResponse(Throwable ex) {
        rollbar.error(ex);
        Response response;
        if (ex instanceof WebApplicationException) {
            response = ((WebApplicationException) ex).getResponse();
        } else {
            response = Response.serverError()
                    .entity(ex.getMessage())
                    .build();
        }
        return response;
    }
}
