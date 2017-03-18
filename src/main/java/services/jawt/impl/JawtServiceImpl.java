package main.java.services.jawt.impl;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableMap;
import main.java.core.annotation.Service;
import main.java.core.api.request.GenerateExpiredTokenRequest;
import main.java.core.api.request.GenerateValidTokenRequest;
import main.java.core.api.request.RefreshAuthTokenRequest;
import main.java.core.api.response.GenerateExpiredTokenResponse;
import main.java.core.api.response.GenerateValidTokenResponse;
import main.java.core.api.response.RefreshAuthTokenResponse;
import main.java.core.api.validation.ResponseCode;
import main.java.core.vo.AuthTokenVO;
import main.java.core.vo.JawtUserVO;
import main.java.core.vo.UserVO;
import io.dropwizard.auth.Auth;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.Principal;
import static java.util.Collections.singletonMap;
import java.util.Date;
import java.util.Map;
import main.java.mao.user.impl.UserMaoImpl;
import static org.jose4j.jws.AlgorithmIdentifiers.HMAC_SHA256;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;
import main.java.services.jawt.IJawtService;
import main.java.core.config.StartupConfig;
import main.java.utils.http.HttpSender;

/**
 * Created by digvijaysharma on 09/01/17.
 */
@Service
public class JawtServiceImpl implements IJawtService{

    private org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(JawtServiceImpl.class);

    private static String url = "http://" + StartupConfig.getJawtConfig().getAppHost() + ":" + StartupConfig.getJawtConfig().getAppPort() + "/";

    private byte[] tokenSecret;

    private UserMaoImpl userMao;

