package main.java.core.api.response;

import java.util.List;
import main.java.core.api.base.ServiceResponse;
import main.java.core.dto.SellerProfileForCustomerDTO;

/**
 * Created by digvijaysharma on 04/02/17.
 */
public class FindNearbySellersResponse extends ServiceResponse {

    private static final long serialVersionUID = -7191710901031348258L;

    List<SellerProfileForCustomerDTO> sellers;

    public List<SellerProfileForCustomerDTO> getSellers() {
        return sellers;
    }

    public void setSellers(List<SellerProfileForCustomerDTO> sellers) {
        this.sellers = sellers;
    }
}


