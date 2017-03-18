package main.java.utils.latlong;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import main.java.core.vo.CoordinatesVO;

/**
 * Created by digvijaysharma on 04/02/17.
 */

/**
 * ---- COORDINATES FORMAT ----
 * * The coordinates can be
 * List in the format :
 * List.get(0) = Latitude
 * List.get(1) = Longitude
 * like source list, destination list for distanceBetweenTwoPoints
 * or
 * Object of the inner class Coordinates here
 */
public class LocationUtils {

    private static double R = 6371;

    /**
     * For List Format :
     *
     * @param source
     * @param destination
     * @return
     */
    public double distanceBetweenTwoPoints(List<Double> source, List<Double> destination) {
        return calculateDistance(source.get(0), source.get(1), destination.get(0), destination.get(1));
    }

    /**
     * For Coordinates Format :
     *
     * @param source
     * @param destination
     * @return
     */
    public double distanceBetweenTwoPoints(Coordinates source, Coordinates destination) {
        return calculateDistance(source.getLatitude(), source.getLongitude(), destination.getLatitude(), destination.getLongitude());
    }

    /**
     * The shortest distance (the geodesic) between two given points
     * P1=(lat1, lon1) and P2=(lat2, lon2)
     * on the surface of a sphere with radius R is the great circle distance.
     *
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return the distance, measured in the same unit as the radius
     * argument.
     */
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        return (acos(sin(lat1) * sin(lat2)) + (cos(lat1) * cos(lat2) * cos(lon1 - lon2)) * R);
    }

    public static class Coordinates {

        private double latitude;

        private double longitude;

        public Coordinates(CoordinatesVO coordinatesVO) {
            this.latitude = coordinatesVO.getLatitude();
            this.longitude = coordinatesVO.getLongitude();
        }

        public Coordinates(List<Double> list) {
            this.latitude = list.get(0);
            this.longitude = list.get(1);
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
    }

    public static void main(String[] args) {

    }

    /**
     * Takes in a map containing the coordinates to check if they lie in the circle with given radius
     * @param indexToCoordinates = Map to keep track of indices of the coordinates for selection
     * @param pivot = Point about which search is being done
     * @param radius = The radius to search
     * @return Returns only the selected coordinates lying in the query circle
     */
    public static Map<Integer, Coordinates> selectCoordinatesWithinRadius(Map<Integer, Coordinates> indexToCoordinates, Coordinates pivot, double radius) {
        Map<Integer, Coordinates> selectedCoordinates = new HashMap<Integer, Coordinates>();
        Set<Map.Entry<Integer, Coordinates>> entries = indexToCoordinates.entrySet();
        for (Map.Entry<Integer, Coordinates> entry : entries) {
            Integer index = entry.getKey();
            Coordinates coordinates = entry.getValue();
            if (checkCoordinates(LocationUtils.R, radius, pivot, coordinates.getLatitude(), coordinates.getLongitude())) {
                selectedCoordinates.put(index, coordinates);
            }
        }
        return selectedCoordinates;
    }

    /**
     * Check if coordinates {Lat, Lon} is inside the circle with radius {radius}
     * @param earthRadius = The radius of the earth
     * @param radius = The radius to search
     * @param pivot = Point about which search is being done
     * @param Lat = Latitude of the point to be checked
     * @param Lon = Longitude of the point to be checked
     * @return True if coordinates {Lat, Lon} lie in the query circle
     */
    private static boolean checkCoordinates(double earthRadius, double radius, Coordinates pivot, double Lat, double Lon) {
        GeoLocation pivotLocation = GeoLocation.fromRadians(pivot.getLatitude(), pivot.getLongitude());
        GeoLocation[] boundingCoordinates = pivotLocation.boundingCoordinates(radius, earthRadius);
        boolean meridian180WithinDistance = boundingCoordinates[0].getLongitudeInRadians() > boundingCoordinates[1].getLongitudeInRadians();
        if (meridian180WithinDistance) {
            if ((Lat >= boundingCoordinates[0].getLatitudeInRadians() && Lat <= boundingCoordinates[1].getLatitudeInRadians()) && (
                    Lon >= boundingCoordinates[0].getLongitudeInRadians() || Lon <= boundingCoordinates[1].getLongitudeInRadians()) &&
                    acos(sin(pivotLocation.getLatitudeInRadians()) * sin(Lat) + cos(pivotLocation.getLatitudeInRadians()) * cos(Lat) * cos(
                            Lon - pivotLocation.getLongitudeInRadians())) <= radius / earthRadius) {
                return true;
            }
        } else {
            if ((Lat >= boundingCoordinates[0].getLatitudeInRadians() && Lat <= boundingCoordinates[1].getLatitudeInRadians()) && (
                    Lon >= boundingCoordinates[0].getLongitudeInRadians() && Lon <= boundingCoordinates[1].getLongitudeInRadians()) &&
                    acos(sin(pivotLocation.getLatitudeInRadians()) * sin(Lat) + cos(pivotLocation.getLatitudeInRadians()) * cos(Lat) * cos(
                            Lon - pivotLocation.getLongitudeInRadians())) <= radius / earthRadius) {
                return true;
            }
        }
        return false;
    }
}
