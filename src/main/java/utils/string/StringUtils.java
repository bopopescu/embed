package main.java.utils.string;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by digvijaysharma on 15/01/17.
 */
public class StringUtils {

    public static boolean equalsAny(String pattern,String[] matchers) {
        for(String matcher : matchers) {
            if(matcher.equals(pattern)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBlank(String string) {
        if(string.equals(""))
            return true;
        else
            return false;
    }

    /**
     * Serializes the object to String
     * @param object
     * @return
     */
    public static String toString(Object object) {
        return main.java.utils.json.JsonUtils.objectToString(object);
    }

    /**
     * Deserializes the string into object of given toClass
     * @param s
     * @param toClass
     * @return
     */
    public static Object toObject(String s, Class toClass) {
        return main.java.utils.json.JsonUtils.stringToObject(s,toClass);
    }

    public static boolean isNotBlank(String string) {
        return !isBlank(string);
    }

    public static void main(String[] args) {
        List<String> list= new ArrayList<String>();
        list.add("ads");
        list.add("dhg");
        System.out.println(toString(list));
        System.out.println(toString(new Object() {
            String x = "asd";
            int y = 25;
            Object z = new Object() {
                String a = "dj";
                int b = 10;
            };
        })
        );
    }
}
