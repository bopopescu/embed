package main.java.core.api.request;

import main.java.core.api.base.ServiceRequest;
import main.java.core.dto.WsDeviceFingerprint;

/**
 * Created by digvijaysharma on 29/12/16.
 */
public class AssertFingerprintRequest extends ServiceRequest {

    private static final long serialVersionUID = -7191710901031348258L;

    private WsDeviceFingerprint deviceFingerprint;

    public WsDeviceFingerprint getDeviceFingerprint() {
        return deviceFingerprint;
    }

    public void setDeviceFingerprint(WsDeviceFingerprint deviceFingerprint) {
        this.deviceFingerprint = deviceFingerprint;
    }
}
