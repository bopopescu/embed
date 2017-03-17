package main.java.core.api.request;

import main.java.core.api.base.ServiceRequest;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by digvijaysharma on 09/01/17.
 */
public class GenerateValidTokenRequest extends ServiceRequest {

    public enum Type {
        REFRESH,
        AUTH
    }

    private static final long serialVersionUID = -7191710901031348258L;

    @NotEmpty String username;

    @NotEmpty String password;

    private String type;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GenerateValidTokenRequest() {

    }

    public GenerateValidTokenRequest(String username, String password, String type) {
        this.username = username;
        this.type = type;
        this.password = password;
    }
}

