package main.java.core.dto;

import java.util.List;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by digvijaysharma on 18/12/16.
 */
public class SellerProfileDTO {

    @NotEmpty private UserProfileDTO userProfile;

    private String shopName = null;

    private List<Float> shopLocation = null;

    private boolean delivery;

    private String storeId = null;

    @NotBlank
    private String partyCode;

    private List<String> storeTimings = null;

    private boolean openAllDay;

    public UserProfileDTO getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileDTO userProfile) {
        this.userProfile = userProfile;
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

    public String getPartyCode() {
        return partyCode;
    }

    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
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
