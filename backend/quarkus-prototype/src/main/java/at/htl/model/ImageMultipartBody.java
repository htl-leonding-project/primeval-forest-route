package at.htl.model;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;

public class ImageMultipartBody {

    @FormParam("value")
    public InputStream inputStream;

    @FormParam("name")
    @PartType(MediaType.TEXT_PLAIN)
    public String fileName;
}
