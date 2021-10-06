package com.jobsity.challenge;

import com.jobsity.challenge.exceptions.AppException;
import com.jobsity.challenge.input.FileInputReader;
import com.jobsity.challenge.input.InputReader;
import com.jobsity.challenge.input.ScannerInputReader;
import com.jobsity.challenge.models.players.Player;
import com.jobsity.challenge.output.OutputPrinter;
import com.jobsity.challenge.output.OutputStreamOutputPrinter;
import com.jobsity.challenge.processors.LineProcessor;
import com.jobsity.challenge.processors.PlayerLineProcessor;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@RequiredArgsConstructor
public class Main {

    private final InputReader<Collection<Player>> inputReader;
    private final OutputPrinter<Player> outputPrinter;

    private void doStuff() {
        try {
            outputPrinter.print(inputReader.read());
        } catch (AppException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.err.println("Please specify the filename");
            return;
        }
        InputReader<Collection<Player>> inputReader = new FileInputReader<>(new File(args[0]), new PlayerLineProcessor());
        new Main(inputReader, new OutputStreamOutputPrinter(System.out)).doStuff();

    }
}
