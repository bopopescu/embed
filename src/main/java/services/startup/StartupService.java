package main.java.services.startup; /**
 * Created by admin on 4/27/16.
 */

import com.github.toastshaman.dropwizard.auth.jwt.JwtAuthFilter;
import com.meltmedia.dropwizard.mongo.MongoBundle;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import main.java.core.annotation.Controller;
import main.java.core.annotation.FetchBean;
import main.java.core.annotation.Mao;
import main.java.core.annotation.Service;
import main.java.core.config.AppNameConfig;
import main.java.core.config.JawtConfig;
import main.java.core.config.MongoConfig;
import main.java.core.config.StartupConfig;
import main.java.core.vo.JawtUserVO;
import main.java.core.vo.UserVO;
import main.java.mao.user.impl.UserMaoImpl;
import main.java.utils.security.JawtAuthenticator;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;
import org.mongodb.morphia.DatastoreImpl;
import org.mongodb.morphia.Morphia;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;

public class StartupService extends Application<StartupConfig> {

    private MongoClient client;

    private DatastoreImpl datastore;

    private Morphia morphia;

    private ServerAddress addr;

    private MongoCredential credentia;

    public static List<Object> maos;

    public static List<Object> services;

    public static List<Object> resources;

    private JwtConsumer consumer;

    private org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(StartupService.class);

    public static void main(String[] args) throws Exception {
        new StartupService().run(args);
    }

    @Override public String getName() {
        return ((AppNameConfig) main.java.utils.yaml.YamlMapper.getObject("app_name.yml", AppNameConfig.class)).getAppName();
    }

    @Override public void initialize(Bootstrap<StartupConfig> bootstrap) {
        LOG.info("Initializing App");
        bootstrap.addBundle(MongoBundle.<StartupConfig>builder().withConfiguration(StartupConfig::getMongo).build());
        setupMongo();
    }

    @Override public void run(StartupConfig configuration, Environment environment) throws IllegalAccessException, IOException, InterruptedException {
        LOG.info("Starting App");
        setupJawt(configuration, environment);
        setupEnvironment(environment);
        setupControllers(environment);
        fetchBeans();
        LOG.info("Running the core code on Pi Now.....");
        StartupServiceNew.main();
    }

