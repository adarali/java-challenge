package com.jobsity.challenge;

import com.jobsity.challenge.exceptions.AppException;
import com.jobsity.challenge.input.InputReader;
import com.jobsity.challenge.models.players.Player;
import com.jobsity.challenge.output.OutputPrinter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;

@SpringBootApplication
@RequiredArgsConstructor
public class Main {

    @Bean
    @Profile("!test")
    CommandLineRunner runner(InputReader<Map<String, Player>> inputReader, OutputPrinter<Collection<Player>> outputPrinter) {
        return args -> {
            try {
                outputPrinter.print(inputReader.read().values());
            } catch (AppException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    @Bean
    PrintWriter printWriter() {
        return new PrintWriter(System.out);
    }

    public static void main(String[] args) {
        ApplicationArguments arguments = new DefaultApplicationArguments(args);
        if(!arguments.containsOption("file") || StringUtils.isBlank(arguments.getOptionValues("file").get(0))) {
            System.err.println("Please specify the file path");
            return;
        }
        SpringApplication.run(Main.class, args);
    }
}
