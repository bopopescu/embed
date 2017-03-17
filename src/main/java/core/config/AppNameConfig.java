package main.java.core.config;

/**
 * Created by digvijaysharma on 02/02/17.
 */
public class AppNameConfig {

    private static String appName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        core.config.AppNameConfig.appName = appName;
    }
}
