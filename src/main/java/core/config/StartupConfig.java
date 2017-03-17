package main.java.core.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.meltmedia.dropwizard.mongo.MongoConfiguration;
import io.dropwizard.Configuration;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by digvijaysharma on 17/12/16.
 */
public class StartupConfig extends Configuration {

    /* Mongo Configuration input for server yml */
    @JsonProperty @NotNull @Valid private MongoConfiguration mongo;

    /* Mongo Configuration input from yml */
    private static core.config.MongoConfig mongoConfig;

    private static String appName;

    private static core.config.JawtConfig jawtConfig;

    public MongoConfiguration getMongo() {
        return mongo;
    }

    public void setMongo(MongoConfiguration mongo) {
        this.mongo = mongo;
    }

    public static core.config.MongoConfig getMongoConfig() {
        return mongoConfig;
    }

    public static void setMongoConfig(core.config.MongoConfig mongoConfig) {
        core.config.StartupConfig.mongoConfig = mongoConfig;
    }

    public static core.config.JawtConfig getJawtConfig() {
        return jawtConfig;
    }

    public static void setJawtConfig(core.config.JawtConfig jawtConfig) {
        core.config.StartupConfig.jawtConfig = jawtConfig;
    }

    public static String getAppName() {
        return appName;
    }

    public static void setAppName(String appName) {
        core.config.StartupConfig.appName = appName;
    }
}
