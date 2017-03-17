package main.java.core.dto;

import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by admin on 6/1/16.
 */
public class UserProfileDTO {

    @NotEmpty
    private String name;

    @Pattern(regexp = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    private String email;

    @NotEmpty
    private WsAddress address;

    @Pattern(regexp = "\\d{10}")
    private String phoneNumber;

    private String landLine = null;

    public String getLandLine() {
        return landLine;
    }

    public void setLandLine(String landLine) {
        this.landLine = landLine;
    }

    public String getName() {
        return name;
    }

    public void setName(String sellerName) {
        this.name = sellerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public WsAddress getAddress() {
        return address;
    }

    public void setAddress(WsAddress address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
