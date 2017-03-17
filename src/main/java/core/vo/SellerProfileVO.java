package main.java.core.vo;

import java.util.List;
import org.hibernate.validator.constraints.NotBlank;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by digvijaysharma on 11/01/17.
 */
@Entity("seller_profile")
public class SellerProfileVO extends UserProfileVO {

    @Id
    private String Id;

    private String userProfile;

    private String shopName = null;

    private List<Float> shopLocation = null;

    private boolean delivery;

    private String storeId = null;

    @NotBlank
    private String partyCode;

    private List<String> storeTimings = null;

    private boolean openAllDay;

    @Override public String getId() {
        return Id;
    }

    @Override public void setId(String id) {
        Id = id;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public String getPartyCode() {
        return partyCode;
    }

    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<Float> getShopLocation() {
        return shopLocation;
    }

    public void setShopLocation(List<Float> shopLocation) {
        this.shopLocation = shopLocation;
    }

    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public List<String> getStoreTimings() {
        return storeTimings;
    }

    public void setStoreTimings(List<String> storeTimings) {
        this.storeTimings = storeTimings;
    }

    public boolean isOpenAllDay() {
        return openAllDay;
    }

    public void setOpenAllDay(boolean openAllDay) {
        this.openAllDay = openAllDay;
    }
}
