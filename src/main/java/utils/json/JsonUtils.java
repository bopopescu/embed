package main.java.utils.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import main.java.core.vo.JawtUserVO;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by digvijaysharma on 05/01/17.
 */
public class JsonUtils {

    /**
     * <p>          JSON TO OBJECT          </p>
     * @param object = Can be a JsonObject
     * @param toClass = Destination Class for new Object
     * @return
     */
    public static Object jsonToObject(JsonObject object, Class toClass) {
        return convertJsonToObject(jsonToString(object), toClass);
    }

    public static Object jsonToObject(String json, Class toClass) {
        return convertJsonToObject(json, toClass);
    }

    private static Object convertJsonToObject(String object, Class toClass) {
        try {
            return new ObjectMapper().readValue(object,toClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <p>          OBJECT TO JSON         </p>
     * @param object
     * @return
     */
    public static String objectToJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *      <p>     STRING TO JSON    </p>
     * @param s
     * @return
     */
    public static JsonObject stringToJson(String s) {
        if(s.isEmpty())
            return new JsonObject();
        return new JsonParser().parse(s).getAsJsonObject();
    }

    /**
     *      <p>     JSON TO STRING    </p>
     * @param Json
     * @return
     */
    public static String jsonToString(JsonObject Json) {
        return Json.toString();
    }

    public static Map<String, Object> jsonToMap(JsonObject Json) {
        Map<String, Object> retMap = new HashMap<String, Object>();

        if(!Json.isJsonNull()) {
            retMap = toMap(Json);
        }
        return retMap;
    }

    /**
     *      <p>    OBJECT TO STRING      </p>
     * @return
     */
    public static String objectToString(Object object) {
        return objectToJson(object);
    }

    /**
     *       <p>      STRING TO OBJECT       </p>
     * Hopefully its a json String :)
     * @param toClass
     * @return
     */
    public static Object stringToObject(String string, Class toClass) {
        return jsonToObject(stringToJson(string), toClass);
    }

    private static Map<String, Object> toMap(JsonObject object) {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<Map.Entry<String, JsonElement>> keysItr = object.entrySet().iterator();
        while(keysItr.hasNext()) {
            String key = keysItr.next().getKey();
            Object value = object.get(key);

            if(value instanceof JsonArray) {
                value = toList((JsonArray) value);
            }

            else if(value instanceof JsonObject) {
                value = toMap((JsonObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    private static List<Object> toList(JsonArray array) {
        List<Object> list = new ArrayList<Object>();
        for(int i = 0; i < array.size(); i++) {
            Object value = array.get(i);
            if(value instanceof JsonArray) {
                value = toList((JsonArray) value);
            }

            else if(value instanceof JsonObject) {
                value = toMap((JsonObject) value);
            }
            list.add(value);
        }
        return list;
    }

    public static void main(String[] args) {
        //        System.out.println(JsonUtils.stringToJson("{\"a\": \"A\"}"));
        //        JsonObject jsonObject = new JsonObject();
        //        JsonObject.addProperty("a","A");
        //        System.out.println(JsonUtils.jsonToString(JsonObject));
        JawtUserVO x = new JawtUserVO(new BigDecimal(5), "fghgh");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("a","A");
        JsonObject xj = new JsonObject();
        xj.addProperty("id",5);
        xj.addProperty("name","asdrt");
        String s = null;
        Map<String,Object> map = JsonUtils.jsonToMap(stringToJson(s));
        System.out.println(map.toString());
        for(Map.Entry entry : map.entrySet()) {
            entry.setValue(processJson(entry.getValue()));
        }
        System.out.println(map.toString());
        //        System.out.println(stringToJson(jsonToString(jsonObject)).get("a"));
        //        System.out.println(stringToObject(jsonToString(xj), JawtUserVO.class));
        //        System.out.println(objectToString(stringToObject(jsonToString(xj), JawtUserVO.class)));
        //        System.out.println(objectToString(x));
    }

    public static Object processJson(Object object) {
        System.out.println("object = [" + object + "]");
        if(object instanceof ArrayList) {
            object = processJsonArray(object);
        }
        else if(object instanceof JsonObject) {
            for(Map.Entry entry : JsonUtils.jsonToMap((JsonObject) object).entrySet()) {
                object = processJson(entry.getValue());
            }
        }
        else if(object instanceof JsonElement) {
        }
        else {
            System.out.println("objectOri = [" + object + "]");
            System.out.println("ohh");
        }
        return object;
    }

    public static Set<Object> processJsonArray(Object object) {
        Set<Object> set = new HashSet<Object>();
        System.out.println("Processing JSOn Array");
        for(Object ele : (ArrayList) object) {
            if(ele instanceof JsonElement) {
            }
            else if(ele instanceof ArrayList) {
                processJsonArray(ele);
            }
            else if(ele instanceof JsonObject) {
                processJson(ele);
            }
            if(!set.contains(ele)) {
                System.out.println("Adding to set: " + ele);
                set.add(ele);
            }
        }
        return set;
    }
}