    public JawtServiceImpl(UserMaoImpl userMao) {
        try {
            this.tokenSecret = StartupConfig.getJawtConfig().getJwtTokenSecret();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.userMao = userMao;
    }

    @Override
    public GenerateExpiredTokenResponse generateExpiredToken(GenerateExpiredTokenRequest request) {
        GenerateExpiredTokenResponse response = new GenerateExpiredTokenResponse();
        UserVO user = userMao.getUserByUsernameOrMobileNumber(request.getUsername());
        if(user != null) {
            if(user.getPassword().equals(request.getPassword())) {
                final JwtClaims claims = new JwtClaims();
                claims.setExpirationTimeMinutesInTheFuture(-20);
                claims.setSubject(user.getId());

                final JsonWebSignature jws = new JsonWebSignature();
                jws.setPayload(claims.toJson());
                jws.setAlgorithmHeaderValue(HMAC_SHA256);
                jws.setKey(new HmacKey(tokenSecret));

                try {
                    AuthTokenVO authToken = new AuthTokenVO();
                    String mAuthToken = jws.getCompactSerialization();
                    response.setSuccess(true);
                    response.setMessage("Save this token for using other Apis");
                    response.setToken(singletonMap("token", mAuthToken));
                    return response;
                }
                catch (JoseException e) { throw Throwables.propagate(e); }
            }
            else {
                response.addError(ResponseCode.INVALID_CREDENTIALS, "Invalid Credentials");
            }
        }
        else {
            response.addError(ResponseCode.USER_DOES_NOT_EXIST, "User does not exist");
        }
        return response;
    }

    @Override
    public GenerateValidTokenResponse generateValidToken(GenerateValidTokenRequest request) {
        GenerateValidTokenResponse response = new GenerateValidTokenResponse();
        UserVO user = userMao.getUserByUsernameOrMobileNumber(request.getUsername());
        if(user != null) {
            if(user.getPassword() != null) {
                if(user.getPassword().equals(request.getPassword())) {
                    final JwtClaims claims = new JwtClaims();
                    claims.setSubject(user.getId());
                    if(request.getType().equals(GenerateValidTokenRequest.Type.REFRESH.name())) {
                        claims.setExpirationTimeMinutesInTheFuture(60);
                    }
                    else {
                        claims.setExpirationTimeMinutesInTheFuture(30);
                    }
                    final JsonWebSignature jws = new JsonWebSignature();
                    jws.setPayload(claims.toJson());
                    jws.setAlgorithmHeaderValue(HMAC_SHA256);
                    jws.setKey(new HmacKey(tokenSecret));

                    try {
                        String mAuthToken = jws.getCompactSerialization();
                        response.setSuccess(true);
                        response.setMessage("Save this token for using other Apis");
                        response.setToken(singletonMap("token", mAuthToken));
                        return response;
                    }
                    catch (JoseException e) { throw Throwables.propagate(e); }
                }
                else {
                    response.addError(ResponseCode.INVALID_CREDENTIALS, "Invalid Credentials");
                }
            }
            else {
                response.addError(ResponseCode.NO_PASSWORD_SET, "Set a password first");
            }
        }
        else {
            response.addError(ResponseCode.USER_DOES_NOT_EXIST, "User does not exist");
        }
        return response;
    }

    @Override
    public RefreshAuthTokenResponse refreshAuthToken(RefreshAuthTokenRequest request)
            throws UnsupportedEncodingException
    {
        RefreshAuthTokenResponse response = new RefreshAuthTokenResponse();
        UserVO user = null;
        try {
            try {
                final byte[] key = StartupConfig.getJawtConfig().getJwtTokenSecret();
                JwtConsumer consumer = new JwtConsumerBuilder().setAllowedClockSkewInSeconds(30)    // allow some leeway in validating time based claims to account for clock skew
                                                        .setRequireExpirationTime()                 // the JWT must have an expiration time
                                                        .setRequireSubject()                        // the JWT must have a subject claim
                                                        .setVerificationKey(new HmacKey(key))       // verify the signature with the public key
                                                        .setRelaxVerificationKeyValidation()        // relaxes key length requirement
                                                        .build();                                   // create the JwtConsumer instance
                user = userMao.getUserByUserCode(consumer.process(request.getRefreshToken()).getJwtClaims().getSubject());
            }
            catch (MalformedClaimException e) {
                e.printStackTrace();
            }
        }
        catch (InvalidJwtException e) {
            e.printStackTrace();
            String errMsg = e.getMessage().toString();
            user = userMao.getUserByUserCode(errMsg.substring(errMsg.indexOf(":")+2,errMsg.indexOf(",")-1));
        }
        if(user != null) {
            if(checkTokenExpired(userMao.getAuthTokenByUserCode(user.getCode()).getRefreshToken())) {
                response.setAuthToken(generateValidToken(new GenerateValidTokenRequest(user.getUsername(), user.getPassword(),
                        GenerateValidTokenRequest.Type.AUTH.name())).getToken().get("token"));
                response.setRefreshToken(generateValidToken(new GenerateValidTokenRequest(user.getUsername(), user.getPassword(),
                        GenerateValidTokenRequest.Type.REFRESH.name())).getToken().get("token"));
            }
            else {
                if(checkTokenExpired(userMao.getAuthTokenByUserCode(user.getCode()).getAuthToken())) {
                    if(userMao.getAuthTokenByUserCode(user.getCode()).getRefreshToken().equals(request.getRefreshToken())) {
                        response.setAuthToken(generateValidToken(new GenerateValidTokenRequest(user.getUsername(), user.getPassword(),GenerateValidTokenRequest.Type.AUTH.name())).getToken().get("token"));
                        response.setRefreshToken(request.getRefreshToken());
                    }
                    else {
                        response.addError(ResponseCode.INVALID_REFRESH_TOKEN, "This refresh token is illegal");
                    }
                }
                else {
                    response.addError(ResponseCode.AUTH_TOKEN_STILL_VALID, "The auth token for this user is still valid");
                }
            }
        }
        else {
            response.addError(ResponseCode.USER_DOES_NOT_EXIST, "User does not exist");
        }
        if(!response.hasErrors()) {
            AuthTokenVO authToken = new AuthTokenVO();
            authToken.setRefreshToken(response.getRefreshToken());
            authToken.setAuthToken(response.getAuthToken());
            authToken.setUserCode(user.getCode());
            authToken.setUserName(user.getUsername());
            authToken.setExpiry(new Date(System.currentTimeMillis()+1800000));
            authToken.setCreated(new Date());
            authToken.setUpdated(new Date());
            userMao.saveAuthToken(authToken);

            response.setSuccess(true);
        }
        return response;
    }

    private boolean checkTokenExpired(String token){
        StringBuffer httpResponse = null;
        try {
            httpResponse = new HttpSender().send(
                    "GET",
                    url + "jwt/check-token",
                    "{\"Content-Type\":\"application/json\",\"Authorization\":\"Bearer " + token + "\"}",
                    "",
                    "{}"
            );
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println(httpResponse);
        return (httpResponse.toString().contains("Credentials are required to access this resource."));
    }

    @Override
    public Map<String, Object> get(@Auth Principal user) {
        return ImmutableMap.<String, Object>of("username", user.getName(), "id", ((JawtUserVO) user).getId());
    }
}
