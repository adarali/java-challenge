package com.jobsity.challenge.misc;

import com.jobsity.challenge.models.Player;

import java.util.Map;

public class Utils {

    public static int parsePoints(String points) {
        if(points == null || "F".equals(points)) return 0;
        return Integer.parseInt(points);
    }

    public static void setScores(Map<String, Player> map, String line) {
        String[] arr = line.split("\\t");
        String name = arr[0];
        String points = arr[1];
        Player player = map.getOrDefault(name, new Player(name));
        player.setPoints(points);
        map.put(name, player);
    }
}
