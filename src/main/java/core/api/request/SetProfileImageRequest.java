package main.java.core.api.request;

import javax.validation.Valid;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import main.java.core.api.base.ServiceRequest;
import main.java.core.dto.SellerProfileDTO;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by digvijaysharma on 09/01/17.
 */
public class SetProfileImageRequest extends ServiceRequest {

    private static final long serialVersionUID = -7191710901031348258L;

    @Valid
    @NotEmpty SellerProfileDTO userProfile;

    @Context SecurityContext securityContext;

    public SellerProfileDTO getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(SellerProfileDTO userProfile) {
        this.userProfile = userProfile;
    }

    public SecurityContext getSecurityContext() {
        return securityContext;
    }

    public void setSecurityContext(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }
}
