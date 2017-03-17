package main.java.core.dto;

import main.java.core.vo.CoordinatesVO;

/**
 * Created by digvijaysharma on 11/01/17.
 */
public class WsCoordinates {

    private double latitude;

    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public WsCoordinates() {

    }

    public WsCoordinates(CoordinatesVO coordinates) {
        this.latitude = coordinates.getLatitude();
        this.longitude = coordinates.getLongitude();
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
}
