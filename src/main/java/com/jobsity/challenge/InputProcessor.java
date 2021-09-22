package com.jobsity.challenge;

import com.jobsity.challenge.exceptions.AppException;
import com.jobsity.challenge.models.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InputProcessor {

    public static void processFile(File file) {
        try {
            processInput(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void processInput(InputStream inputStream) {
        Map<String, Player> playerMap = new HashMap<>();
        try (Scanner scanner = new Scanner(inputStream)) {
            int i = 0;
            while (scanner.hasNext()) {
                i++;
                String line = scanner.nextLine();
                if (!line.matches("\\w.*(\\s|\\t)(F|\\d.*)$")) {
                    throw new AppException(String.format("Line: %d is invalid", i));
                }
                String[] arr = line.split("\\s|\\t");
                String name = arr[0];
                String pointString = arr[1];
                int points = 0;
                if (!"F".equals(pointString)) {
                    points = Integer.parseInt(pointString);
                }
                setScores(playerMap, name, points);
            }

            for (Map.Entry<String, Player> entry : playerMap.entrySet()) {
                System.out.println(entry.getValue());
            }
        } catch (AppException e) {
            System.err.println(e.getMessage());
        }
    }

    static void setScores(Map<String, Player> map, String name, int points) {
        Player player = map.getOrDefault(name, new Player(name));
        player.setPoints(points);
        map.put(name, player);
    }
}
