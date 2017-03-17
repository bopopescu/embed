package main.java.core.api.request;

import main.java.core.api.base.ServiceRequest;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by digvijaysharma on 09/01/17.
 */
public class GenerateExpiredTokenRequest extends ServiceRequest {

    private static final long serialVersionUID = -7191710901031348258L;

    @NotEmpty String username;

    @NotEmpty String password;

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
}
