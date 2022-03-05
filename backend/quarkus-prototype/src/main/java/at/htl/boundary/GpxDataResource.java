package at.htl.boundary;

import at.htl.controller.GpxDataRepository;
import at.htl.model.ImageMultipartBody;
import at.htl.model.Route;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import javax.ws.rs.core.MediaType;

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
    @Path("/id/{id}")
    public Response getCoordinatesListById(@PathParam("id") Long i) {
        return Response.ok(gpxDataRepository.getCoordinateListById(i)).build();
    }

    @GET
    @Path("/coords/{name}")
    public Response getCoordinatesListByName(@PathParam("name") String name) {
        return Response.ok(gpxDataRepository.getCoordinateListByName(name)).build();
    }

    @GET
    @Path("/points/{id}")
    public Response getControlPointsListById(@PathParam("id") Long id) {
        return Response.ok(gpxDataRepository.getControlPointListById(id)).build();
    }

    @GET
    @Path("/points/name/{name}")
    public Response getControlPointsListByName(@PathParam("name") String name) {
        return Response.ok(gpxDataRepository.getControlPointListByName(name)).build();
    }

    @POST
    @Transactional
    @Path("/uploadGPX/{route-name}")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public Response create(InputStream xml, @PathParam("route-name") String routeName) {
        System.out.println(xml);
        return Response.ok(
                gpxDataRepository.uploadXml(xml, routeName)
        ).build();
    }

    @GET
    public Response getAllGpxData() {
        return Response.ok().build();
    }
}
