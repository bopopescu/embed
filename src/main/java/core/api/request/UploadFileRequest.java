package main.java.core.api.request;

import java.io.InputStream;
import main.java.core.api.base.ServiceRequest;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 * Created by digvijaysharma on 16/12/16.
 */
public class UploadFileRequest extends ServiceRequest {

    private static final long serialVersionUID = -7191710901031348258L;

    @FormDataParam("file")
    InputStream uploadedInputStream;

    @FormDataParam("file")
    FormDataContentDisposition fileDetail;

    public InputStream getUploadedInputStream() {
        return uploadedInputStream;
    }

    public void setUploadedInputStream(InputStream uploadedInputStream) {
        this.uploadedInputStream = uploadedInputStream;
    }

    public FormDataContentDisposition getFileDetail() {
        return fileDetail;
    }

    public void setFileDetail(FormDataContentDisposition fileDetail) {
        this.fileDetail = fileDetail;
    }
}
