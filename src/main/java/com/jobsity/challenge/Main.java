package com.jobsity.challenge;

import com.jobsity.challenge.exceptions.AppException;
import com.jobsity.challenge.models.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.err.println("Please specify the filename");
            return;
        }

        Map<String, Player> map = new HashMap<>();

        try (Scanner scanner = new Scanner(new File(args[0]))){
            Input.processInput(scanner, line -> {
                String[] arr = line.split("\\t");
                setScores(map, arr[0], arr[1]);
            });
            Output.print(map);
        } catch (FileNotFoundException | AppException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    static void setScores(Map<String, Player> map, String name, String points) {
        Player player = map.getOrDefault(name, new Player(name));
        player.setPoints(points);
        map.put(name, player);
    }
}
