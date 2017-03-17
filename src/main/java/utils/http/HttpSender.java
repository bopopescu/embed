package main.java.utils.http;

import com.google.gson.JsonPrimitive;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Created by digvijaysharma on 05/01/17.
 */
/*
        HTTP Client for sending get and post requests
 */
public class HttpSender {

    private org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(HttpSender.class);

    /**
     * @param method "GET" / "POST"
     * @param url Url for the request
     * @param headers Headers as NameValue Pairs
     * @param params Params as NameValue Pairs
     * @param body Body as JSON inside String
     */
    public StringBuffer send(String method, String url, String headers, String params, String body)
            throws URISyntaxException
    {
        if(method.equalsIgnoreCase("GET")) {
            return get(url,headers,params);
        }
        else if(method.equalsIgnoreCase("POST")) {
            if(body != null) {
                return post(url,headers,params,body);
            }
            else {
                LOG.info("Cannot execute Http Post with empty Body");
            }
        }
        else {
            LOG.info("Invalid Http Method");
        }
        return null;
    }

    private StringBuffer get(String url, String headers, String params)
            throws URISyntaxException
    {
        Map<String,Object> headersMapWithPrimitive = utils.json.JsonUtils.jsonToMap(utils.json.JsonUtils.stringToJson(headers));
        Map<String,Object> paramsMapWithPrimitive = utils.json.JsonUtils.jsonToMap(utils.json.JsonUtils.stringToJson(params));
        Map<String,String> headersMap = new HashMap<String,String>();
        Map<String,String> paramsMap = new HashMap<String,String>();
        for(Map.Entry<String,Object> primitiveEntry : headersMapWithPrimitive.entrySet()) {
            JsonPrimitive primitive = (JsonPrimitive)(primitiveEntry.getValue());
            headersMap.put(primitiveEntry.getKey(),primitive.getAsString());
        }
        for(Map.Entry<String,Object> primitiveEntry : paramsMapWithPrimitive.entrySet()) {
            JsonPrimitive primitive = (JsonPrimitive)(primitiveEntry.getValue());
            paramsMap.put(primitiveEntry.getKey(),primitive.getAsString());
        }
        HttpClient client = HttpClientBuilder.create().build();
        URIBuilder builder = new URIBuilder(url);
        if(!params.isEmpty()) {
            Iterator it = paramsMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                builder.setParameter((String)pair.getKey(),(String)pair.getValue());
                it.remove();
            }
        }
        HttpGet request = new HttpGet(builder.build());
        StringBuffer result = new StringBuffer();
        LOG.info("Sending Http Request : " + request);
        try {
            if(!headers.isEmpty()) {
                Iterator it = headersMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    request.setHeader((String)pair.getKey(),(String)pair.getValue());
                    it.remove();
                }
            }
            HttpResponse response = client.execute(request);

