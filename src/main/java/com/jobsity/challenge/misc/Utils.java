package com.jobsity.challenge.misc;

import com.jobsity.challenge.models.players.Player;

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
        Player player = map.getOrDefault(name, Player.createPlayer(name));
        player.setPoints(points);
        map.putIfAbsent(name, player);
    }

    public static String repeatString(String str, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
}
