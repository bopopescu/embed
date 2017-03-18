package main.java.core.web.controller;

import com.google.common.collect.ImmutableMap;
import io.dropwizard.auth.Auth;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import main.java.core.annotation.Controller;
import main.java.core.api.request.GenerateExpiredTokenRequest;
import main.java.core.api.request.GenerateValidTokenRequest;
import main.java.core.api.request.RefreshAuthTokenRequest;
import main.java.core.api.response.GenerateExpiredTokenResponse;
import main.java.core.api.response.GenerateValidTokenResponse;
import main.java.core.api.response.RefreshAuthTokenResponse;
import main.java.core.vo.JawtUserVO;
import main.java.services.jawt.impl.JawtServiceImpl;

/**
 * Created by digvijaysharma on 08/01/17.
 */
@Controller
@Path("/jwt")
@Produces(APPLICATION_JSON)
public class JawtResource {

    private JawtServiceImpl jawtService;

    public JawtResource(JawtServiceImpl jawtService) {
        this.jawtService = jawtService;
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Path("/generate-expired-token")
    public GenerateExpiredTokenResponse generateExpiredToken(GenerateExpiredTokenRequest request) {
        return jawtService.generateExpiredToken(request);
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Path("/generate-valid-token")
    public GenerateValidTokenResponse generateValidToken(GenerateValidTokenRequest request) {
        return jawtService.generateValidToken(request);
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Path("/refresh-auth-token")
    public RefreshAuthTokenResponse refreshAuthToken(RefreshAuthTokenRequest request) {
        try {
            return jawtService.refreshAuthToken(request);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        RefreshAuthTokenResponse refreshAuthTokenResponse = new RefreshAuthTokenResponse();
        refreshAuthTokenResponse.setMessage("Could not refresh token");
        return refreshAuthTokenResponse;
    }

    @GET
    @Path("/check-token")
    public Map<String, Object> get(@Auth Principal user) {
        return ImmutableMap.<String, Object>of("username", user.getName(), "id", ((JawtUserVO) user).getId());
    }
}
