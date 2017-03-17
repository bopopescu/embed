package main.java.core.api.response;

import java.util.Map;
import main.java.core.api.base.ServiceResponse;

/**
 * Created by digvijaysharma on 09/01/17.
 */
public class GenerateValidTokenResponse  extends ServiceResponse {

    private static final long serialVersionUID = -7191710901031348258L;

    private Map<String, String> token;

    public Map<String, String> getToken() {
        return token;
    }

    public void setToken(Map<String, String> token) {
        this.token = token;
    }
}

