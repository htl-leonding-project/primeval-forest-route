package at.htl.boundary;

import at.htl.controller.GpxDataRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.IOException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Consumes;

@Path("gpx")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GpxDataResource {

    @Inject
    GpxDataRepository gpxDataRepository;

    @GET
    @Path("all")
    public Response getListOfRouteNames() {
        return Response.ok(gpxDataRepository.getAllGpxData()).build();
    }

    @GET
    @Path("{id}")
    public Response getCoordinatesListWithRouteId(@PathParam("id") Long i) {
        return Response.ok(gpxDataRepository.getCoordinateList(i)).build();

    @GET
    public Response getAllGpxData() {
        return Response.ok().build();
    }
}
