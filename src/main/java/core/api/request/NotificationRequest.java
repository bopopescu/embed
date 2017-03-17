package main.java.core.api.request;

import java.util.List;
import main.java.core.api.base.ServiceRequest;

/**
 * Created by digvijaysharma on 07/01/17.
 */
public class NotificationRequest extends ServiceRequest {

    private static final long serialVersionUID = -7191710901031348258L;

    private String inquiry;

    private List<String> sellers;

    public String getInquiry() {
        return inquiry;
    }

    public void setInquiry(String inquiry) {
        this.inquiry = inquiry;
    }

    public List<String> getSellers() {
        return sellers;
    }

    public void setSellers(List<String> sellers) {
        this.sellers = sellers;
    }
}

