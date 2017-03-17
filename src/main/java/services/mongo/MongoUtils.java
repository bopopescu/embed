package main.java.services.mongo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

/**
 * Created by digvijaysharma on 04/02/17.
 */
public class MongoUtils {

    public static String getIdFromObjectId(String id) {
        return id.substring(id.indexOf("(")+2,id.indexOf(")")-2);
    }

    public static <T> void update(Datastore store, T object) {
        Class<T> clazz = (Class<T>) object.getClass();
        Method m = null;
        try {
            m = clazz.getMethod("getId");
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        String id = null;
        try {
            id = (String) m.invoke(object);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        Query<T> query = store.createQuery(clazz).field("id").equal(id);
        UpdateOperations<T> ops = store.createUpdateOperations(clazz);
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            if (field.getName() != "id" && field.getName() != "created" && field.getName() != "updated") {
                if (field.getType().equals(Boolean.class)) {
                    try {
                        ops.set(field.getName(), clazz.getMethod("is" + field.getName()).invoke(object));
                    }
                    catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        ops.set(field.getName(), clazz.getMethod("get" + field.getName()).invoke(object));
                    }
                    catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        store.update(query, ops);
    }
}
