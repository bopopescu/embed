package main.java.core.api.request;

import main.java.core.api.base.ServiceRequest;

/**
 * Created by digvijaysharma on 18/03/17.
 */
public class SetDeviceDimmingLevelRequest extends ServiceRequest {

    private static final long serialVersionUID = -7191710901031348258L;

    private String level;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
