package main.java.core.api.response;

import main.java.core.api.base.ServiceResponse;

/**
 * Created by digvijaysharma on 16/12/16.
 */
public class UploadFileResponse extends ServiceResponse {

    private static final long serialVersionUID = -7191710901031348258L;

    private String fileLink;

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }
}
