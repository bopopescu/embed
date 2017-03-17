package main.java.core.api.response;

import main.java.core.api.base.ServiceResponse;
import main.java.core.dto.SellerProfileDTO;

/**
 * Created by digvijaysharma on 16/12/16.
 */
public class GetSellerProfileResponse extends ServiceResponse {

    private SellerProfileDTO sellerProfileDTO;

    public SellerProfileDTO getSellerProfileDTO() {
        return sellerProfileDTO;
    }

    public void setSellerProfileDTO(SellerProfileDTO sellerProfileDTO) {
        this.sellerProfileDTO = sellerProfileDTO;
    }
}

