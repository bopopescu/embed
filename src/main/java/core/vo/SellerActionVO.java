package main.java.core.vo;

import java.util.List;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by digvijaysharma on 05/02/17.
 */
@Entity("seller_action")
public class SellerActionVO {

    @Id
    private String id;

    private String seller;

    private String inquiry;

    private List<String> pricedMedicines;

    private String created;

    private String updated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getInquiry() {
        return inquiry;
    }

    public void setInquiry(String inquiry) {
        this.inquiry = inquiry;
    }

    public List<String> getPricedMedicines() {
        return pricedMedicines;
    }

    public void setPricedMedicines(List<String> pricedMedicines) {
        this.pricedMedicines = pricedMedicines;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}
