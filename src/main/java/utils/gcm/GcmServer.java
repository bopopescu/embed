package main.java.utils.gcm;

import java.net.URISyntaxException;
import main.java.utils.http.HttpSender;

/**
 * Created by digvijaysharma on 05/01/17.
 */
public class GcmServer {

    /**
     *
     * @param gcmId The device's Gcm Identifier
     * @param data  The data to send in json form encapsulated in a string
     */
    public static StringBuffer sendMessage(String gcmId, String data) {
        StringBuffer response = null;
        try {
            response = new HttpSender().send(
                    "POST",
                    "https://fcm.googleapis.com/fcm/send",
                    "{\"Content-Type\":\"application/json\",\"Authorization\":\"key=AAAAAETG2us:APA91bFsAecpya9Ow53OumKLpNCzCA1DYKSXg-cvNDgX8A98iALfHpED56cG_zLYl2zAaE3PHBKQzbV5QTBZau5H64O5LIiooYdnS8D5eZGwX5__Iw0lP59RJv1Kb4ZjxcrrjngvaPmS\"}",
                    "",
                    "{ \"data\":"+ data +",\n"
                            + "  \"to\" : \"bk3RNwTe3H0:CI2k_HHwgIpoDKCIZvvDMExUdFQ3P1...\"\n" + "}"
            );
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println("Response : " + response);
        return response;
    }

    public static void main(String[] args) {
        StringBuffer response = GcmServer.sendMessage("abcd1234","{\n" + "    \"score\": \"5x1\",\n" + "    \"time\": \"15:10\"}");
    }
}
