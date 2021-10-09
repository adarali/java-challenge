package com.jobsity.challenge;

import com.jobsity.challenge.exceptions.AppException;
import com.jobsity.challenge.input.InputReader;
import com.jobsity.challenge.models.players.Player;
import com.jobsity.challenge.output.OutputPrinter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.Collection;
import java.util.Map;

@SpringBootApplication
@RequiredArgsConstructor
public class Main {

    private final InputReader<Map<String, Player>> inputReader;
    private final OutputPrinter<Collection<Player>> outputPrinter;

    public void start() {
        try {
            outputPrinter.print(inputReader.read().values());
        } catch (AppException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @Bean
    @Profile("!test")
    CommandLineRunner runner() {
        return args -> start();
    }

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.err.println("Please specify the filename");
            return;
        }
//        String filename = args[0].split("=")[1];
//        InputReader<Collection<Player>> inputReader = new FileInputReader<>(new File(filename), new PlayerLineProcessor(new HashMap<>()));
//        new Main(inputReader, new PlayerOutputStreamOutputPrinter(System.out)).run(args);

        SpringApplication.run(Main.class, args);

    }
}
