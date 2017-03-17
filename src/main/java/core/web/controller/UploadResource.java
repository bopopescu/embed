package main.java.core.web.controller;

import io.dropwizard.auth.Auth;
import java.io.IOException;
import java.security.Principal;
import javax.mail.MessagingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import main.java.core.annotation.Controller;
import main.java.core.api.request.UploadFileRequest;
import main.java.core.api.response.UploadFileResponse;
import services.file.impl.FileServiceImpl;

/**
 * Created by Sonu on 6/4/16.
 */
@Controller
@Path("/user/profile")
public class UploadResource {

    private FileServiceImpl uploadService;

    public UploadResource(FileServiceImpl uploadService) {
        this.uploadService = uploadService;
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/upload")
    public UploadFileResponse uploadFile(@Auth Principal user, UploadFileRequest request) throws MessagingException, IOException {
        request.setCallingUserId(user.getName());
        return uploadService.uploadFile(request);
    }

}
