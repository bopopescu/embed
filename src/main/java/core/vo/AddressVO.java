package main.java.core.vo;

import java.util.Date;
import main.java.core.dto.WsAddress;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by digvijaysharma on 18/12/16.
 */
@Entity("address")
public class AddressVO {

    @Id
    private String id;

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

    private CoordinatesVO coordinates;

    private Date created;

    private Date updated;

    public AddressVO(WsAddress address) {
        this.setCreated(new Date());
        this.setUpdated(new Date());
        this.setAddressLine1(address.getAddressLine1());
        this.setAddressLine2(address.getAddressLine2());
        this.setCity(address.getCity());
        this.setCoordinates(new CoordinatesVO(address.getCoordinates()));
        this.setCountryCode(address.getCountryCode());
        this.setStateCode(address.getStateCode());
        this.setPincode(address.getPincode());
        this.setPhone(address.getPhone());
    }

    public AddressVO() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public CoordinatesVO getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(CoordinatesVO coordinates) {
        this.coordinates = coordinates;
    }
}