            LOG.info("Response Code : " + response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private StringBuffer post(String url, String headers, String params, String body)
            throws URISyntaxException
    {
        Map<String,Object> headersMapWithPrimitive = utils.json.JsonUtils.jsonToMap(utils.json.JsonUtils.stringToJson(headers));
        Map<String,Object> paramsMapWithPrimitive = utils.json.JsonUtils.jsonToMap(utils.json.JsonUtils.stringToJson(params));
        Map<String,String> headersMap = new HashMap<String,String>();
        Map<String,String> paramsMap = new HashMap<String,String>();
        for(Map.Entry<String,Object> primitiveEntry : headersMapWithPrimitive.entrySet()) {
            JsonPrimitive primitive = (JsonPrimitive)(primitiveEntry.getValue());
            headersMap.put(primitiveEntry.getKey(),primitive.getAsString());
        }
        for(Map.Entry<String,Object> primitiveEntry : paramsMapWithPrimitive.entrySet()) {
            JsonPrimitive primitive = (JsonPrimitive)(primitiveEntry.getValue());
            paramsMap.put(primitiveEntry.getKey(),primitive.getAsString());
        }
        HttpClient client = HttpClientBuilder.create().build();
        URIBuilder builder = new URIBuilder(url);
        if(!params.isEmpty()) {
            Iterator it = paramsMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                builder.setParameter((String)pair.getKey(),(String)pair.getValue());
                it.remove();
            }
        }
        HttpPost post = new HttpPost(builder.build());
        StringBuffer result = new StringBuffer();
        LOG.info("Sending Http Request : ");
        if(!headers.isEmpty()) {
            Iterator it = headersMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                post.setHeader((String)pair.getKey(),(String)pair.getValue());
                it.remove();
            }
        }
        try {
            HttpEntity entity = new ByteArrayEntity(body.getBytes("UTF-8"));
            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            LOG.info("Response Code : " + response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
//        Map<String,Object> headers = new HashMap<String,Object>();
//        String body = "{\"registration_ids\":[\"ABC\"]}";
//        String gcmUrl = "https://fcm.googleapis.com/fcm/send";
//        headers.put("Content-Type","application/json");
//        headers.put("Authorization","key=AAAAAETG2us:APA91bFsAecpya9Ow53OumKLpNCzCA1DYKSXg-cvNDgX8A98iALfHpED56cG_zLYl2zAaE3PHBKQzbV5QTBZau5H64O5LIiooYdnS8D5eZGwX5__Iw0lP59RJv1Kb4ZjxcrrjngvaPmS");
        StringBuffer response = null;
//        try {
//            response = new HttpSender().send("POST",gcmUrl,headers,null,body);
//        }
//        catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        System.out.println("My Response : " + response);


        //SHORTHAND NOTATION FOR HTTP REQUESTS  ---------->
//        try {
//            response = new HttpSender().send(
//                    "POST",
//                    "https://fcm.googleapis.com/fcm/send",
//                    "{\"Content-Type\":\"application/json\",\"Authorization\":\"key=AAAAAETG2us:APA91bFsAecpya9Ow53OumKLpNCzCA1DYKSXg-cvNDgX8A98iALfHpED56cG_zLYl2zAaE3PHBKQzbV5QTBZau5H64O5LIiooYdnS8D5eZGwX5__Iw0lP59RJv1Kb4ZjxcrrjngvaPmS\"}",
//                    "",
//                    "{\"registration_ids\":[\"ABC\"]}"
//            );
//        }
//        catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        System.out.println(new HttpSender(true).executeGet("https://www.singlekart.com/wp-json/wc/v1/products?consumer_key=ck_ac07ede27ebf2884d9a06dada0fb1eb3a6f19810&consumer_secret=cs_c9ec0eeb500af6b34bcec948ac6ee88396daf4cc&per_page=1&page=1",null));
        String s ='import machine\nimport time\n\ndef CONVERT_DISPLAY(d):\n    \t""" Write dimming level on the SPI Connection """    \t\n    \tdig = []\n    \ttemp = d\n    \ttemp = temp/10\n    \tdig1 = d%10\n    \tdig2 = temp%10\n\t\tdig3 = temp/10\n\t\tdig[0] = dig3\n\t\tdig[1] = dig2\n\t\tdig[2] = dig1\n\t\tfor(x=0;x<3;x++)\n\t\t{\n\t\ttemp = dig[x]|0x30;\t\t\n\t\tspi.write(btemp)\n\t\t}\n\n# initiliazing loads for different Dimmer levels\nLOAD1 = 0, LOAD2 = 20, LOAD3 = 50, LOAD4 = 100\n# init SPI with baud 9600\nspi = machine.SPI(1, baudrate=9600, polarity=0, phase=0)\n\n# Writing A? where ? = dimming level\n# and A = LOAD1 as given on chip\nspi.write(b\'A\')\nCONVERT_DISPLAY(LOAD1)\ntime.sleep_ms(3000)\n\nspi.write(b\'A\')\nCONVERT_DISPLAY(LOAD2)\ntime.sleep_ms(3000)\n\nspi.write(b\'A\')\nCONVERT_DISPLAY(LOAD3)\ntime.sleep_ms(3000)\n\nspi.write(b\'A\')\nCONVERT_DISPLAY(LOAD4)\ntime.sleep_ms(3000)\n\n'
        try {
            response = new HttpSender().send(
                    "GET",
                    "https://www.singlekart.com/wp-json/wc/v1/products?consumer_key=ck_ac07ede27ebf2884d9a06dada0fb1eb3a6f19810&consumer_secret=cs_c9ec0eeb500af6b34bcec948ac6ee88396daf4cc&per_page=1&page=1",
                    "",
                    "",
                    ""
            );
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
//        try {
//            response = new HttpSender().send(
//                    "GET",
//                    "https://sellercentral.amazon.in/hz/orders/details?_encoding=UTF8&orderId=407-1522546-3877112&ref_=ag_orddet_cont_myo",
//                    "{\"X-DevTools-Emulate-Network-Conditions-Client-Id\":\"9fe46f03-cb1c-41e5-a225-24dc85d69cb9\n\" + \"Upgrade-Insecure-Requests\":\"1\n\"
//                            + \"User-Agent\":\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36\n\"
//                            + \"Accept\":\"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\n\"
//                            + \"Referer\":\"https://sellercentral.amazon.in/gp/orders-v2/list?ie=UTF8&byDate=shipDate&ignoreSearchType=1&ref_=ag_myo_wos3_home&searchDateOption=preSelected&searchFulfillers=mfn&searchType=OrderStatus&shipSearchDateOption=noTimeLimit&sortBy=OrderStatusDescending&statusFilter=ItemsToShip&\"\n
//                            + \"Accept-Encoding\":\"gzip, deflate, sdch, br\n\" + \"Accept-Language\":\"en-GB,en-US;q=0.8,en;q=0.6\"}", //headers
//                    "",     //params
//                    ""      //body
//            );
//        }
//        catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
        System.out.println(response);
    }
}
