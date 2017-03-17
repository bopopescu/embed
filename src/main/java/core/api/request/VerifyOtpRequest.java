package main.java.core.api.request;

import main.java.core.api.base.ServiceRequest;
import main.java.core.dto.WsUser;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by digvijaysharma on 16/12/16.
 */
public class VerifyOtpRequest extends ServiceRequest {

    private static final long serialVersionUID = -7191710901031348258L;

    public VerifyOtpRequest() {

    }

    public VerifyOtpRequest(WsUser userDetails) {
        this.userDetails = userDetails;
    }

    @NotEmpty WsUser userDetails;

    public WsUser getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(WsUser userDetails) {
        this.userDetails = userDetails;
    }
}

