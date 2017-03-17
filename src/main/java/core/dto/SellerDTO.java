package main.java.core.dto;

import java.util.List;

/**
 * Created by digvijaysharma on 05/02/17.
 */
public class SellerDTO {

    private String user;

    List<String> inquiries;

    List<String> orders;

    private SellerProfileDTO sellerProfile;

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

    public List<String> getOrders() {
        return orders;
    }

    public void setOrders(List<String> orders) {
        this.orders = orders;
    }

    public SellerProfileDTO getSellerProfile() {
        return sellerProfile;
    }

    public void setSellerProfile(SellerProfileDTO sellerProfile) {
        this.sellerProfile = sellerProfile;
    }
}
