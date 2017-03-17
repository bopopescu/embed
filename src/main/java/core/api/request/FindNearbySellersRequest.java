package main.java.core.api.request;

import java.util.List;
import main.java.core.api.base.ServiceRequest;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by digvijaysharma on 04/02/17.
 */
public class FindNearbySellersRequest extends ServiceRequest {

    private static final long serialVersionUID = -7191710901031348258L;

    @NotEmpty
    private double radius;

    private List<Double> customerLocation;

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public List<Double> getCustomerLocation() {
        return customerLocation;
    }

    public void setCustomerLocation(List<Double> customerLocation) {
        this.customerLocation = customerLocation;
    }
}

