package main.java.core.entity;

/**
 * Created by digvijaysharma on 11/02/17.
 */

/**
 * Shows The Statistics Of The Remote Being Used
 */
public class RemoteStats {

    public String buttonName;

    public String remoteControlName;

    public int repeatCount;

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public String getRemoteControlName() {
        return remoteControlName;
    }

    public void setRemoteControlName(String remoteControlName) {
        this.remoteControlName = remoteControlName;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }
}
