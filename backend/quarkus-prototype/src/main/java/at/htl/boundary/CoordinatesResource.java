package at.htl.boundary;

import at.htl.controller.CoordinatesRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("coordinates")
public class CoordinatesResource {

    @Inject
    CoordinatesRepository cr;

    @GET
    @Path("all")
    public Response get() {
        return Response.ok(cr.getAllCoordinates()).build();
    }
}
