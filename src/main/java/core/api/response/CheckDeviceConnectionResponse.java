package main.java.core.api.response;

import main.java.core.api.base.ServiceResponse;

/**
 * Created by digvijaysharma on 18/03/17.
 */
public class CheckDeviceConnectionResponse extends ServiceResponse {

    private static final long serialVersionUID = -7191710901031348258L;

    private boolean connected  = false;

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
