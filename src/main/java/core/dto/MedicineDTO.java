package main.java.core.dto;

import main.java.core.vo.MedicineVO;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by digvijaysharma on 05/02/17.
 */
public class MedicineDTO {
    @NotEmpty
    private String name;

    @NotEmpty
    private Integer quantity;

    @NotEmpty
    private Float price;

    @NotEmpty
    private Float discount;

    public MedicineDTO() {

    }

    public MedicineDTO(MedicineVO medicineVO) {
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }
}
