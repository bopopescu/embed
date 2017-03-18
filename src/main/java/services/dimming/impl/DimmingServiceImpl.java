package main.java.services.dimming.impl;

import java.net.URISyntaxException;
import main.java.core.annotation.Service;
import main.java.core.api.request.ChangeDeviceStateRequest;
import main.java.core.api.request.CheckDeviceConnectionRequest;
import main.java.core.api.request.SetDeviceDimmingLevelRequest;
import main.java.core.api.response.ChangeDeviceStateResponse;
import main.java.core.api.response.CheckDeviceConnectionResponse;
import main.java.core.api.response.SetDeviceDimmingLevelResponse;
import main.java.core.api.validation.ResponseCode;
import main.java.services.dimming.IDimmingService;
import main.java.utils.http.HttpSender;

/**
 * Created by digvijaysharma on 18/03/17.
 */
@Service
public class DimmingServiceImpl implements IDimmingService {

    private org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(DimmingServiceImpl.class);

    public DimmingServiceImpl() {
    }

    public CheckDeviceConnectionResponse checkDeviceConnection(CheckDeviceConnectionRequest request) {
        CheckDeviceConnectionResponse response = new CheckDeviceConnectionResponse();
//        String email = userMao.getUserById(userCode).getUsername();
//        UserProfileVO userProfile = userProfileMao.getUserProfileByEmail(email);
//        if (userProfile != null) {
//            response.setSuccess(true);
//            response.setMessage("Profile Picture Set!");
//        }
//        else {
//            response.addError(ResponseCode.USER_PROFILE_DOES_NOT_EXIST, "User Profile does not exist");
//        }
        return response;
    }

    public SetDeviceDimmingLevelResponse setDeviceDimmingLevel(SetDeviceDimmingLevelRequest request) {
        SetDeviceDimmingLevelResponse response = new SetDeviceDimmingLevelResponse();
        StringBuffer deviceResponse = null;
        /* Send dimming level to device */
        try {
            deviceResponse = new HttpSender().send(
                    "POST",
                    "http://192.168.43.172/",
                    "{\"Content-Type\":\"application/json\"}",
                    "",
                    "{\"x\":\"" + request.getLevel() + "\"}"
            );
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
        LOG.info(String.valueOf(deviceResponse));
        if (deviceResponse != null) {
            response.setSuccess(true);
            response.setMessage("Dimming Level Set!");
        }
        else {
            response.addError(ResponseCode.INTERNAL_SERVER_ERROR, "Dimming level could not be set");
        }
        return response;
    }

    public ChangeDeviceStateResponse changeDeviceState(ChangeDeviceStateRequest request) {
        ChangeDeviceStateResponse response = new ChangeDeviceStateResponse();
//        if (deviceResponse != null) {
//            response.setSuccess(true);
//            response.setMessage("Dimming level Set!");
//        }
//        else {
//            response.addError(ResponseCode.USER_PROFILE_DOES_NOT_EXIST, "User Profile does not exist");
//        }
        return response;
    }
}
