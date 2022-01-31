package at.htl.boundary;

import at.htl.controller.PictureRepository;
import at.htl.model.ImageMultipartBody;
import at.htl.model.Picture;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Path("picture")
public class PictureResource {

    @Inject
    PictureRepository repo;

    @GET
    @Path("getImageById/{id}")
    public Response getImageById(@PathParam("id") Long id) {
        File file = repo.getPictureById(id);
        System.out.println(file);
        return Response.ok(file).build();
    }

    @POST
    @Transactional
    @Path("uploadPicture")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response create(@MultipartForm ImageMultipartBody imageMultipartBody) {
        return Response.ok(
            repo.uploadImage(imageMultipartBody)
        ).build();
    }
}
