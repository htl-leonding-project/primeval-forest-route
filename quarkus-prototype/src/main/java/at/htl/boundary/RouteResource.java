package at.htl.boundary;

import at.htl.controller.RouteRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("route")
public class RouteResource {

    final RouteRepository routeRepository = new RouteRepository();

    @GET
    @Path("all")
    public Response getRoutes() {
        return Response.ok(routeRepository.getAllRoutes()).build();
    }


    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") Long id) {
        return Response.ok(routeRepository.findById(id)).build();
    }
}
