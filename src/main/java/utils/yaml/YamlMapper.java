package main.java.utils.yaml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import main.java.core.config.MongoConfig;
import java.io.IOException;

/**
 * Created by digvijaysharma on 02/02/17.
 */
public class YamlMapper {

    public static <T> T getObject(String fileName, Class clazz) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return (T) mapper.readValue(YamlMapper.class.getClassLoader().getResourceAsStream(fileName), clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
       //  ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
//        try {
//            //            JawtConfig jawtConfig = mapper.readValue(new File("yml/jawt.yml"), JawtConfig.class);
//            MongoConfig mongoConfig = mapper.readValue(new File("yml/mongo.yml"), MongoConfig.class);
//            System.out.println(mongoConfig.getDatabase());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(System.getProperty(YamlMapper.class.getClassLoader().getResource("mongo.yml").getFile()));
//        System.out.println(YamlMapper.class.getProtectionDomain().getCodeSource().getLocation().getFile());
//        System.out.println(StartupService.class.getProtectionDomain().getCodeSource().getLocation().getFile());
//        getObject("mongo.yml", MongoConfig.class);
        getObject("mongo.yml", MongoConfig.class);
    }
}
