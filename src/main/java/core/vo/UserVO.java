package main.java.core.vo;

import java.util.Date;
import java.util.List;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by admin on 4/27/16.
 */
@Entity("user")
public class UserVO {

    public enum Type {
      CUSTOMER,
      SELLER
    };

    @Id
    private String id;

    private String type;

    private String code;

    private String username;

    private String password;

    private String mobileNumber;

    private String otp;

    private List<DeviceFingerprintVO> fingerprints;

    private Date created;

    private Date updated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public List<DeviceFingerprintVO> getFingerprints() {
        return fingerprints;
    }

    public void setFingerprints(List<DeviceFingerprintVO> fingerprints) {
        this.fingerprints = fingerprints;
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
}
