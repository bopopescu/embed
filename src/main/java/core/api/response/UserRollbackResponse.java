package main.java.core.api.response;

import main.java.core.api.base.ServiceResponse;

/**
 * Created by digvijaysharma on 26/01/17.
 */
public class UserRollbackResponse extends ServiceResponse {

    private static final long serialVersionUID = -7191710901031348258L;

    private int n;

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}
