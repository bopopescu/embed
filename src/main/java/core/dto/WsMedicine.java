package main.java.core.dto;

import main.java.core.vo.MedicineVO;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by digvijaysharma on 15/01/17.
 */
public class WsMedicine {

    @NotEmpty
    private String name;

    @NotEmpty
    private Integer quantity;

    public WsMedicine() {

    }

    public WsMedicine(MedicineVO medicineVO) {
        this.name = medicineVO.getName();
        this.quantity = medicineVO.getQuantity();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
