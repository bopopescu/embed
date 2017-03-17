package main.java.core.vo;

import java.util.List;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by digvijaysharma on 18/12/16.
 */
@Entity("seller")
public class SellerVO extends UserVO {

    @Id
    private String id;

    private String user;

    List<String> inquiries;

    List<String> orders;

    List<String> sellerActions;

    private String sellerProfile;

    @Override public String getId() {
        return id;
    }

    @Override public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSellerProfile() {
        return sellerProfile;
    }

    public void setSellerProfile(String sellerProfile) {
        this.sellerProfile = sellerProfile;
    }

    public List<String> getInquiries() {
        return inquiries;
    }

    public void setInquiries(List<String> inquiries) {
        this.inquiries = inquiries;
    }

    public List<String> getSellerActions() {
        return sellerActions;
    }

    public void setSellerActions(List<String> sellerActions) {
        this.sellerActions = sellerActions;
    }

    public List<String> getOrders() {
        return orders;
    }

    public void setOrders(List<String> orders) {
        this.orders = orders;
    }
}
