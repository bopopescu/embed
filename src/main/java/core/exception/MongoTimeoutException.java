package main.java.core.exception;

import com.mongodb.MongoClientException;

/**
 * Created by digvijaysharma on 18/12/16.
 */
public class MongoTimeoutException extends MongoClientException {

    private static final long serialVersionUID = -3016560214331826577L;

    private String message;

    public MongoTimeoutException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
