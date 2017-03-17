package main.java.utils.http;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

/**
 * Created by admin on 4/30/16.
 */
public class OtpServer {

    private static Client client = ClientBuilder.newClient();

    private static final String baseUrl = "https://control.msg91.com/api/sendhttp.php";

    public static boolean sendOtp(String phoneNumber, int otp) {
        Response response = client.target(baseUrl).queryParam("authkey", "136528A6bPyMrLG58712f40").queryParam("route", "4").
                queryParam("sender", "MSGIND").queryParam("country", "0").queryParam("mobiles", "91" + phoneNumber).
                queryParam("message", "Never share OTP with any one. The OTP is: " + otp).request().get();
        System.out.println(response);
        if (response.getStatusInfo().getStatusCode() == 200) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(sendOtp("9818539195", 987125));
    }
}
