package main.java.core.api.request;

import main.java.core.api.base.ServiceRequest;
import main.java.core.dto.SellerProfileDTO;

/**
 * Created by digvijaysharma on 29/01/17.
 */
public class CreateSellerProfileRequest extends ServiceRequest {

    private static final long serialVersionUID = -7191710901031348258L;

    private SellerProfileDTO sellerProfile;

    public SellerProfileDTO getSellerProfile() {
        return sellerProfile;
    }

    public void setSellerProfile(SellerProfileDTO sellerProfile) {
        this.sellerProfile = sellerProfile;
    }
}
