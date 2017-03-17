package main.java.core.dto;

/**
 * Created by digvijaysharma on 29/12/16.
 */
public class WsDeviceFingerprint {

    private String gcmId;

    private String macAddress;

    public String getGcmId() {
        return gcmId;
    }

    public void setGcmId(String gcmId) {
        this.gcmId = gcmId;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

}
