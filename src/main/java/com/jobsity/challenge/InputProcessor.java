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
                if (!line.matches("^\\w.*\\t(F|\\d.*)$")) {
                    throw new AppException(String.format("Line: %d is invalid", i));
                }
                String[] arr = line.split("\\t");
                setScores(playerMap, arr[0], arr[1]);
            }

            for (Map.Entry<String, Player> entry : playerMap.entrySet()) {
                System.out.println(entry.getValue());
            }
            if (i == 0) {
                throw new AppException("The file is empty");
            }
        } catch (AppException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    static void setScores(Map<String, Player> map, String name, String points) {
        Player player = map.getOrDefault(name, new Player(name));
        player.setPoints(points);
        map.put(name, player);
    }
}
