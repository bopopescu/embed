package main.java.core.dto;

import main.java.core.vo.InquiryVO;

/**
 * Created by digvijaysharma on 02/02/17.
 */
public class InquiryDTO {

    private String code;

    private boolean assigned = false;

    private WsPrescription prescription;

    private Integer eta;

    private WsAddress deliveryAddress;

    private String priority;

    private String status;

    public InquiryDTO() {

    }

    public InquiryDTO(InquiryVO inquiryVO) {
        this.code = inquiryVO.getCode();
        this.assigned = inquiryVO.isAssigned();
        this.prescription = new WsPrescription(inquiryVO.getPrescription());
        this.eta = inquiryVO.getEta();
        this.deliveryAddress = new WsAddress(inquiryVO.getDeliveryAddress());
        this.priority = inquiryVO.getPriority();
        this.status = inquiryVO.getStatus();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