    private void setupJawt(StartupConfig configuration, Environment environment) {
        LOG.info("Setting Up Jawt");
        configuration.setJawtConfig(main.java.utils.yaml.YamlMapper.getObject("jawt.yml", JawtConfig.class));
        try {
            final byte[] key = configuration.getJawtConfig().getJwtTokenSecret();
            consumer = new JwtConsumerBuilder().setAllowedClockSkewInSeconds(
                    30)           // allow some leeway in validating time based claims to account for clock skew
                    .setRequireExpirationTime()                 // the JWT must have an expiration time
                    .setRequireSubject()                        // the JWT must have a subject claim
                    .setVerificationKey(new HmacKey(key))       // verify the signature with the public key
                    .setRelaxVerificationKeyValidation()        // relaxes key length requirement
                    .build();                                   // create the JwtConsumer instance
            environment.jersey().register(new AuthDynamicFeature(
                    new JwtAuthFilter.Builder<JawtUserVO>().setJwtConsumer(consumer).setRealm("realm")
                            .setPrefix("Bearer")
                            .setAuthenticator(new JawtAuthenticator(new UserMaoImpl(datastore)))
                            .buildAuthFilter()));
            environment.jersey().register(new AuthValueFactoryProvider.Binder<>(Principal.class));
            environment.jersey().register(RolesAllowedDynamicFeature.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupEnvironment(Environment environment) {
        LOG.info("Setting Up Environment");
        environment.jersey().setUrlPattern("/*");
        environment.jersey().register(MultiPartFeature.class);
    }

    private void setupControllers(Environment environment) {
        LOG.info("Setting Up Controllers");
        registerResources(environment, inflateResources(inflateServices(inflateMaos())));
    }

    private void registerResources(Environment environment, List<Object> resources) {
        LOG.info("Registering Controllers");
        for (Object res : resources) {
            environment.jersey().register(res);
        }
    }

    private List<Object> inflateMaos() {
        LOG.info("Inflating Maos");
        return maos = injectDependencies("main.java.mao", Mao.class, Arrays.asList(new Object[] { datastore }));
    }

    private List<Object> inflateServices(List<Object> maos) {
        LOG.info("Inflating Services");
        return services = injectDependencies("main.java.services", Service.class, maos);
    }

    private List<Object> inflateResources(List<Object> services) {
        LOG.info("Inflating Resources");
        return resources = injectDependencies("main.java.core.web.controller", Controller.class, services);
    }

    private List<Object> injectDependencies(String dependencyPackageName, Class<? extends Annotation> annotation,
            List<Object> dependencies)
    {
        List<Object> beans = new ArrayList<Object>();
        Reflections reflections = new Reflections(dependencyPackageName);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(annotation);
        for (Class<?> clazz : annotated) {
            Constructor<?>[] ctor = null;
            ctor = clazz.getConstructors();
            List<Object> injectionParams = new ArrayList<Object>();
            for (Class<?> cl : ctor[0].getParameterTypes()) {
                for (Object dependency : dependencies) {
                    if (dependency.getClass().getName().equals(cl.getName())) {
                        injectionParams.add(dependency);
                    }
                }
            }
            Object[] injectionArray = (Object[]) injectionParams.toArray();
            try {
                beans.add(ctor[0].newInstance(injectionArray));
            }
            catch (InstantiationException e) {
                e.printStackTrace();
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        for (Object bean : beans)
            LOG.info(bean.getClass().getName());
        return beans;
    }

    private void fetchBeans() throws IllegalAccessException {
        LOG.info("Fetching field dependency beans and injecting them");
        fetchBeans("main.java.mao");
        fetchBeans("main.java.services");
        fetchBeans("main.java.core.web.controller");
    }

    private void fetchBeans(String dependencyPackageName) throws IllegalAccessException {
        Reflections reflections = new Reflections(dependencyPackageName, new FieldAnnotationsScanner());
        Set<Field> annotated = reflections.getFieldsAnnotatedWith(FetchBean.class);
        for (Field field : annotated) {
            Class<?> owningClazz = field.getDeclaringClass();
            LOG.info(owningClazz.getName());
            Object owningInstance = null;
            Class<?> typeClazz = field.getType();
            if(owningClazz.isAnnotationPresent(Mao.class)) {
                for(Object mao : maos) {
                    if(mao.getClass().getName().equals(owningClazz.getName())) {
                        owningInstance = mao;
                    }
                }
            }
            else if(owningClazz.isAnnotationPresent(Service.class)) {
                for(Object service : services) {
                    if(service.getClass().getName().equals(owningClazz.getName())) {
                        owningInstance = service;
                    }
                }
            }
            else if(owningClazz.isAnnotationPresent(Controller.class)) {
                for(Object resource : resources) {
                    if(resource.getClass().getName().equals(owningClazz.getName())) {
                        owningInstance = resource;
                    }
                }
            }
            else {
                LOG.error("Owning class should be one of { Mao,Service,Controller }");
                return;
            }
                
            if(typeClazz.isAnnotationPresent(Mao.class)) {
                for(Object mao : maos) {
                    if(mao.getClass().getName().equals(typeClazz.getName())) {
                        field.set(owningInstance, mao);
                    }
                }
            }
            else if(typeClazz.isAnnotationPresent(Service.class)) {
                for(Object service : services) {
                    if(service.getClass().getName().equals(typeClazz.getName())) {
                        field.set(owningInstance, service);
                    }
                }
            }
            else if(typeClazz.isAnnotationPresent(Controller.class)) {
                for(Object resource : resources) {
                    if(resource.getClass().getName().equals(typeClazz.getName())) {
                        field.set(owningInstance, resource);
                    }
                }
            }
            else {
                LOG.error("Type class should be one of { Mao,Service,Controller }");
                return;
            }
        }
    }

    private void setupMongo() {
        LOG.info("Configuring Mongo Client");
        MongoConfig mongoConfig = main.java.utils.yaml.YamlMapper.getObject("mongo.yml", MongoConfig.class);
        morphia = new Morphia();
        morphia.map(UserVO.class);
        addr = new ServerAddress(mongoConfig.getServer(), mongoConfig.getPort());
        List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
        credentia = MongoCredential.createCredential(mongoConfig.getUsername(), mongoConfig.getDatabase(), mongoConfig.getPassword().toCharArray());
        credentialsList.add(credentia);
        client = new MongoClient(addr, credentialsList);
        datastore = (DatastoreImpl) morphia.createDatastore(client, mongoConfig.getClientName());
    }
}