package main.java.core.api.request;

import main.java.core.api.base.ServiceRequest;

/**
 * Created by digvijaysharma on 05/02/17.
 */
public class CreateRandomCodeRequest extends ServiceRequest {

    private static final long serialVersionUID = -7191710901031348258L;

    private int noOfAlphabets;

    private int lengthOfCode;

    public CreateRandomCodeRequest() {

    }

    public CreateRandomCodeRequest(int noOfAlphabets, int lengthOfCode) {
        this.lengthOfCode = lengthOfCode;
        this.noOfAlphabets = noOfAlphabets;
    }

    public int getNoOfAlphabets() {
        return noOfAlphabets;
    }

    public void setNoOfAlphabets(int noOfAlphabets) {
        this.noOfAlphabets = noOfAlphabets;
    }

    public int getLengthOfCode() {
        return lengthOfCode;
    }

    public void setLengthOfCode(int lengthOfCode) {
        this.lengthOfCode = lengthOfCode;
    }
}

