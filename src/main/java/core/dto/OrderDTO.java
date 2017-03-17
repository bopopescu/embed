package main.java.core.dto;

/**
 * Created by digvijaysharma on 05/02/17.
 */
public class OrderDTO {

    private String feasibility;

    private String status;

    private String customer;

    private SellerDTO seller;

    private InquiryDTO inquiry;

    private Integer eta;

    private WsAddress deliveryAddress;

    private WsAddress shippingAddress;

    public String getFeasibility() {
        return feasibility;
    }

    public void setFeasibility(String feasibility) {
        this.feasibility = feasibility;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public SellerDTO getSeller() {
        return seller;
    }

    public void setSeller(SellerDTO seller) {
        this.seller = seller;
    }

    public InquiryDTO getInquiry() {
        return inquiry;
    }

    public void setInquiry(InquiryDTO inquiry) {
        this.inquiry = inquiry;
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

    public WsAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(WsAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
