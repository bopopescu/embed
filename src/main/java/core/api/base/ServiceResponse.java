package main.java.core.api.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import main.java.core.api.validation.ResponseCode;
import main.java.core.api.validation.WsError;

;

/**
 * Created by admin on 4/30/16.
 */
public class ServiceResponse implements Serializable {

    private List<WsError> errors = new ArrayList<WsError>();

    private boolean success;

    private String  message;

    public List<WsError> getErrors() {
        return errors;
    }

    public void setErrors(List<WsError> errors) {
        this.errors = errors;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void addError(ResponseCode code, String message) {
        this.errors.add(new WsError(code.getCode(),message));
    }

    public boolean hasErrors() {
        return this.errors.size() == 0 ? false : true;
    }
}
