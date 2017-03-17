package main.java.core.dto;

import main.java.core.vo.AddressVO;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by digvijaysharma on 11/01/17.
 */
public class WsAddress {
    @NotBlank
    @Length(max = 500)
    private String addressLine1;

    @Length(max = 500)
    private String addressLine2;

    @NotBlank
    @Length(max = 100)
    private String city;

    @NotBlank
    private String stateCode;

    private String countryCode;

    @NotBlank
    @Length(max = 45)
    private String pincode;

    @NotBlank
    @Length(max = 50)
    private String phone;

    private WsCoordinates coordinates;

    public WsAddress() {

    }

    public WsAddress(AddressVO address) {
        this.setAddressLine1(address.getAddressLine1());
        this.setAddressLine2(address.getAddressLine2());
        this.setCity(address.getCity());
        this.setCoordinates(new WsCoordinates(address.getCoordinates()));
        this.setCountryCode(address.getCountryCode());
        this.setStateCode(address.getStateCode());
        this.setPincode(address.getPincode());
        this.setPhone(address.getPhone());
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public WsCoordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(WsCoordinates coordinates) {
        this.coordinates = coordinates;
    }
}
