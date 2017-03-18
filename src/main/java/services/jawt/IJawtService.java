package main.java.services.jawt;

import main.java.core.api.request.GenerateExpiredTokenRequest;
import main.java.core.api.request.GenerateValidTokenRequest;
import main.java.core.api.request.RefreshAuthTokenRequest;
import main.java.core.api.response.GenerateExpiredTokenResponse;
import main.java.core.api.response.GenerateValidTokenResponse;
import main.java.core.api.response.RefreshAuthTokenResponse;
import io.dropwizard.auth.Auth;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Map;

/**
 * Created by digvijaysharma on 09/01/17.
 */
public interface IJawtService {
    GenerateExpiredTokenResponse generateExpiredToken(GenerateExpiredTokenRequest request);

    GenerateValidTokenResponse generateValidToken(GenerateValidTokenRequest request);

    RefreshAuthTokenResponse refreshAuthToken(RefreshAuthTokenRequest request) throws UnsupportedEncodingException;

    Map<String, Object> get(@Auth Principal user);
}
