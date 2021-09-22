package com.jobsity.challenge.misc;

public class Utils {

    public static int parsePoints(String points) {
        if(points == null || "F".equals(points)) return 0;
        return Integer.parseInt(points);
    }
}
