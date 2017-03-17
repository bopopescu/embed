package main.java.core.api.request;

import javax.ws.rs.FormParam;
import main.java.core.api.base.ServiceRequest;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by digvijaysharma on 16/12/16.
 */
public class UserLoginRequest extends ServiceRequest {

    private static final long serialVersionUID = -7191710901031348258L;

    @NotEmpty
    @FormParam("username")
    String username;

    @NotEmpty
    @FormParam("password")
    String password;

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

