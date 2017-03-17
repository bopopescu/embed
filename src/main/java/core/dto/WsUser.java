package main.java.core.dto;

import javax.validation.constraints.Pattern;
import main.java.core.vo.UserVO;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by digvijaysharma on 18/12/16.
 */
public class WsUser {

    @NotEmpty
    private String username;

    @NotEmpty
    private String type;

    @Pattern(regexp = "\\d{10}")
    private String mobileNumber;

    @Pattern(regexp = "\\d{6}")
    private String otp;

    public WsUser() {

    }

    public WsUser(UserVO userVO) {
        this.username = userVO.getUsername();
        this.type = userVO.getType();
        this.mobileNumber = userVO.getMobileNumber();
        this.otp = userVO.getOtp();
    }

    public WsUser(String username, String mobileNumber, String otp) {
        this.username = username;
        this.mobileNumber = mobileNumber;
        this.otp = otp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}

