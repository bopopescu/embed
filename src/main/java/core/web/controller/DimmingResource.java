package main.java.core.web.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import main.java.core.annotation.Controller;
import main.java.core.api.request.ChangeDeviceStateRequest;
import main.java.core.api.request.SetDeviceDimmingLevelRequest;
import main.java.core.api.response.ChangeDeviceStateResponse;
import main.java.core.api.response.SetDeviceDimmingLevelResponse;
import main.java.services.dimming.impl.DimmingServiceImpl;

/**
 * Created by digvijaysharma on 18/03/17.
 */
@Controller
@Path("/dimming")
@Produces(APPLICATION_JSON)
public class DimmingResource {

    private DimmingServiceImpl dimmingService;

    public DimmingResource(DimmingServiceImpl dimmingService) {
        this.dimmingService = dimmingService;
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Path("/change-device-state")
    public ChangeDeviceStateResponse changeDeviceState(ChangeDeviceStateRequest request) {
        return dimmingService.changeDeviceState(request);
    }

    @POST
    @Consumes(APPLICATION_JSON)
    @Path("/set-dimming-level")
    public SetDeviceDimmingLevelResponse setDeviceDimmingLevel(SetDeviceDimmingLevelRequest request) {
        return dimmingService.setDeviceDimmingLevel(request);
    }
}
