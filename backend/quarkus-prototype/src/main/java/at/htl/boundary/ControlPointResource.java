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
import java.io.InputStream;
import java.util.LinkedList;

@Path("controlPoint")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ControlPointResource {

    @Inject
    ControlPointRepository controlPointRepository;

    private LinkedList<ControlPoint> controlPoints = new LinkedList<>();

    @GET
    @Path("all")
    public Response getControlPoints() {
        return Response.ok(controlPointRepository.getAllControlpoints()).build();
    }

    @POST
    @Path("addControlPoint")
    public Response addControlPoint(ControlPoint controlPoint) {
        this.controlPoints.add(controlPoint);
        return Response.ok(controlPoint).build();
    }

    @POST
    @Path("import-cp-csv/{gpx-data-id}")
    @Consumes("application/vnd.ms-excel")
    @Produces(MediaType.TEXT_PLAIN)
    public Response importCpCsv(InputStream is, @PathParam("gpx-data-id") Long id) {
        return Response.ok(
                controlPointRepository.importCsvCp(is, id)
        ).build();
    }
}
