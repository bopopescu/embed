package main.java.core.api.base;

import java.io.Serializable;

/**
 * Created by digvijaysharma on 16/12/16.
 */
public class ServiceRequest implements Serializable {

    private static final long serialVersionUID = -7191710901031348258L;

    private String callingUserId;

    public String getCallingUserId() {
        return callingUserId;
    }

    public void setCallingUserId(String callingUserId) {
        this.callingUserId = callingUserId;
    }
}
