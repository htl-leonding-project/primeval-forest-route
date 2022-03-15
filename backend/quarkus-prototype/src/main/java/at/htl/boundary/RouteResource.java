package at.htl.boundary;

import at.htl.controller.RouteRepository;
import at.htl.model.Route;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.net.URI;

@Path("route")
public class RouteResource {

    final RouteRepository routeRepository = new RouteRepository();

    @GET
    @Path("all")
    public Response get() {
        return Response.ok(routeRepository.getAllRoutes()).build();
    }

    @POST
    @Path("create")
    @Transactional
    public Response create(Route route) {
        routeRepository.save(route);
        return Response.created(URI.create("route/" + route.getId())).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Route update(@PathParam("id") Long id, Route routeUpdated) {
        Route route = routeRepository.findById(id);

        if(route == null) {
            throw new NotFoundException();
        }

        route.setLength(routeUpdated.getLength());
        route.setName(routeUpdated.getName());

        return route;
    }

    @POST
    @Transactional
    @Path("/uploadRouteWithGpxId/{gpxId}")
    public Response uploadRouteWithGpxId(Route route, @PathParam("gpxId") Long gpxId) {
        return Response.ok(
                routeRepository.saveRouteWithGpxId(route, gpxId)
        ).build();
    }

    @GET
    @Path("{id}")
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(routeRepository.findById(id)).build();
    }
}

