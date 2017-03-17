package main.java.core.vo;

import java.util.Date;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by digvijaysharma on 18/12/16.
 */
@Entity("order")
public class OrderVO {

    public enum Feasibility {
        FULFILABLE,
        UNFULFILABLE
    };

    public enum Status {
        CREATED,
        DISPATCHED,
        SHIPPED,
        RETURNED
    };

    @Id
    private String id;

    private String feasibility;

    private String status;

    private CustomerVO customer;

    private SellerVO seller;

    private InquiryVO inquiry;

    private Integer eta;

    private AddressVO deliveryAddress;

    private AddressVO shippingAddress;

    private Date created;

    private Date updated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public CustomerVO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerVO customer) {
        this.customer = customer;
    }

    public SellerVO getSeller() {
        return seller;
    }

    public void setSeller(SellerVO seller) {
        this.seller = seller;
    }

    public InquiryVO getInquiry() {
        return inquiry;
    }

    public void setInquiry(InquiryVO inquiry) {
        this.inquiry = inquiry;
    }

    public Integer getEta() {
        return eta;
    }

    public void setEta(Integer eta) {
        this.eta = eta;
    }

    public AddressVO getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(AddressVO deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public AddressVO getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressVO shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
