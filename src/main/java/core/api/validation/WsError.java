package main.java.core.api.validation;

/**
 * Created by digvijaysharma on 18/12/16.
 */
public class WsError {

    private int code;

    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public WsError(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
