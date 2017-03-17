package main.java.core.dto;

import java.util.List;
import main.java.core.vo.SellerProfileVO;
import main.java.core.vo.UserProfileVO;

/**
 * Created by digvijaysharma on 04/02/17.
 */
public class SellerProfileForCustomerDTO {

    private String shopName = null;

    private List<Float> shopLocation = null;

    private boolean delivery;

    private List<String> storeTimings = null;

    private boolean openAllDay;

    private WsAddress address;

    public SellerProfileForCustomerDTO() {

    }

    public SellerProfileForCustomerDTO(SellerProfileVO sellerProfileVO, UserProfileVO userProfileVO) {
        this.shopLocation = sellerProfileVO.getShopLocation();
        this.delivery = sellerProfileVO.isDelivery();
        this.openAllDay = sellerProfileVO.isOpenAllDay();
        this.storeTimings = sellerProfileVO.getStoreTimings();
        this.shopName = sellerProfileVO.getShopName();
        this.address = (userProfileVO.getAddress() != null) ? new WsAddress(userProfileVO.getAddress()): null;
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

    public WsAddress getAddress() {
        return address;
    }

    public void setAddress(WsAddress address) {
        this.address = address;
    }
}
