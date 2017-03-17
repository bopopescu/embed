package main.java.core.dto;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by digvijaysharma on 15/01/17.
 */
public class WsInquiry {

    @NotEmpty
    private WsPrescription prescription;

    @NotEmpty
    private Integer eta;

    @NotEmpty
    private WsAddress deliveryAddress;

    @NotEmpty
    private String priority;

    public WsPrescription getPrescription() {
        return prescription;
    }

    public void setPrescription(WsPrescription prescription) {
        this.prescription = prescription;
    }

    public Integer getEta() {
        return eta;
    }

    public void setEta(Integer eta) {
        this.eta = eta;
    }

    public WsAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(WsAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
