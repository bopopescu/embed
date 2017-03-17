package main.java.core.api.request;

import main.java.core.api.base.ServiceRequest;

/**
 * Created by digvijaysharma on 09/01/17.
 */
public class ImageDownloadRequest extends ServiceRequest {

    private static final long serialVersionUID = -7191710901031348258L;

    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
