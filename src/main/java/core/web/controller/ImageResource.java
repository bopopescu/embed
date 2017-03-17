package main.java.core.web.controller;

import java.io.InputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import main.java.core.annotation.Controller;
import main.java.core.api.request.ImageDownloadRequest;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import services.file.impl.FileServiceImpl;

/**
 * Created by digvijaysharma on 09/01/17.
 */
@Controller
@Path("/image")
public class ImageResource {

    private FileServiceImpl fileService;

    public ImageResource(FileServiceImpl fileService) {
        this.fileService = fileService;
    }

    @POST
    @Path("/download")
    @Produces({"image/png", "image/jpg", "image/gif"})
    @Consumes(MediaType.APPLICATION_JSON)
    public Response downloadImageFile(ImageDownloadRequest request) {
        return fileService.downloadImageFile(request);
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImageFile(
            @FormDataParam("uploadFile") InputStream fileInputStream,
            @FormDataParam("uploadFile") FormDataContentDisposition fileFormDataContentDisposition) {
        return fileService.uploadImageFile(fileInputStream,fileFormDataContentDisposition);
    }
}
