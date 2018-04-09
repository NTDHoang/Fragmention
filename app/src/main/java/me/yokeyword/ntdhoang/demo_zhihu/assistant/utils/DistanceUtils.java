package me.yokeyword.ntdhoang.demo_zhihu.assistant.utils;

import android.location.Location;
import java.text.DecimalFormat;

/**
 * Created by framgia on 01/12/2016.
 */

public class DistanceUtils {

    public static final String METRE = "m";
    public static final String KILOMETRE = "km";
    public static final String POINT_ONE = "#.#";
    public static final String POINT_TWO = "#.##";

    /**
     * @param from coordinate from
     * @param to coordinate to
     * @return distance 2 point (mile or km dependencies case)
     */
    public static String getDistanceTwoCoordinatesString(Location from, Location to) {
        double distance = getDistanceTwoCoordinates(from, to);
        DecimalFormat df = new DecimalFormat(POINT_ONE);
        if (distance < 1000) {
            return df.format(distance) + METRE;
        }
        df = new DecimalFormat(POINT_TWO);
        return df.format(distance) + KILOMETRE;
    }

    /**
     * return distance between 2 locations in meters
     */
    public static double getDistanceBetween2Location(double latitudeFrom, double longitudeFrom,
            double latitudeTo, double longitudeTo) {
        final int R = 6371; // Radius of the earth
        Double latDistance = deg2rad(latitudeTo - latitudeFrom);
        Double lonDistance = deg2rad(longitudeTo - longitudeFrom);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(deg2rad(latitudeFrom)) * Math.cos(deg2rad(latitudeTo)) * Math.sin(
                lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters
        distance = Math.pow(distance, 2);
        return Math.sqrt(distance);
    }

    /**
     * @param from coordinate from
     * @param to coordinate to
     * @return distance 2 point (mile)
     */
    private static double getDistanceTwoCoordinates(Location from, Location to) {
        double theta = from.getLongitude() - to.getLongitude();
        double dist = Math.sin(deg2rad(from.getLatitude())) * Math.sin(deg2rad(to.getLatitude()))
                + Math.cos(deg2rad(from.getLatitude())) * Math.cos(deg2rad(to.getLatitude())) * Math
                .cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
