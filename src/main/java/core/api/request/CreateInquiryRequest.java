package main.java.core.api.request;

import main.java.core.api.base.ServiceRequest;
import main.java.core.dto.WsInquiry;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by digvijaysharma on 15/01/17.
 */
public class CreateInquiryRequest extends ServiceRequest {

    private static final long serialVersionUID = -7191710901031348258L;

    @NotEmpty WsInquiry inquiry;

    public WsInquiry getInquiry() {
        return inquiry;
    }

    public void setInquiry(WsInquiry inquiry) {
        this.inquiry = inquiry;
    }
}

