package com.jobsity.challenge.misc;

import com.jobsity.challenge.models.players.Player;

import java.util.Map;

public class Utils {

    public static String repeatString(String str, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
}
