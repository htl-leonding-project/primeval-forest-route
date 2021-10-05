package at.htl.boundary;

import at.htl.model.ControlPoint;
import at.htl.model.Route;

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

    private LinkedList<ControlPoint> controlPoints = new LinkedList<>();

    @GET
    @Path("getAllControlPoints")
    public Response getControlPoints() {
        return Response.ok(controlPoints).build();
    }

    @POST
    @Path("addControlPoint")
    public Response addControlPoint(ControlPoint controlPoint) {
        this.controlPoints.add(controlPoint);
        return Response.ok(controlPoint).build();
    }

}
