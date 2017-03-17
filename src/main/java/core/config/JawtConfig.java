package main.java.core.config;

import java.io.UnsupportedEncodingException;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by digvijaysharma on 02/02/17.
 */
public class JawtConfig {

    @NotEmpty
    private static String jwtTokenSecret;

    private static String appPort;

    private static String appHost;

    public byte[] getJwtTokenSecret() throws UnsupportedEncodingException {
        return jwtTokenSecret.getBytes("UTF-8");
    }

    public void setJwtTokenSecret(String jwtTokenSecret) {
        this.jwtTokenSecret = jwtTokenSecret;
    }

    public static String getAppPort() {
        return appPort;
    }

    public void setAppPort(String appPort) {
        core.config.JawtConfig.appPort = appPort;
    }

    public String getAppHost() {
        return appHost;
    }

    public void setAppHost(String appHost) {
        core.config.JawtConfig.appHost = appHost;
    }
}
