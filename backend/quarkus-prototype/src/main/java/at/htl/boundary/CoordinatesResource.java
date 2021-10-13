package at.htl.boundary;

import at.htl.controller.CoordinatesRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("coordinates")
public class CoordinatesResource {

    final CoordinatesRepository cr = new CoordinatesRepository();

    @GET
    @Path("all")
    public Response get() {
        return Response.ok(cr.getAllCoordinates()).build();
    }
}
