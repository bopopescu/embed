package main.java.core.dto;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by digvijaysharma on 05/02/17.
 */
public class WsOrder {

    @NotEmpty
    private String seller;

    @NotNull
    private Integer eta;

    @NotEmpty
    private String inquiry;

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public Integer getEta() {
        return eta;
    }

    public void setEta(Integer eta) {
        this.eta = eta;
    }

    public String getInquiry() {
        return inquiry;
    }

    public void setInquiry(String inquiry) {
        this.inquiry = inquiry;
    }
}
