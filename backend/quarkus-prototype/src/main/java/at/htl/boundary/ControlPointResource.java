package at.htl.boundary;

import at.htl.controller.ControlPointRepository;
import at.htl.model.ControlPoint;
import at.htl.model.Route;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;

@Path("controlPoint")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ControlPointResource {

    @Inject
    ControlPointRepository controlPointRepository;

    @GET
    @Path("all")
    public Response getControlPoints() {
        return Response.ok(controlPointRepository.getAllControlpoints()).build();
    }

    @GET
    @Path("{routeId}")
    public Response getCoordinatesByRouteId(@PathParam("routeId") Long id) {
        return Response.ok(controlPointRepository.getControlPointByRouteId(id)).build();
    }
}
