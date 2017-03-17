package main.java.core.vo;

import java.util.List;
import org.mongodb.morphia.annotations.Entity;

/**
 * Created by digvijaysharma on 18/12/16.
 */
@Entity("customer")
public class CustomerVO extends UserVO {

    private String user;

    private List<String> inquiries;

    private String customerProfile;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<String> getInquiries() {
        return inquiries;
    }

    public void setInquiries(List<String> inquiries) {
        this.inquiries = inquiries;
    }

    public String getCustomerProfile() {
        return customerProfile;
    }

    public void setCustomerProfile(String customerProfile) {
        this.customerProfile = customerProfile;
    }
}