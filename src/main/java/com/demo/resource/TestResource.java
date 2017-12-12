package com.demo.resource;

import com.demo.provider.Flag;


import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;


@Path("test")
@Produces(MediaType.TEXT_PLAIN)
public class TestResource {


    @GET
    @Path("rte")
    public String getRte(@QueryParam("error") Flag error) {
        if (error.isPresent()) {
            throw new RuntimeException("error from RTE resource.");
        }
        return "OK from RTE resource.\n";
    }

    @GET
    @Path("wae")
    public String getWae(@QueryParam("error") Flag error) {
        if (error.isPresent()) {
            throw new WebApplicationException("error from WAE resource.", 500);
        }
        return "OK from WAE resource.\n";
    }

    @GET
    @Path("bre")
    public String getBre(@QueryParam("error") Flag error) {
        if (error.isPresent()) {
            throw new BadRequestException("error from BRE resource.");
        }
        return "OK from BRE resource.\n";
    }
}
