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
    private static MongoConfig mongoConfig;

    private static String appName;

    private static JawtConfig jawtConfig;

    public MongoConfiguration getMongo() {
        return mongo;
    }

    public void setMongo(MongoConfiguration mongo) {
        this.mongo = mongo;
    }

    public static MongoConfig getMongoConfig() {
        return mongoConfig;
    }

    public static void setMongoConfig(MongoConfig mongoConfig) {
        StartupConfig.mongoConfig = mongoConfig;
    }

    public static JawtConfig getJawtConfig() {
        return jawtConfig;
    }

    public static void setJawtConfig(JawtConfig jawtConfig) {
        StartupConfig.jawtConfig = jawtConfig;
    }

    public static String getAppName() {
        return appName;
    }

    public static void setAppName(String appName) {
        StartupConfig.appName = appName;
    }
}
