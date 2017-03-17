package main.java.core.api.response;

import main.java.core.api.base.ServiceResponse;

/**
 * Created by digvijaysharma on 16/12/16.
 */
public class SignupResponse extends ServiceResponse {

    private static final long serialVersionUID = -7191710901031348258L;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
