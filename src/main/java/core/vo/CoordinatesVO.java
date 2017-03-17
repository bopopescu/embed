package main.java.core.vo;

import java.util.Date;
import main.java.core.dto.WsCoordinates;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by digvijaysharma on 18/12/16.
 */
@Entity("coordinates")
public class CoordinatesVO {

    @Id
    private String id;

    private double latitude;

    private double longitude;

    private Date created;

    private Date updated;

    public CoordinatesVO() {

    }

    public CoordinatesVO(WsCoordinates coordinates) {
        this.latitude = coordinates.getLatitude();
        this.longitude = coordinates.getLongitude();
        this.created = new Date();
        this.updated = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
