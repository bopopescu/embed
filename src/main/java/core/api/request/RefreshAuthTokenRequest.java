package main.java.core.api.request;

import main.java.core.api.base.ServiceRequest;

/**
 * Created by digvijaysharma on 29/01/17.
 */
public class RefreshAuthTokenRequest extends ServiceRequest {

    private static final long serialVersionUID = -7191710901031348258L;

    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}


