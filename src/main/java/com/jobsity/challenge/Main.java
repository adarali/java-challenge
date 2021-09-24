package com.jobsity.challenge;

import com.jobsity.challenge.exceptions.AppException;
import com.jobsity.challenge.misc.Input;
import com.jobsity.challenge.misc.Output;
import com.jobsity.challenge.misc.Utils;
import com.jobsity.challenge.models.players.Player;

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
            Input.processInput(scanner, line -> Utils.setScores(map, line));
            Output.print(map.values());
        } catch (FileNotFoundException | AppException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
