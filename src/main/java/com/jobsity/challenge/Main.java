package com.jobsity.challenge;

import com.jobsity.challenge.exceptions.AppException;
import com.jobsity.challenge.input.InputReader;
import com.jobsity.challenge.models.players.Player;
import com.jobsity.challenge.output.OutputPrinter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collection;

@SpringBootApplication
@RequiredArgsConstructor
public class Main implements CommandLineRunner {

    private final InputReader<Collection<Player>> inputReader;
    private final OutputPrinter<Player> outputPrinter;

    @Override
    public void run(String... args) {
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
//        InputReader<Collection<Player>> inputReader = new FileInputReader<>(new File(args[0]), new PlayerLineProcessor());
//        new Main(inputReader, new OutputStreamOutputPrinter(System.out)).doStuff();

        SpringApplication.run(Main.class, args);

    }
}
