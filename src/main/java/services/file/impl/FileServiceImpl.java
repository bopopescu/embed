package services.file.impl;

import core.annotation.Service;
import core.api.request.ImageDownloadRequest;
import core.api.request.UploadFileRequest;
import core.api.response.UploadFileResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.mail.MessagingException;
import javax.ws.rs.core.Response;
import mao.upload.impl.UploadMaoImpl;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import services.file.IFileService;

/**
 * Created by digvijaysharma on 16/12/16.
 */
@Service
public class FileServiceImpl implements IFileService {

    private org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(FileServiceImpl.class);

    public static final String IMAGE_SERVER = "/home/digvijaysharma/images/";

    private UploadMaoImpl uploadMao;

    public FileServiceImpl(UploadMaoImpl uploadMao) {
        this.uploadMao = uploadMao;
    }

    @Override public UploadFileResponse uploadFile(UploadFileRequest request) throws MessagingException, IOException {
        UploadFileResponse response = new UploadFileResponse();
        FormDataContentDisposition fileDetail = request.getFileDetail();
        InputStream uploadedInputStream = request.getUploadedInputStream();
        String uploadedFileLocation = "/Users/admin/Desktop/" + fileDetail.getFileName();
        writeToFile(uploadedInputStream, uploadedFileLocation);
        String output = "File uploaded to : " + uploadedFileLocation;
        response.setFileLink(uploadedFileLocation);
        response.setSuccess(true);
        return response;
    }

    private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) throws IOException {
        int read;
        final int BUFFER_LENGTH = 1024;
        final byte[] buffer = new byte[BUFFER_LENGTH];
        OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
        while ((read = uploadedInputStream.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
        out.flush();
        out.close();
    }

    @Override
    public Response downloadImageFile(ImageDownloadRequest request) {
        File file = new File(IMAGE_SERVER + request.getFileName());
        Response.ResponseBuilder responseBuilder = Response.ok((Object) file);
        responseBuilder.header("Content-Disposition", "attachment; filename=\"profileImage.png\"");
        return responseBuilder.build();
    }

    @Override
    public Response uploadImageFile(InputStream fileInputStream,
            FormDataContentDisposition fileFormDataContentDisposition)
    {
        String fileName = null;
        String uploadFilePath = null;

        try {
            fileName = fileFormDataContentDisposition.getFileName();
            uploadFilePath = writeToImageServer(fileInputStream, fileName);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        finally {
            // release resources, if any
        }
        return Response.ok("File uploaded successfully at " + uploadFilePath).build();
    }

    /**
     * write input stream to file server
     *
     * @param inputStream
     * @param fileName
     * @throws IOException
     */
    private String writeToImageServer(InputStream inputStream, String fileName) throws IOException {

        OutputStream outputStream = null;
        String qualifiedUploadFilePath = IMAGE_SERVER + fileName;

        try {
            outputStream = new FileOutputStream(new File(qualifiedUploadFilePath));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        finally {
            //release resource, if any
            outputStream.close();
        }
        return qualifiedUploadFilePath;
    }
}
