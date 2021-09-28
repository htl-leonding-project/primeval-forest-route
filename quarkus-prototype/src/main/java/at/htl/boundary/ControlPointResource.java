
package at.htl.boundary;

import at.htl.model.ControlPoint;
import at.htl.model.Route;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;

@Path("controlPoint")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ControlPointResource {

    private LinkedList<ControlPoint> controlPoints = new LinkedList<>();

    public ControlPointResource() {
        this.controlPoints.add(
                new ControlPoint("c1", 12.2, 14.5,
                        new Route("r1", 123.0)));
    }

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