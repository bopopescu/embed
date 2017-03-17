package main.java.core.vo;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import utils.json.JsonUtils;

/**
 * Created by digvijaysharma on 18/12/16.
 */
@Entity("inquiry")
public class InquiryVO {

    public enum Priority {
        HIGH,
        LOW,
        MEDIUM
    };

    public enum Status {
        UNASSIGNED,
        ASSIGNED,
        COMPLETED
    };

    @Id
    private String id;

    private String code;

    private String customer;

    private List<String> sellers;

    private boolean assigned = false;

    private PrescriptionVO prescription;

    private Integer eta;

    private AddressVO deliveryAddress;

    private Set<String> sellerActions;

    private String priority;

    private String status;

    private Date created;

    private Date updated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<String> getSellerActions() {
        return sellerActions;
    }

    public void setSellerActions(Set<String> sellerActions) {
        this.sellerActions = sellerActions;
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

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public List<String> getSellers() {
        return sellers;
    }

    public void setSellers(List<String> sellers) {
        this.sellers = sellers;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public PrescriptionVO getPrescription() {
        return prescription;
    }

    public void setPrescription(PrescriptionVO prescription) {
        this.prescription = prescription;
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

    @Override public String toString() {
        return JsonUtils.objectToString(this);
    }
}
