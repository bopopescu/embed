package main.java.core.api.response;

import main.java.core.api.base.ServiceResponse;

/**
 * Created by digvijaysharma on 16/12/16.
 */
public class ResetPasswordResponse extends ServiceResponse {

    private static final long serialVersionUID = -7191710901031348258L;

    private String authToken;

    private String refreshToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
