package main.java.services.file;

import main.java.core.api.request.ImageDownloadRequest;
import main.java.core.api.request.UploadFileRequest;
import main.java.core.api.response.UploadFileResponse;
import java.io.IOException;
import java.io.InputStream;
import javax.mail.MessagingException;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

/**
 * Created by digvijaysharma on 16/12/16.
 */
public interface IFileService {
    UploadFileResponse uploadFile(UploadFileRequest request) throws MessagingException, IOException;

    Response downloadImageFile(ImageDownloadRequest request);

    Response uploadImageFile(InputStream fileInputStream, FormDataContentDisposition fileFormDataContentDisposition);
}
