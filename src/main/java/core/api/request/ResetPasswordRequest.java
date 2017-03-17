package main.java.core.api.request;

import javax.validation.constraints.Pattern;
import main.java.core.api.base.ServiceRequest;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by digvijaysharma on 16/12/16.
 */
public class ResetPasswordRequest extends ServiceRequest {

    private static final long serialVersionUID = -7191710901031348258L;

    @NotEmpty
    String username;

    @NotEmpty
    String password;

    String mobileNumber;

    @Pattern(regexp = "\\d{6}")
    private String otp;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
